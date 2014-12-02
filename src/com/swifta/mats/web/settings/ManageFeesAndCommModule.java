package com.swifta.mats.web.settings;

import java.util.ArrayList;

import com.swifta.mats.web.usermanagement.BtnTabLike;
import com.swifta.mats.web.usermanagement.BtnTabLikeClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ManageFeesAndCommModule {

	public VerticalLayout getMenu(boolean boolAddHeaderStatus,
			boolean boolEditStatus, boolean hasOp) {

		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();

		HorizontalLayout cManageAndAddTab = new HorizontalLayout();

		BtnTabLike btnPersonal = new BtnTabLike("Fees", null);
		btnPersonal.setStyleName("btn_tab_like btn_tab_like_active");

		BtnTabLike btnAccount = new BtnTabLike("Commission", null);
		// BtnTabLike btnAuth = new BtnTabLike("Authentication", null);
		// BtnTabLike btnLog = new BtnTabLike("Log", null);
		cManageAndAddTab.addComponent(btnPersonal);
		cManageAndAddTab.addComponent(btnAccount);
		// cManageAndAddTab.addComponent(btnAuth);
		// cManageAndAddTab.addComponent(btnLog);

		final ArrayList<BtnTabLike> arrLTabBtns = new ArrayList<BtnTabLike>();
		arrLTabBtns.add(btnPersonal);
		arrLTabBtns.add(btnAccount);
		// arrLTabBtns.add(btnAuth);
		// arrLTabBtns.add(btnLog);

		btnPersonal.setEnabled(false);

		ArrayList<HorizontalLayout> arrLSubTabs = new ArrayList<HorizontalLayout>();

		HorizontalLayout cManUserSubMenu = new HorizontalLayout();

		arrLSubTabs.add(cManUserSubMenu);

		UI.getCurrent()
				.getSession()
				.setAttribute(
						WorkSpaceManageFeesAndComm.SESSION_WSMP_CUR_ACTION,
						WorkSpaceManageFeesAndComm.SESSION_VAR_WSMP_PERSONAL);

		/*
		 * String[] arrSessions = new String[]{
		 * ManageUserModule.SESSION_UMANAGE, SESSION_UDM_TABLE,
		 * SESSION_UDM_IS_LOG};
		 */

		String[] arrSessions = new String[] {
				WorkSpaceManageFeesAndComm.SESSION_WSMP_CUR_ACTION,
				WorkSpaceManageFeesAndComm.SESSION_UDM_IS_LOG };

		btnPersonal.addClickListener(new BtnTabLikeClickListener(false, false,
				arrLSubTabs, arrLTabBtns, null, null, "account_change_log",
				"001", hasOp, boolEditStatus, arrSessions,
				/*
				 * new
				 * String[]{ManageUserModule.SESSION_VAR_UMANAGE_USER_ACTIONS,
				 * SESSION_VAR_UDM_PER, SESSION_VAR_UDM_IS_LOG_FALSE}));
				 */
				new String[] {
						WorkSpaceManageFeesAndComm.SESSION_VAR_WSMP_PERSONAL,
						null }));

		btnAccount
				.addClickListener(new BtnTabLikeClickListener(
						false,
						false,
						arrLSubTabs,
						arrLTabBtns,
						null,
						null,
						"account_change_log",
						"001",
						hasOp,
						boolEditStatus,
						arrSessions,
						new String[] {
								WorkSpaceManageFeesAndComm.SESSION_VAR_WSMP_AUTH,
								null }));

		/*
		 * btnAuth.addClickListener(new BtnTabLikeClickListener( false, false,
		 * arrLSubTabs, arrLTabBtns, null, null, "account_change_log", "001",
		 * hasOp, boolEditStatus, arrSessions, new String[] {
		 * WorkSpaceManageFeesAndComm.SESSION_VAR_WSMP_AUTH, null }));
		 */

		cManageUserMenu.addComponent(cManageAndAddTab);
		// cManageUserMenu.addComponent(cManUserSubMenu);

		String strManBtnPref = "man";

		/*
		 * cManUserSubMenu = getAddUserSubMenu(btnLog, strManBtnPref,
		 * arrLTabBtns, cManUserSubMenu, arrLSubTabs, hasOp, boolEditStatus,
		 * null, null, null);
		 */
		// cManUserSubMenu.setSizeUndefined();

		BtnTabLike.btnPrevMenuState = BtnTabLike.btnTabCur;

		return cManageUserMenu;

	}

	private HorizontalLayout getAddUserSubMenu(BtnTabLike btnAddUser,
			String strBtnPref, ArrayList<BtnTabLike> arrLTabBtns,
			HorizontalLayout cAddUserSubMenu,
			ArrayList<HorizontalLayout> arrLAddUserSubTabs, boolean hasOp,
			boolean boolEditStatus, Object aum, String strSessionVar,
			String strSessionSub) {

		cAddUserSubMenu.setStyleName("c_sub_menu_invisible");
		cAddUserSubMenu.setSizeUndefined();

		BtnTabLike btnAct = new BtnTabLike("Account Activity Log", null);

		BtnTabLike btnAcc = new BtnTabLike("Account Change Log", null);

		btnAct.setStyleName("btn_tab_like btn_tab_like_active");
		btnAct.setEnabled(false);
		BtnTabLike.btnTabCur = btnAct;

		cAddUserSubMenu.addComponent(btnAct);
		cAddUserSubMenu.addComponent(btnAcc);

		final ArrayList<BtnTabLike> arrLSubTabBtns = new ArrayList<BtnTabLike>();
		arrLSubTabBtns.add(btnAct);
		arrLSubTabBtns.add(btnAcc);

		/*
		 * String[] arrSessions = new String[]{
		 * ManageUserModule.SESSION_UMANAGE, SESSION_UDM_TABLE, SESSION_UDM_LOG
		 * };
		 */

		/*
		 * String[] arrSessions = new String[]{
		 * ManageUserModule.SESSION_UMANAGE, SESSION_UDM_TABLE_LOG,
		 * SESSION_UDM_IS_LOG};
		 */

		String[] arrSessions = new String[] {
				WorkSpaceManageFeesAndComm.SESSION_UDM_TABLE_LOG,
				WorkSpaceManageFeesAndComm.SESSION_UDM_IS_LOG };

		// new String[]{WorkSpaceManageProfile.SESSION_VAR_WSMP_PERSONAL}));

		btnAct.addClickListener(new BtnTabLikeClickListener(false, false,
				arrLAddUserSubTabs, arrLSubTabBtns, null, aum,
				"account_change_log", "001", hasOp, boolEditStatus,
				arrSessions, new String[] {
						WorkSpaceManageFeesAndComm.SESSION_VAR_WSMP_ACT_LOG,
						WorkSpaceManageFeesAndComm.SESSION_VAR_UDM_LOG }));

		btnAcc.addClickListener(new BtnTabLikeClickListener(false, false,
				arrLAddUserSubTabs, arrLSubTabBtns, null, aum,
				"account_change_log", "001", hasOp, boolEditStatus,
				arrSessions, new String[] {
						WorkSpaceManageFeesAndComm.SESSION_VAR_WSMP_ACC_LOG,
						WorkSpaceManageFeesAndComm.SESSION_VAR_UDM_LOG }));

		btnAddUser
				.addClickListener(new BtnTabLikeClickListener(
						false,
						true,
						arrLAddUserSubTabs,
						arrLTabBtns,
						cAddUserSubMenu,
						aum,
						"activity_log",
						"001",
						hasOp,
						boolEditStatus,
						new String[] { WorkSpaceManageFeesAndComm.SESSION_UDM_IS_LOG },
						new String[] { WorkSpaceManageFeesAndComm.SESSION_VAR_UDM_LOG }));

		UI.getCurrent().getSession()
				.setAttribute(WorkSpaceManageFeesAndComm.SESSION_UDM_LOG, null);
		UI.getCurrent()
				.getSession()
				.setAttribute(WorkSpaceManageFeesAndComm.SESSION_UDM_TABLE_LOG,
						WorkSpaceManageFeesAndComm.SESSION_VAR_WSMP_ACT_LOG);

		return cAddUserSubMenu;
	}

}
