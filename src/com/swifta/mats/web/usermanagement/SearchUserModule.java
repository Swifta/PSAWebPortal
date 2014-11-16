package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;

import com.swifta.mats.web.WorkSpace;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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

	public SearchUserModule() {

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

		Label lbSearch = new Label("Search " + strUserType + " by: ");
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
		arrLTfCaptions.add(strUserType + " ID");
		arrLTfCaptions.add("Username");
		arrLTfCaptions.add("MSISDN");
		arrLTfCaptions.add("Company");
		arrLTfCaptions.add("First Name");
		arrLTfCaptions.add("Last Name");
		arrLTfCaptions.add("Others");

		searchForm.addComponent(searchUserHeader);
		final ArrayList<TextField> arrLTfs = addTfs(arrLTfCaptions, searchForm);
		Button btnSearch = new Button("Search");
		searchForm.addComponent(btnSearch);

		btnSearch.setIcon(FontAwesome.SEARCH);

		btnSearch.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -5894920456172825127L;

			@Override
			public void buttonClick(ClickEvent event) {
				StringBuilder strBuilder = new StringBuilder();
				for (TextField tF : arrLTfs) {

					strBuilder.append(tF.getCaption());
					strBuilder.append("=");
					strBuilder.append(tF.getValue());
					strBuilder.append("&");
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

	private ArrayList<TextField> addTfs(ArrayList<String> arrLTfCaptions,
			FormLayout searchForm) {

		TextField tF;
		ArrayList<TextField> arrLTfs = new ArrayList<TextField>();
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
