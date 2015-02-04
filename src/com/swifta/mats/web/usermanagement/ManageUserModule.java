package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ManageUserModule {

	public static final String DEFAULT_UMANAGE_SESSION_VAR = null;
	public static final String SESSION_UMANAGE = "umanage_session";
	public static final String SESSION_VAR_UMANAGE_SEARCH = "search_user";
	public static final String SESSION_VAR_UMANAGE_SEARCH_RESULTS = "search_results";
	public static final String SESSION_VAR_UMANAGE_USER_ACTIONS = "user_actions";

	HorizontalLayout cPerAccAuthInfo;

	private static final String btnIDAddUser = "add_user";
	private static final String strBtnIDAgent = "agent";
	private static final String strBtnIDMerchant = "merchant";
	private static final String strBtnIDDealer = "dealer";
	private static final String strBtnIDPartner = "partner";
	private static final String strBtnIDBA = "ba";
	private static final String strBtnIDCCO = "cco";
	private static final String btnIDManageUser = "manage_user";

	// private FormLayout uDetailsForm;
	ArrayList<BtnTabLike> arrLTabBtns;
	Window popup;
	VerticalLayout cPopupMsg;

	// private TextField tfUname;

	public ManageUserModule() {
	}

	public VerticalLayout getManageUserMenu(boolean boolAddHeaderStatus,
			boolean boolEditStatus, boolean hasOp) {

		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();

		HorizontalLayout cManageAndAddTab = new HorizontalLayout();

		BtnTabLike btnManUser = new BtnTabLike("Manage", btnIDManageUser);
		btnManUser.setStyleName("btn_tab_like btn_tab_like_active");

		UI.getCurrent()
				.getSession()
				.setAttribute(
						WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE,
						WorkSpaceManageUser.SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE);

		BtnTabLike btnAddUser = new BtnTabLike("Add New", btnIDAddUser);
		cManageAndAddTab.addComponent(btnManUser);
		cManageAndAddTab.addComponent(btnAddUser);

		// final ArrayList<BtnTabLike> arrLTabBtns = new
		// ArrayList<BtnTabLike>();
		// arrLTabBtns.add(btnAddUser);
		// arrLTabBtns.add(btnManUser);

		btnManUser.setEnabled(false);

		// ArrayList<HorizontalLayout> arrLSubTabs = new
		// ArrayList<HorizontalLayout>();

		// HorizontalLayout cManUserSubMenu = new HorizontalLayout();
		// HorizontalLayout cAddUserSubMenu = new HorizontalLayout();

		// arrLSubTabs.add(cManUserSubMenu);
		// arrLSubTabs.add(cAddUserSubMenu);

		cManageUserMenu.addComponent(cManageAndAddTab);
		// cManageUserMenu.addComponent(cManUserSubMenu);
		// cManageUserMenu.addComponent(cAddUserSubMenu);

		// String strManBtnPref = "man";
		// String strAddUserBtnPref = "add";
		// String strSessionVarAddUser =
		// WorkSpaceManageUser.SESSION_VAR_WORK_AREA_ADD_USER;
		// String strSessionVarManageUser =
		// WorkSpaceManageUser.SESSION_VAR_WORK_AREA_MANAGE_USER;
		// String strSessionSub = ManageUserModule.SESSION_VAR_UMANAGE_SEARCH;

		// cManUserSubMenu = getAddUserSubMenu(btnManUser, strManBtnPref,
		// arrLTabBtns, cManUserSubMenu, arrLSubTabs, hasOp,
		// boolEditStatus, null, strSessionVarManageUser, strSessionSub);
		// cAddUserSubMenu = getAddUserSubMenu(btnAddUser, strAddUserBtnPref,
		// arrLTabBtns, cAddUserSubMenu, arrLSubTabs, hasOp,
		// boolEditStatus, null, strSessionVarAddUser, null);

		// cManUserSubMenu.setStyleName("c_u_sub_menu_visible");
		// cManUserSubMenu.setSizeUndefined();
		// cAddUserSubMenu.setSizeUndefined();

		return cManageUserMenu;

	}

}
