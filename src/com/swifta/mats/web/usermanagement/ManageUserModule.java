package com.swifta.mats.web.usermanagement;

import java.util.HashMap;

import com.swifta.mats.web.Initializer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class ManageUserModule {

	private HashMap<String, String> hmPerms;

	public ManageUserModule() {

	}

	private VerticalLayout getManageUserMenu(boolean boolAddHeaderStatus,
			boolean boolEditStatus, boolean hasOp) {

		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();

		HorizontalLayout cManageAndAddTab = new HorizontalLayout();

		BtnTabLike btnManUser = new BtnTabLike("Manage", null);
		btnManUser.setStyleName("btn_tab_like btn_tab_like_active");

		BtnTabLike btnAddUser = new BtnTabLike("Add New", null);

		BtnTabLike btnTemp = null;

		if (Initializer.setUserPermissions.contains(hmPerms.get("man"))) {
			cManageAndAddTab.addComponent(btnManUser);
			btnTemp = btnManUser;
		}
		if (Initializer.setUserPermissions.contains(hmPerms.get("register"))) {
			cManageAndAddTab.addComponent(btnAddUser);
		}

		if (btnTemp == null)
			btnTemp = btnAddUser;

		btnTemp.setEnabled(false);
		btnTemp.setStyleName("btn_tab_like btn_tab_like_active");

		// ArrayList<HorizontalLayout> arrLSubTabs = new
		// ArrayList<HorizontalLayout>();

		// HorizontalLayout cManUserSubMenu = new HorizontalLayout();
		// HorizontalLayout cAddUserSubMenu = new HorizontalLayout();

		// arrLSubTabs.add(cManUserSubMenu);
		// arrLSubTabs.add(cAddUserSubMenu);

		cManageUserMenu.addComponent(cManageAndAddTab);

		return cManageUserMenu;

	}

}
