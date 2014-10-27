package com.swifta.mats.web.accountprofile;

import java.util.ArrayList;

import com.swifta.mats.web.WorkSpace;
import com.swifta.mats.web.usermanagement.BtnTabLike;
import com.swifta.mats.web.usermanagement.BtnTabLikeClickListener;
import com.swifta.mats.web.usermanagement.WorkSpaceManageUser;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ManageProfileModule {
	
	
	
	
	public VerticalLayout getMenu(HorizontalLayout cContent){
		VerticalLayout cManageUserMenu = new VerticalLayout();
		cManageUserMenu.setMargin(new MarginInfo(false, true, false, true));
		cManageUserMenu.setStyleName("c_u_manage_menu");
		cManageUserMenu.setSizeUndefined();
		
		
		
		HorizontalLayout cManageAndAddTab = new HorizontalLayout();
		
		BtnTabLike btnProfile = new BtnTabLike("Profile", null);
		BtnTabLike btnAuth = new BtnTabLike("Authentication", null);
		BtnTabLike btnLog = new BtnTabLike("Log", null);
		btnProfile.setStyleName("btn_tab_like btn_tab_like_active");
		
		
		//edit...
		
		cManageAndAddTab.addComponent(btnProfile);
		cManageAndAddTab.addComponent(btnAuth);
		cManageAndAddTab.addComponent(btnLog);
		
		final ArrayList<BtnTabLike> arrLTabBtns = new ArrayList<BtnTabLike>();
		arrLTabBtns.add(btnProfile);
		arrLTabBtns.add(btnAuth);
		arrLTabBtns.add(btnLog);
		
		//btnManUser.addClickListener(new BtnTabLikeClickListener(true, arrLTabBtns, new String[]{WorkSpaceManageUser.SESSION_WORK_AREA, ManageUserModule.SESSION_UMANAGE}, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_MANAGE_USER, ManageUserModule.SESSION_VAR_UMANAGE_SEARCH}));
		btnProfile.setEnabled(false);
		
		//btnAddUser.addClickListener(new BtnTabLikeClickListener(true, arrLTabBtns, new String[]{WorkSpaceManageUser.SESSION_WORK_AREA}, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_ADD_USER}));
		
		ArrayList<HorizontalLayout> arrLSubTabs = new ArrayList<HorizontalLayout>();
		
		HorizontalLayout cLogSubMenu = new HorizontalLayout();
		//HorizontalLayout cAddUserSubMenu = new HorizontalLayout();
		
		
		arrLSubTabs.add(cLogSubMenu);
		//arrLSubTabs.add(cAddUserSubMenu);
		
		
		cManageUserMenu.addComponent(cManageAndAddTab);
		cManageUserMenu.addComponent(cLogSubMenu);
		//cManageUserMenu.addComponent(cAddUserSubMenu);
		
		btnProfile.addClickListener(new BtnTabLikeClickListener(true, false, arrLSubTabs, arrLTabBtns, cContent, this,
				"personal", "001", false, false, new String[]{WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION}, new String[]{WorkSpaceManageProfile.SESSION_VAR_WSMP_PERSONAL} ));
		btnAuth.addClickListener(new BtnTabLikeClickListener(true, false, arrLSubTabs, arrLTabBtns, cContent, this,
				"personal", "001", false, false , new String[]{ WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION}, new String[]{WorkSpaceManageProfile.SESSION_VAR_WSMP_AUTH}));

		
		cLogSubMenu = getUserLogSubMenu(btnLog, arrLTabBtns, cLogSubMenu, arrLSubTabs,cContent, false, false);
		//cAddUserSubMenu = getAddUserSubMenu(btnAuth, strAddUserBtnPref,  arrLTabBtns, cAddUserSubMenu, cContent, arrLSubTabs, false, false, this);
		//cAddUserSubMenu = getAddUserSubMenu(btnLog, strAddUserBtnLog,  arrLTabBtns, cAddUserSubMenu, cContent, arrLSubTabs, false, false, this);

		//cLogSubMenu.setStyleName("c_u_sub_menu_invisible");
		cLogSubMenu.setSizeUndefined();
		cLogSubMenu.setSizeUndefined();
		
		UI.getCurrent().getSession().setAttribute(WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION, WorkSpaceManageProfile.SESSION_VAR_WSMP_PERSONAL);
		if(WorkSpace.wsmp != null) WorkSpace.wsmp.wsmpModifier();
		
		
		return cManageUserMenu;
	}



public HorizontalLayout getAddUserSubMenu(BtnTabLike btnAddUser, String strBtnPref, ArrayList<BtnTabLike> arrLTabBtns,  HorizontalLayout cAddUserSubMenu, HorizontalLayout cContent, ArrayList<HorizontalLayout> arrLAddUserSubTabs, boolean hasOp, boolean boolEditStatus, Object aum){
	//btnLog.addClickListener(new BtnTabLikeClickListener(false, arrLTabBtns, cPerAccAuthInfo, udm, "log", "001" ));
	//VerticalLayout cLog = new VerticalLayout();
	//cLog.setSizeUndefined();
	cAddUserSubMenu.setStyleName("c_sub_menu_invisible");
	cAddUserSubMenu.setSizeUndefined();
	
	String strBtnIDAgent = "agent";
	String strBtnIDMerchant = "agent";
	
	
	BtnTabLike btnAddAgent = new BtnTabLike("Activity Log",  strBtnPref+"_"+strBtnIDAgent);
	
	BtnTabLike btnAddMerchant = new BtnTabLike("Account Log", strBtnPref+"_"+strBtnIDMerchant);
	
	
	btnAddAgent.setStyleName("btn_tab_like btn_tab_like_active");
	btnAddAgent.setEnabled(false);
	
	
	cAddUserSubMenu.addComponent(btnAddAgent);
	cAddUserSubMenu.addComponent(btnAddMerchant);
	
	final ArrayList<BtnTabLike> arrLSubTabBtns = new ArrayList<BtnTabLike>();
	arrLSubTabBtns.add(btnAddAgent);
	arrLSubTabBtns.add(btnAddMerchant);
	
	
	
	
	
	
	/*btnLog.addClickListener(new BtnTabLikeClickListener(false, true, arrLTabBtns, cUserLogMenu, cPerAccAuthInfo, udm, 
			"activity_log", "001"));*/
	btnAddAgent.addClickListener(new BtnTabLikeClickListener(false, false, arrLAddUserSubTabs,  arrLSubTabBtns, cContent, aum,
			"account_change_log", "001", hasOp, boolEditStatus, WorkSpaceManageUser.SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE ));
	
	btnAddMerchant.addClickListener(new BtnTabLikeClickListener(false, false, arrLAddUserSubTabs, arrLSubTabBtns,  cContent, aum,
			"activity_log", "001", hasOp, boolEditStatus, WorkSpaceManageUser.SESSION_VAR_WORK_AREA_USER_MERCHANT ));
	
	
	
	
		
	
	
	return cAddUserSubMenu;
}


private HorizontalLayout getUserLogSubMenu(BtnTabLike btnLog, ArrayList<BtnTabLike> arrLTabBtns, HorizontalLayout cUserLogMenu, ArrayList<HorizontalLayout> arrLSubTabs,HorizontalLayout cContent, boolean hasOp, boolean boolEditStatus){
	
	cUserLogMenu.setStyleName("c_sub_menu_invisible");
	cUserLogMenu.setSizeUndefined();
	
	String strBtnIDActLog = "act";
	String strBtnIDAccChangeLog = "acc";
	
	
	BtnTabLike btnAccChangeLog = new BtnTabLike("Activity Log", strBtnIDActLog);
	btnAccChangeLog.setStyleName("btn_tab_like btn_tab_like_active");
	btnAccChangeLog.setEnabled(false);
	
	BtnTabLike btnActLog = new BtnTabLike("Account Change Log", strBtnIDAccChangeLog);
	cUserLogMenu.addComponent(btnAccChangeLog);
	cUserLogMenu.addComponent(btnActLog);
	
	final ArrayList<BtnTabLike> arrLSubTabBtns = new ArrayList<BtnTabLike>();
	arrLSubTabBtns.add(btnActLog);
	arrLSubTabBtns.add(btnAccChangeLog);
	
	
	
	btnActLog.addClickListener(new BtnTabLikeClickListener(true, false, arrLSubTabs, arrLSubTabBtns,  cContent, this,
			"activity_log", "001", hasOp, boolEditStatus, new String[]{WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION}, new String[]{WorkSpaceManageProfile.SESSION_VAR_WSMP_ACT_LOG} ));
	btnAccChangeLog.addClickListener(new BtnTabLikeClickListener(true, false, arrLSubTabs,  arrLSubTabBtns, cContent, this,
			"account_change_log", "001", hasOp, boolEditStatus, new String[]{WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION}, new String[]{WorkSpaceManageProfile.SESSION_VAR_WSMP_ACC_LOG} ));
	
	btnLog.addClickListener(new BtnTabLikeClickListener(false, true, arrLTabBtns, cUserLogMenu, cContent, this,
			"activity_log", "001", hasOp, boolEditStatus));
	//cUserLogMenu.setVisible(false);
	//cLog.addComponent(cUserLogMenu);
	
	
	
	return cUserLogMenu;
}

























}
