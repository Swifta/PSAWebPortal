package com.swifta.mats.web.usermanagement;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class ManageUserModule {

	public ManageUserModule() {
	}

	public VerticalLayout getManageUserMenu(boolean boolAddHeaderStatus,
			boolean boolEditStatus, boolean hasOp) {

		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();

		HorizontalLayout cManageAndAddTab = new HorizontalLayout();

		BtnTabLike btnManUser = new BtnTabLike("Manage", null);
		btnManUser.setStyleName("btn_tab_like btn_tab_like_active");

		BtnTabLike btnAddUser = new BtnTabLike("Add New", null);
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
