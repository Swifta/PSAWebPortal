package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class SearchUserModule {

	private ArrayList<Object> arrLTfs;
	private boolean isSearchURL = false;
	private String curURL = null;

	private Map<Integer, String> profToID;
	private BE2 be;

	public SearchUserModule() {

		profToID = new HashMap<>();
		profToID.put(0, "ALL");
		profToID.put(1, "MATS_ADMIN_USER_PROFILE");
		profToID.put(3, "MATS_FINANCIAL_CONTROLLER_USER_PROFILE");
		profToID.put(4, "MATS_CUSTOMER_CARE_USER_PROFILE");
		profToID.put(6, "MATS_SUPER_AGENT_USER_PROFILE");
		profToID.put(7, "MATS_SUB_AGENT_USER_PROFILE");
		profToID.put(11, "MATS_DEALER_USER_PROFILE");
		profToID.put(15, "MATS_SERVICE_PROVIDER_USER_PROFILE");

	}

	public void addFilters(String strParams) {

		if (be == null)
			be = new BE2();
		be.addFilters(strParams);
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
					curURL = UI.getCurrent().getPage().getUriFragment();
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
						String strProf = profToID.get(combo.getValue());
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
			be = new BE2();
		return be.queryBackEnd(strSearchParams);
	}

	private ArrayList<Object> addTfs(ArrayList<String> arrLTfCaptions,
			FormLayout searchForm) {

		ArrayList<Object> arrLTfs = new ArrayList<Object>();
		TextField tF;

		ComboBox comboProfile = new ComboBox("Profile Type");

		Set<Entry<Integer, String>> set = profToID.entrySet();

		for (Entry<Integer, String> e : set) {
			comboProfile.addItem(e.getKey());
			comboProfile.setItemCaption(e.getKey(), e.getValue());
		}

		comboProfile.select(0);

		arrLTfs.add(comboProfile);
		searchForm.addComponent(comboProfile);

		for (String tFCaption : arrLTfCaptions) {
			tF = new TextField(tFCaption);
			arrLTfs.add(tF);

			searchForm.addComponent(tF);
		}
		return arrLTfs;
	}

}
