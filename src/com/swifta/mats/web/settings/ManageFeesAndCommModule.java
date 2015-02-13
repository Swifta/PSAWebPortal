package com.swifta.mats.web.settings;

import com.swifta.mats.web.usermanagement.BtnTabLike;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ManageFeesAndCommModule {

	public static BtnTabLike btnComm;
	public static BtnTabLike btnFees;
	public static BtnTabLike btnExisting;
	private boolean isSettingsURL = false;
	private String curURL = null;

	public VerticalLayout getMenu(boolean boolAddHeaderStatus,
			boolean boolEditStatus, boolean hasOp) {

		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();

		HorizontalLayout cManageAndAddTab = new HorizontalLayout();

		final BtnTabLike btnPersonal = new BtnTabLike("Fees", null);
		btnFees = btnPersonal;
		btnPersonal.setStyleName("btn_tab_like btn_tab_like_active");

		final BtnTabLike btnAccount = new BtnTabLike("Commission", null);
		btnComm = btnAccount;

		final BtnTabLike btnAuth = new BtnTabLike("Existing", null);
		btnExisting = btnAuth;
		// btnAuth.setEnabled(false);

		cManageAndAddTab.addComponent(btnPersonal);
		cManageAndAddTab.addComponent(btnAccount);
		cManageAndAddTab.addComponent(btnAuth);

		btnPersonal.setEnabled(false);
		cManageUserMenu.addComponent(cManageAndAddTab);

		btnPersonal.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				btnPersonal.setStyleName("btn_tab_like btn_tab_like_active ");
				btnAccount.setStyleName("btn_tab_like");
				btnExisting.setStyleName("btn_tab_like");

				btnPersonal.setEnabled(false);
				btnAccount.setEnabled(true);
				btnExisting.setEnabled(true);

				if (!isSettingsURL) {
					String s = UI.getCurrent().getPage().getUriFragment();
					curURL = (s.indexOf('?') == -1) ? s + "/" : s.substring(0,
							s.indexOf('?'));
					isSettingsURL = true;
				}

				String url = curURL + "?action=Fees";

				if (WorkSpaceManageFeesAndComm.prevSearchFrag.contains(url))
					WorkSpaceManageFeesAndComm.prevSearchFrag.remove(url);
				UI.getCurrent().getPage().setUriFragment(url);

			}
		});

		btnAccount.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				btnAccount.setStyleName("btn_tab_like btn_tab_like_active ");
				btnPersonal.setStyleName("btn_tab_like");
				btnAuth.setStyleName("btn_tab_like");

				btnAccount.setEnabled(false);
				btnPersonal.setEnabled(true);
				btnAuth.setEnabled(true);

				if (!isSettingsURL) {
					String s = UI.getCurrent().getPage().getUriFragment();
					curURL = (s.indexOf('?') == -1) ? s + "/" : s.substring(0,
							s.indexOf('?'));
					isSettingsURL = true;
				}

				String url = curURL + "?action=Commission";

				if (WorkSpaceManageFeesAndComm.prevSearchFrag.contains(url))
					WorkSpaceManageFeesAndComm.prevSearchFrag.remove(url);
				UI.getCurrent().getPage().setUriFragment(url);

			}
		});

		btnAuth.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				btnAuth.setStyleName("btn_tab_like btn_tab_like_active ");
				btnAccount.setStyleName("btn_tab_like");
				btnPersonal.setStyleName("btn_tab_like");

				btnAuth.setEnabled(false);
				btnAccount.setEnabled(true);
				btnPersonal.setEnabled(true);

				if (!isSettingsURL) {
					String s = UI.getCurrent().getPage().getUriFragment();
					curURL = (s.indexOf('?') == -1) ? s + "/" : s.substring(0,
							s.indexOf('?'));
					isSettingsURL = true;
				}

				String url = curURL + "?action=existing";

				if (WorkSpaceManageFeesAndComm.prevSearchFrag.contains(url))
					WorkSpaceManageFeesAndComm.prevSearchFrag.remove(url);
				UI.getCurrent().getPage().setUriFragment(url);

			}
		});

		return cManageUserMenu;

	}

}
