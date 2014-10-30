package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;

import com.swifta.mats.web.WorkSpace;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
	UserDetailsModule udm;
	private static final String btnIDPersonal = "user_details_personal";
	private static final String btnIDAccount = "user_details_account";
	private static final String btnIDAuth = "user_details_auth";
	private static final String btnIDAddUser = "add_user";
	private static final String btnIDLog = "user_log";
	
	private static final String strBtnIDAgent = "agent";
	private static final String strBtnIDMerchant = "merchant";
	private static final String strBtnIDDealer = "dealer";
	private static final String strBtnIDPartner = "partner";
	private static final String strBtnIDBA = "ba";
	private static final String strBtnIDCCO = "cco";
	private String btnIDActLog;
	private String btnIDAccChangeLog;
	private static final String btnIDManageUser= "manage_user";
	
	
	
	//private FormLayout uDetailsForm;
	ArrayList<BtnTabLike> arrLTabBtns;
	Window popup;
	VerticalLayout cPopupMsg;
	//private TextField tfUname;
	
	
	
	public ManageUserModule(){
	}
	
	
	
	public VerticalLayout getSearchResults(String strSearchParams){
		SearchUserModule sum = new SearchUserModule();
		return sum.getSearchResults(strSearchParams);
	}
	
	public VerticalLayout getUserDetailsContainer(){
		UserDetailsModule udm = new UserDetailsModule();
		return udm.getUserDetailsContainer("personal", "001", false, false);
	}
		
	
	

	
	
	
	
	

		
		public VerticalLayout getManageUserMenu(boolean boolAddHeaderStatus, boolean boolEditStatus, boolean hasOp, Object aum){
			
			if(!boolAddHeaderStatus){
				
				
				VerticalLayout cManageUserMenu = new VerticalLayout();
				cManageUserMenu.setStyleName("c_u_manage_menu");
				cManageUserMenu.setSizeUndefined();
				
				HorizontalLayout cManageAndAddTab = new HorizontalLayout();
				
				BtnTabLike btnManUser = new BtnTabLike("Manage", btnIDManageUser);
				btnManUser.setStyleName("btn_tab_like btn_tab_like_active");
				
				UI.getCurrent().getSession().setAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE, WorkSpaceManageUser.SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE);
				//edit...
				
				BtnTabLike btnAddUser = new BtnTabLike("Add New", btnIDAddUser);
				cManageAndAddTab.addComponent(btnManUser);
				cManageAndAddTab.addComponent(btnAddUser);
				
				final ArrayList<BtnTabLike> arrLTabBtns = new ArrayList<BtnTabLike>();
				arrLTabBtns.add(btnAddUser);
				arrLTabBtns.add(btnManUser);
				
				
				//btnManUser.addClickListener(new BtnTabLikeClickListener(true, arrLTabBtns, new String[]{WorkSpaceManageUser.SESSION_WORK_AREA, ManageUserModule.SESSION_UMANAGE}, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_MANAGE_USER, ManageUserModule.SESSION_VAR_UMANAGE_SEARCH}));
				btnManUser.setEnabled(false);
				
				//btnAddUser.addClickListener(new BtnTabLikeClickListener(true, arrLTabBtns, new String[]{WorkSpaceManageUser.SESSION_WORK_AREA}, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_ADD_USER}));
				
				ArrayList<HorizontalLayout> arrLSubTabs = new ArrayList<HorizontalLayout>();
				
				HorizontalLayout cManUserSubMenu = new HorizontalLayout();
				HorizontalLayout cAddUserSubMenu = new HorizontalLayout();
				
				
				arrLSubTabs.add(cManUserSubMenu);
				arrLSubTabs.add(cAddUserSubMenu);
				
				
				cManageUserMenu.addComponent(cManageAndAddTab);
				cManageUserMenu.addComponent(cManUserSubMenu);
				cManageUserMenu.addComponent(cAddUserSubMenu);
				
				String strManBtnPref = "man";
				String strAddUserBtnPref = "add";
				String strSessionVarAddUser = WorkSpaceManageUser.SESSION_VAR_WORK_AREA_ADD_USER;
				String strSessionVarManageUser = WorkSpaceManageUser.SESSION_VAR_WORK_AREA_MANAGE_USER;
				String strSessionSub = ManageUserModule.SESSION_VAR_UMANAGE_SEARCH;
				
				cManUserSubMenu = getAddUserSubMenu(btnManUser, strManBtnPref,  arrLTabBtns, cManUserSubMenu, arrLSubTabs, hasOp, boolEditStatus, null, strSessionVarManageUser, strSessionSub);
				cAddUserSubMenu = getAddUserSubMenu(btnAddUser, strAddUserBtnPref,  arrLTabBtns, cAddUserSubMenu,  arrLSubTabs, hasOp, boolEditStatus, null, strSessionVarAddUser, null);
				
				cManUserSubMenu.setStyleName("c_u_sub_menu_visible");
				cManUserSubMenu.setSizeUndefined();
				cAddUserSubMenu.setSizeUndefined();
				
				
				return cManageUserMenu;
			}else{
				return null;
			}
			
		}
		
		private HorizontalLayout getAddUserSubMenu(BtnTabLike btnAddUser, String strBtnPref, ArrayList<BtnTabLike> arrLTabBtns,  HorizontalLayout cAddUserSubMenu, ArrayList<HorizontalLayout> arrLAddUserSubTabs, boolean hasOp, boolean boolEditStatus, Object aum, String strSessionVar, String strSessionSub){
			
			cAddUserSubMenu.setStyleName("c_sub_menu_invisible");
			cAddUserSubMenu.setSizeUndefined();
			
			
			BtnTabLike btnAddAgent = new BtnTabLike("Agent",  strBtnPref+"_"+strBtnIDAgent);
			
			
			BtnTabLike btnAddMerchant = new BtnTabLike("Merchant", strBtnPref+"_"+strBtnIDMerchant);
			
			BtnTabLike btnAddDealer = new BtnTabLike("Dealer",  strBtnPref+"_"+strBtnIDDealer);
			
			BtnTabLike btnAddPartner = new BtnTabLike("Partner",  strBtnPref+"_"+strBtnIDPartner);
			
			BtnTabLike btnAddFAdmin = new BtnTabLike("Business Administrator",  strBtnPref+"_"+strBtnIDBA);
			
			BtnTabLike btnAddCCO = new BtnTabLike("CCO",  strBtnPref+"_"+strBtnIDCCO);
			
			btnAddAgent.setStyleName("btn_tab_like btn_tab_like_active");
			btnAddAgent.setEnabled(false);
			BtnTabLike.btnTabCur = btnAddAgent;
			
			
			cAddUserSubMenu.addComponent(btnAddAgent);
			cAddUserSubMenu.addComponent(btnAddMerchant);
			cAddUserSubMenu.addComponent(btnAddDealer);
			cAddUserSubMenu.addComponent(btnAddPartner );
			cAddUserSubMenu.addComponent(btnAddFAdmin);
			cAddUserSubMenu.addComponent(btnAddCCO );
			
			final ArrayList<BtnTabLike> arrLSubTabBtns = new ArrayList<BtnTabLike>();
			arrLSubTabBtns.add(btnAddAgent);
			arrLSubTabBtns.add(btnAddMerchant);
			arrLSubTabBtns.add(btnAddDealer);
			arrLSubTabBtns.add(btnAddPartner);
			arrLSubTabBtns.add(btnAddFAdmin);
			arrLSubTabBtns.add(btnAddCCO);
			
			
			
			String[] arrSessions = new String[]{WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE,
					WorkSpaceManageUser.SESSION_WORK_AREA,
					ManageUserModule.SESSION_UMANAGE };
			
			/*btnLog.addClickListener(new BtnTabLikeClickListener(false, true, arrLTabBtns, cUserLogMenu, cPerAccAuthInfo, udm, 
					"activity_log", "001"));*/
			btnAddAgent.addClickListener(new BtnTabLikeClickListener(false, arrLAddUserSubTabs,  arrLSubTabBtns, null, aum,
					"account_change_log", "001", hasOp, boolEditStatus,   arrSessions, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE, strSessionVar, null} ));
			
			btnAddMerchant.addClickListener(new BtnTabLikeClickListener(false, arrLAddUserSubTabs, arrLSubTabBtns,  null, aum,
					"activity_log", "001", hasOp, boolEditStatus, arrSessions, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_USER_MERCHANT, strSessionVar, null} ));
			
			btnAddDealer.addClickListener(new BtnTabLikeClickListener(false, arrLAddUserSubTabs,  arrLSubTabBtns, null, aum,
					"account_change_log", "001", hasOp, boolEditStatus, arrSessions, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_USER_DEALER, strSessionVar, null} ));
			
			btnAddPartner.addClickListener(new BtnTabLikeClickListener(false, arrLAddUserSubTabs,  arrLSubTabBtns, null, aum,
					"account_change_log", "001", hasOp, boolEditStatus, arrSessions, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_USER_PARTNER, strSessionVar, null} ));
			
			btnAddFAdmin.addClickListener(new BtnTabLikeClickListener(false, arrLAddUserSubTabs,  arrLSubTabBtns, null, aum,
					"account_change_log", "001", hasOp, boolEditStatus, arrSessions, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_USER_BA, strSessionVar, null} ));
			
			btnAddCCO.addClickListener(new BtnTabLikeClickListener(
					false,
					arrLAddUserSubTabs,
					arrLSubTabBtns,
					null,
					aum,
					"account_change_log",
					"001",
					hasOp,
					boolEditStatus,
					arrSessions,
					new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_USER_CCO, strSessionVar, null} ));
			
			btnAddUser.addClickListener(new BtnTabLikeClickListener(
					true ,
					arrLAddUserSubTabs,
					arrLTabBtns,
					cAddUserSubMenu,
					aum,
					"activity_log",
					"001",
					hasOp,
					boolEditStatus,
					arrSessions,
					new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE, strSessionVar, strSessionSub}
					
					));
			
			return cAddUserSubMenu;
		}
		
		
		
			
			
			
		
		
		
		
		
			
			
			
			
		
		
	
	
	
	
}
