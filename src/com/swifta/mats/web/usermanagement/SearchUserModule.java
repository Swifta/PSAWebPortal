package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.swifta.mats.web.WorkSpace;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SearchUserModule {
	public static final String tbUsers = "user";
	public static final String SESSION_USER_ACTION = "session_action";
	public static final String SESSION_USER_TABLE = "users_table";
	public static final String SESSION_USER_TABLE_ROW_ID = "tb_rw_id";
	public static final String ACTION_DETAILS = "details";
	public static final String ACTION_EDIT = "edit";
	public static final String ACTION_LINK = "link";
	public static final String ACTION_DELETE = "delete";
	public static final String ACTION_MORE = "moreActions";

	public static final String SESSION_SEARCH_USER = "search_user";
	public static final String SESSION_VAR_SEARCH_USER_DEFAULT = "agent";
	public static final String SESSION_SEARCH_USER_PARAM = "search_user_param";
	ArrayList<Object> arrLTfs;
	/*
	 * public static final String SESSION_VAR_SEARCH_USER = "merchant"; public
	 * static final String SESSION_VAR_SEARCH_USER = "agent"; public static
	 * final String SESSION_VAR_SEARCH_USER = "agent"; public static final
	 * String SESSION_VAR_SEARCH_USER = "agent"; public static final String
	 * SESSION_VAR_SEARCH_USER = "agent"; public static final String
	 * SESSION_VAR_SEARCH_USER = "agent";
	 */

	private FormLayout searchC;
	private VerticalLayout searchResultsC;

	Window popup;
	private Map<Integer, String> profToID;

	public SearchUserModule() {

		/*
		 * profToID = new HashMap<>(); profToID.put(0, "ALL"); profToID.put(1,
		 * "BACK OFFICE"); profToID.put(2, "SERVICE PROVIDER"); profToID.put(3,
		 * "FINANCIAL CONTROLLER"); profToID.put(4, "CUSTOMER CARE");
		 * profToID.put(5, "MOBILE MONEY"); profToID.put(6, "SUPER AGENT");
		 * profToID.put(7, "SUB AGENT"); profToID.put(8, "DEPOSIT ONLY");
		 * profToID.put(9, "DEPOSIT & WITHDRAW"); profToID.put(10,
		 * "WITHDRAW ONLY"); profToID.put(11, "DEALER"); profToID.put(12,
		 * "CASH"); profToID.put(13, "MATS ACCOUNT"); profToID.put(14,
		 * "MATS USER");
		 */
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
		arrLTfCaptions.add("E-mail");
		// arrLTfCaptions.add("Company");
		// arrLTfCaptions.add("First Name");
		// arrLTfCaptions.add("Last Name");
		// arrLTfCaptions.add("Others");

		searchForm.addComponent(searchUserHeader);
		arrLTfs = addTfs(arrLTfCaptions, searchForm);

		Button btnSearch = new Button("Search");
		searchForm.addComponent(btnSearch);

		btnSearch.setIcon(FontAwesome.SEARCH);

		btnSearch.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -5894920456172825127L;

			@Override
			public void buttonClick(ClickEvent event) {
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

				UI.getCurrent()
						.getSession()
						.setAttribute(
								ManageUserModule.SESSION_VAR_UMANAGE_SEARCH,
								null);
				UI.getCurrent().getSession()
						.setAttribute(UserDetailsModule.SESSION_UDM, null);
				UI.getCurrent()
						.getSession()
						.setAttribute(
								ManageUserModule.SESSION_UMANAGE,
								ManageUserModule.SESSION_VAR_UMANAGE_SEARCH_RESULTS);
				UI.getCurrent().getSession()
						.setAttribute(SESSION_SEARCH_USER_PARAM, strParams);

				if (WorkSpace.wsmu != null)
					WorkSpace.wsmu.wsmuModifier();
			}
		});

		return searchForm;
	}

	private VerticalLayout getSearchResults(String strSearchParams) {
		BE2 be = new BE2();
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

	public Object sumModifier(String strSessionSearch, HorizontalLayout contentC) {

		String strUserType = (String) UI.getCurrent().getSession()
				.getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);

		if (strSessionSearch
				.equals(ManageUserModule.SESSION_VAR_UMANAGE_SEARCH)) {

			if (searchC != null) {
				contentC.removeComponent(searchC);
			}

			searchC = getSearchForm(strUserType);
			contentC.addComponent(searchC);

			searchC.removeStyleName("c_search_user");
			contentC.setComponentAlignment(searchC, Alignment.MIDDLE_CENTER);
			contentC.setSizeFull();
			contentC.setSpacing(false);
			contentC.setMargin(true);
			return searchC;

		} else if (strSessionSearch
				.equals(ManageUserModule.SESSION_VAR_UMANAGE_SEARCH_RESULTS)) {
			searchC.setSizeUndefined();
			searchC.setStyleName("c_search_user");
			contentC.setComponentAlignment(searchC, Alignment.TOP_RIGHT);

			String strSessionSearchParam = (String) UI.getCurrent()
					.getSession()
					.getAttribute(SearchUserModule.SESSION_SEARCH_USER_PARAM);
			if (strSessionSearchParam != null) {

				if (searchResultsC != null) {
					contentC.removeComponent(searchResultsC);
				}
				searchResultsC = getSearchResults(strSessionSearchParam);
				contentC.addComponent(searchResultsC);
				searchResultsC.setSizeUndefined();
				// searchResultsC.setStyleName("s_r_c");
				contentC.setComponentAlignment(searchResultsC,
						Alignment.TOP_LEFT);
				contentC.setSizeUndefined();
				contentC.setMargin(new MarginInfo(true, false, true, false));
				contentC.setSpacing(false);

			}

			return searchResultsC;
		}

		return null;

	}

}
