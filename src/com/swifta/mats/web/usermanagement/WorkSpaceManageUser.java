package com.swifta.mats.web.usermanagement;

import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class WorkSpaceManageUser {
	/**
	 * 
	 */

	public static final String WORK_AREA = "";
	HorizontalLayout contentC;
	VerticalLayout uf;
	FormLayout searchC;
	VerticalLayout searchResultsC;
	AddUserModule aum;
	ManageUserModule mum;
	UserDetailsModule udm;
	VerticalLayout cParentLayout;
	VerticalLayout cuDetails;
	VerticalLayout mm;
	private VerticalLayout auf;
	SearchUserModule sum;
	String curSessionUManage;

	// static final String WORK_AREA = "work_area";
	public final static String SESSION_WORK_AREA_USER_TYPE = "user_type";
	public final static String SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE = "Agent";
	public final static String SESSION_VAR_WORK_AREA_USER_MERCHANT = "Merchant";
	public final static String SESSION_VAR_WORK_AREA_USER_DEALER = "Dealer";
	public final static String SESSION_VAR_WORK_AREA_USER_PARTNER = "Partner";
	public final static String SESSION_VAR_WORK_AREA_USER_BA = "BA";
	public final static String SESSION_VAR_WORK_AREA_USER_CCO = "CCO";

	public final static String SESSION_WORK_AREA = "session_work_area";
	public final static String SESSION_VAR_WORK_AREA_ADD_USER = "add_user";
	public final static String SESSION_VAR_WORK_AREA_MANAGE_USER = "manage_user";
	HorizontalLayout csman = new HorizontalLayout();
	HorizontalLayout caddman = new HorizontalLayout();
	FormLayout cs;

	public WorkSpaceManageUser() {
		setCoreUI();
		addModifier();

	}

	private void setCoreUI() {

		cParentLayout = new VerticalLayout();

		contentC = new HorizontalLayout();
		contentC.setWidthUndefined();
		contentC.setHeightUndefined();
		contentC.setStyleName("content_c");

		sum = new SearchUserModule();
		mm = getManageUserMenu(false);
		mm.setSizeUndefined();
		cs = sum.getSearchForm("x");
		csman.addComponent(cs);
		csman.setSizeFull();
		csman.setComponentAlignment(cs, Alignment.TOP_CENTER);

		contentC.addComponent(csman);

		contentC.setSizeFull();

		cParentLayout.addComponent(mm);
		cParentLayout.setComponentAlignment(mm, Alignment.TOP_CENTER);
		cParentLayout.addComponent(contentC);

	}

	public VerticalLayout getWorkSpaceManageUser() {
		return cParentLayout;

	}

	private VerticalLayout getManageUserMenu(boolean boolEditStatus) {

		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();

		HorizontalLayout cManageAndAddTab = new HorizontalLayout();

		final BtnTabLike btnManUser = new BtnTabLike("Manage", "man");
		btnManUser.setStyleName("btn_tab_like btn_tab_like_active");

		final BtnTabLike btnAddUser = new BtnTabLike("Add New", "add");
		cManageAndAddTab.addComponent(btnManUser);
		cManageAndAddTab.addComponent(btnAddUser);
		btnManUser.setEnabled(false);
		cManageUserMenu.addComponent(cManageAndAddTab);

		btnManUser.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				btnAddUser.setEnabled(true);
				btnManUser.setEnabled(false);
				btnManUser.setStyleName("btn_tab_like btn_tab_like_active");
				btnAddUser.setStyleName("btn_tab_like");
				caddman = (HorizontalLayout) contentC.getComponent(0);
				contentC.replaceComponent(caddman, csman);

			}
		});

		btnAddUser.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				btnAddUser.setEnabled(false);
				btnManUser.setEnabled(true);
				btnAddUser.setStyleName("btn_tab_like btn_tab_like_active");
				btnManUser.setStyleName("btn_tab_like");

				csman = (HorizontalLayout) contentC.getComponent(0);
				contentC.replaceComponent(csman, caddman);

				if (aum == null) {
					aum = new AddUserModule();
					auf = aum.getAddUserForm();
					caddman.addComponent(auf);
					caddman.setSizeFull();
					caddman.setComponentAlignment(auf, Alignment.TOP_CENTER);
				}

			}
		});

		return cManageUserMenu;

	}

	private void addModifier() {
		UI.getCurrent()
				.getPage()
				.addUriFragmentChangedListener(
						new Page.UriFragmentChangedListener() {

							private static final long serialVersionUID = 1L;

							@Override
							public void uriFragmentChanged(
									UriFragmentChangedEvent event) {

								modifier(UI.getCurrent().getPage()
										.getUriFragment());
							}
						});
	}

	private void modifier(String frag) {

		String param = frag.substring(frag.indexOf('?') + 1);
		if (param == null)
			return;

		String aParam[] = param.split("&");
		if (aParam.length < 2)
			return;
		String action = aParam[0].split("=")[1];

		StringBuilder strb = new StringBuilder();
		for (int i = 1; i < aParam.length; i++) {
			strb.append(aParam[i]);
			strb.append("&");
		}

		switch (action) {
		case "search": {
			csman.setComponentAlignment(cs, Alignment.TOP_LEFT);
			VerticalLayout csr = sum.getSearchResults(strb.toString());
			csman.addComponent(csr);
			csman.setExpandRatio(csr, 1.0f);
			csman.setStyleName("csman");
			csman.removeComponent(cs);
			break;
		}
		case "search_results": {
			csman.removeAllComponents();
			VerticalLayout csr = sum.getSearchResults(strb.toString());
			csman.addComponent(csr);
			csman.setExpandRatio(csr, 1.0f);
			break;
		}
		case "filter_search_results": {

			sum.addFilters(strb.toString());
			break;
		}
		}

	}
}