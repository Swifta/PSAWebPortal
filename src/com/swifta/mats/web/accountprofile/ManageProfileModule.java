package com.swifta.mats.web.accountprofile;

import com.swifta.mats.web.usermanagement.BtnTabLike;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class ManageProfileModule {

	public VerticalLayout getMenu() {

		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();
		HorizontalLayout cManageAndAddTab = new HorizontalLayout();
		final BtnTabLike btnPersonal = new BtnTabLike("Personal", null);
		btnPersonal.setStyleName("btn_tab_like btn_tab_like_active");

		final BtnTabLike btnAccount = new BtnTabLike("Account", null);
		final BtnTabLike btnAuth = new BtnTabLike("Authentication", null);
		BtnTabLike btnLog = new BtnTabLike("Log", null);
		cManageAndAddTab.addComponent(btnPersonal);
		cManageAndAddTab.addComponent(btnAccount);
		cManageAndAddTab.addComponent(btnAuth);
		cManageAndAddTab.addComponent(btnLog);
		btnPersonal.setEnabled(false);
		cManageUserMenu.addComponent(cManageAndAddTab);

		return cManageUserMenu;

	}

}
