package com.swifta.mats.web.usermanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.utils.ReportingService;
import com.swifta.sub.mats.reporting.DataServiceFault;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profile;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class SearchUserModule {

	private ArrayList<Object> arrLTfs;
	private boolean isSearchURL = false;

	private Map<String, String> hmProfiles;
	private BE2 be;
	private ReportingService rs;
	private HashMap<String, String> hmPerms;

	public SearchUserModule(HashMap<String, String> hmPerms) {
		this.hmPerms = hmPerms;

	}

	public void addFilters(String strParams) {

		if (be == null)
			be = new BE2(hmPerms);
		be.addFilters(strParams);
	}

	public void search(String strParams) {

		if (be == null)
			be = new BE2(hmPerms);
		be.search(strParams);
	}

	public FormLayout getSearchForm(String strUserType) {

		FormLayout searchForm = new FormLayout();
		searchForm.setSizeUndefined();
		searchForm.setSpacing(true);
		searchForm.setMargin(false);
		searchForm.setStyleName("search_user_form");

		Embedded emb = new Embedded(null, new ThemeResource(
				"img/search_user_icon.png"));
		emb.setDescription("Search users");
		emb.setStyleName("search_user_img");
		emb.setSizeUndefined();

		Label lbSearch = new Label("Search " + "Users" + " by: ");

		// Label lbSearch = new Label("Search " + strUserType + " by: ");
		lbSearch.setSizeUndefined();
		lbSearch.setStyleName("label_search_user");
		lbSearch.setSizeUndefined();

		VerticalLayout searchUserHeader = new VerticalLayout();
		searchUserHeader.setHeightUndefined();
		searchUserHeader.setMargin(false);
		searchUserHeader.setSpacing(true);
		searchUserHeader.addComponent(emb);
		searchUserHeader.addComponent(lbSearch);
		searchUserHeader.setStyleName("search_user_header");

		ArrayList<String> arrLTfCaptions = new ArrayList<String>();

		// arrLTfCaptions.add("ID");
		// arrLTfCaptions.add(strUserType + " ID");
		// arrLTfCaptions.add("Type of User");
		arrLTfCaptions.add("Username");
		arrLTfCaptions.add("MSISDN");
		arrLTfCaptions.add("Email");
		// arrLTfCaptions.add("Company");
		// arrLTfCaptions.add("First Name");
		// arrLTfCaptions.add("Last Name");
		// arrLTfCaptions.add("Others");

		searchForm.addComponent(searchUserHeader);
		arrLTfs = addTfs(arrLTfCaptions, searchForm);

		Button btnSearch = new Button("Search");
		searchForm.addComponent(btnSearch);

		btnSearch.setIcon(FontAwesome.SEARCH);
		isSearchURL = false;

		btnSearch.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -5894920456172825127L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (!isSearchURL) {
					isSearchURL = true;
				}
				StringBuilder strBuilder = new StringBuilder();

				for (Object tF : arrLTfs) {
					if (tF instanceof TextField) {

						strBuilder.append(((TextField) tF).getCaption());
						strBuilder.append("=");
						strBuilder.append(((TextField) tF).getValue());
						strBuilder.append("&");
					} else {
						ComboBox combo = (ComboBox) tF;
						if (combo.getValue() == null) {
							Notification.show("Please at least a profile type",
									Notification.Type.ERROR_MESSAGE);
							combo.focus();
							return;
						}
						String strProf = combo.getValue().toString();
						strBuilder.append(combo.getCaption());
						strBuilder.append("=");
						strBuilder.append(strProf);
						strBuilder.append("&");
					}
				}

				String strParams = strBuilder.toString();
				String url = "!" + UMView.UM + "/?action=search&" + strParams;

				UI.getCurrent().getPage().setUriFragment(url);

			}
		});

		return searchForm;
	}

	public VerticalLayout getSearchResults(String strSearchParams) {
		if (be == null)
			be = new BE2(hmPerms);
		return be.queryBackEnd(strSearchParams);
	}

	private ArrayList<Object> addTfs(ArrayList<String> arrLTfCaptions,
			FormLayout searchForm) {

		ArrayList<Object> arrLTfs = new ArrayList<Object>();
		TextField tF;

		final ComboBox comboProfile = new ComboBox("Profile Type");
		comboProfile.setNullSelectionAllowed(false);

		comboProfile.addFocusListener(new FocusListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void focus(FocusEvent event) {
				addProfiles(comboProfile);

			}

		});

		// Set<Entry<Integer, String>> set = profToID.entrySet();

		/*
		 * for (Entry<Integer, String> e : set) {
		 * comboProfile.addItem(e.getKey());
		 * comboProfile.setItemCaption(e.getKey(), e.getValue()); }
		 */

		// comboProfile.select(0);

		arrLTfs.add(comboProfile);
		searchForm.addComponent(comboProfile);

		for (String tFCaption : arrLTfCaptions) {
			tF = new TextField(tFCaption);
			arrLTfs.add(tF);

			searchForm.addComponent(tF);
		}
		return arrLTfs;
	}

	private void addProfiles(ComboBox combo) {

		if (rs == null) {
			try {
				rs = new ReportingService();
			} catch (AxisFault e) {

				e.printStackTrace();
			}
		}

		try {
			Map<String, String> hmTemp = new HashMap<>();
			for (Profile profile : rs.getProfiles()) {
				hmTemp.put(profile.getProfilename(), profile.getProfileid());

			}

			hmProfiles = hmTemp;
			hmProfiles.put("ALL", "0");
			Set<Entry<String, String>> set = hmProfiles.entrySet();
			for (Entry<String, String> e : set) {
				Object key = e.getKey();
				combo.addItem(key);

			}

			combo.select("ALL");
			combo.setNullSelectionAllowed(false);

		} catch (RemoteException | DataServiceFault e) {

			e.printStackTrace();
		}

	}

}
