package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;

import com.swifta.mats.web.WorkSpace;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
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
	
	
	private FormLayout uDetailsForm;
	ArrayList<BtnTabLike> arrLTabBtns;
	Window popup;
	VerticalLayout cPopupMsg;
	
	
	
	
	
	
	
	
	private TextField tfUname;
	
	
	
	public ManageUserModule(){
	}
	public FormLayout getSearchContainer(String strUserType){
		SearchUserModule sum = new SearchUserModule();
		return sum.getSearchForm(strUserType);
	}
	
	
	public VerticalLayout getSearchResults(String strSearchParams){
		SearchUserModule sum = new SearchUserModule();
		return sum.getSearchResults(strSearchParams);
	}
		
	
	

	
	public VerticalLayout getUserDetailsContainer(String strTbName, String strUID, boolean hasOp, boolean boolEditStatus){
			udm = new UserDetailsModule();
			
			VerticalLayout cUDetails = new VerticalLayout();
			cUDetails.setMargin(new MarginInfo(false, true, true, true));
			
			cUDetails.setStyleName("c_u_details");
			cUDetails.setSizeFull();
			
			
			VerticalLayout cCHeader = new VerticalLayout();
			cCHeader.setSizeUndefined();
			cCHeader.setStyleName("c_c_header");
			cCHeader.setMargin(new MarginInfo(false, true, false, false));
			String strCHeader;
			
			if(boolEditStatus){
				strCHeader = "Edit Details of Sevo";
			}else{
				strCHeader = "Showing Details of Sevo";
			}
			
			Label lbCHeader = new Label(strCHeader);
			lbCHeader.setStyleName("label_c_header");
			lbCHeader.setSizeUndefined();
			
			
			
			
			
			
			cPerAccAuthInfo = new HorizontalLayout();
			cPerAccAuthInfo.setStyleName("c_per_acc_auth_info");
			cPerAccAuthInfo.setSizeFull();
						
			
			
			BtnTabLike btnPersonal;
			BtnTabLike btnAccount;
			BtnTabLike btnAuth;
			BtnTabLike btnLog;
			
			
			
			arrLTabBtns = new ArrayList<BtnTabLike>();
			
			
			btnPersonal = new BtnTabLike("Personal", btnIDPersonal);
			btnAccount = new BtnTabLike("Account", btnIDAccount);
			btnAuth = new BtnTabLike("Authentication", btnIDAuth);
			btnLog = new BtnTabLike("Log", btnIDLog);
			
			VerticalLayout cTabLike = new VerticalLayout();
			cTabLike.setSizeUndefined();
			
			HorizontalLayout cInfoCategory = new HorizontalLayout();
			cInfoCategory.setSizeUndefined();
			cInfoCategory.setStyleName("c_info_category");
			cInfoCategory.addComponent(btnPersonal);
			cInfoCategory.addComponent(btnAccount);
			cInfoCategory.addComponent(btnAuth);
			cInfoCategory.addComponent(btnLog);
			
			arrLTabBtns.add(btnPersonal);
			arrLTabBtns.add(btnAccount);
			arrLTabBtns.add(btnAuth);
			arrLTabBtns.add(btnLog);
			
			cTabLike.addComponent(cInfoCategory);
			
			ArrayList<HorizontalLayout> arrLSubTabs = new ArrayList<HorizontalLayout>();
			HorizontalLayout cUserLogMenu = new HorizontalLayout();
			arrLSubTabs.add(cUserLogMenu);
			HorizontalLayout cLog = getUserLogSubMenu(btnLog, cUserLogMenu, arrLSubTabs, hasOp, boolEditStatus);
			cLog.setSizeUndefined();
			cTabLike.addComponent(cLog);
			cTabLike.setComponentAlignment(cLog, Alignment.TOP_RIGHT);
			
			
			cCHeader.addComponent(lbCHeader);
			cCHeader.addComponent(cTabLike);
			
			if(strTbName.equals("personal")){
				btnPersonal.setStyleName("btn_tab_like btn_tab_like_active");
			}else if(strTbName.equals("account")){
				btnAccount.setStyleName("btn_tab_like btn_tab_like_active");
			}
			
			
			
			
			
			//btnAccount.addClickListener(new BtnTabLikeClickListener());
			//btnAuth.addClickListener(new BtnTabLikeClickListener());
			
		
			
			cUDetails.addComponent(cCHeader);
			cUDetails.setComponentAlignment(cCHeader, Alignment.TOP_LEFT);
			
			cUDetails.addComponent(cPerAccAuthInfo);
			cUDetails.setComponentAlignment(cPerAccAuthInfo, Alignment.TOP_LEFT);
			cUDetails.setExpandRatio(cPerAccAuthInfo, 1.0f);
			
			
			HorizontalLayout cUDetailsAndOperations= udm.getDetailsForm(strTbName, "001", hasOp, boolEditStatus);
			cPerAccAuthInfo.addComponent(cUDetailsAndOperations);
			btnPersonal.setEnabled(false);
			//curIDDetails = btnPersonal.getId();
			
			
			
			btnPersonal.addClickListener(new BtnTabLikeClickListener(false, false, arrLSubTabs, arrLTabBtns, cPerAccAuthInfo, udm,
					"personal", "001", hasOp, boolEditStatus ));
			btnAccount.addClickListener(new BtnTabLikeClickListener(false, false, arrLSubTabs,  arrLTabBtns, cPerAccAuthInfo, udm,
					"account", "001", hasOp, boolEditStatus));
			btnAuth.addClickListener(new BtnTabLikeClickListener(false, false, arrLSubTabs, arrLTabBtns, cPerAccAuthInfo, udm,
					"auth", "001", hasOp, boolEditStatus ));
			
			
			
			
			
			
			
		return cUDetails; 
	}
	
	
	

		
		public VerticalLayout getManageUserMenu(boolean boolAddHeaderStatus, boolean boolEditStatus, boolean hasOp, HorizontalLayout cContent, Object aum){
			
			if(!boolAddHeaderStatus){
				
				
				VerticalLayout cManageUserMenu = new VerticalLayout();
				cManageUserMenu.setStyleName("c_u_manage_menu");
				cManageUserMenu.setSizeUndefined();
				
				HorizontalLayout cManageAndAddTab = new HorizontalLayout();
				
				BtnTabLike btnManUser = new BtnTabLike("Manage", btnIDManageUser);
				btnManUser.setStyleName("btn_tab_like btn_tab_like_active");
				
				UI.getCurrent().getSession().setAttribute(SearchUserModule.SESSION_SEARCH_USER, SearchUserModule.SESSION_VAR_SEARCH_USER_DEFAULT);

				
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
				
				cManUserSubMenu = getAddUserSubMenu(btnManUser, strManBtnPref,  arrLTabBtns, cManUserSubMenu, cContent, arrLSubTabs, hasOp, boolEditStatus, this);
				cAddUserSubMenu = getAddUserSubMenu(btnAddUser, strAddUserBtnPref,  arrLTabBtns, cAddUserSubMenu, cContent, arrLSubTabs, hasOp, boolEditStatus, aum);
				
				cManUserSubMenu.setStyleName("c_u_sub_menu_visible");
				cManUserSubMenu.setSizeUndefined();
				cAddUserSubMenu.setSizeUndefined();
				
				
				return cManageUserMenu;
			}else{
				return null;
			}
			
		}
		
		
		private HorizontalLayout getUserLogSubMenu(BtnTabLike btnLog, HorizontalLayout cUserLogMenu, ArrayList<HorizontalLayout> arrLSubTabs, boolean hasOp, boolean boolEditStatus){
			//btnLog.addClickListener(new BtnTabLikeClickListener(false, arrLTabBtns, cPerAccAuthInfo, udm, "log", "001" ));
			//VerticalLayout cLog = new VerticalLayout();
			//cLog.setSizeUndefined();
			cUserLogMenu.setStyleName("c_sub_menu_invisible");
			cUserLogMenu.setSizeUndefined();
			
			BtnTabLike btnAccChangeLog = new BtnTabLike("User Log", btnIDActLog);
			btnAccChangeLog.setStyleName("btn_tab_like btn_tab_like_active");
			btnAccChangeLog.setEnabled(false);
			
			BtnTabLike btnActLog = new BtnTabLike("Account Log", btnIDAccChangeLog);
			cUserLogMenu.addComponent(btnAccChangeLog);
			cUserLogMenu.addComponent(btnActLog);
			
			final ArrayList<BtnTabLike> arrLSubTabBtns = new ArrayList<BtnTabLike>();
			arrLSubTabBtns.add(btnActLog);
			arrLSubTabBtns.add(btnAccChangeLog);
			/*btnLog.addClickListener(new BtnTabLikeClickListener(false, true, arrLTabBtns, cUserLogMenu, cPerAccAuthInfo, udm, 
					"activity_log", "001"));*/
			
			
			
			
			btnActLog.addClickListener(new BtnTabLikeClickListener(false, false, arrLSubTabs, arrLSubTabBtns,  cPerAccAuthInfo, udm,
					"activity_log", "001", hasOp, boolEditStatus ));
			btnAccChangeLog.addClickListener(new BtnTabLikeClickListener(false, false, arrLSubTabs,  arrLSubTabBtns, cPerAccAuthInfo, udm,
					"account_change_log", "001", hasOp, boolEditStatus ));
			
			btnLog.addClickListener(new BtnTabLikeClickListener(false, true, arrLTabBtns, cUserLogMenu, cPerAccAuthInfo, udm,
					"activity_log", "001", hasOp, boolEditStatus));
			//cUserLogMenu.setVisible(false);
			//cLog.addComponent(cUserLogMenu);
			
			
			
			return cUserLogMenu;
		}
		
		
		
		
		private HorizontalLayout getAddUserSubMenu(BtnTabLike btnAddUser, String strBtnPref, ArrayList<BtnTabLike> arrLTabBtns,  HorizontalLayout cAddUserSubMenu, HorizontalLayout cContent, ArrayList<HorizontalLayout> arrLAddUserSubTabs, boolean hasOp, boolean boolEditStatus, Object aum){
			//btnLog.addClickListener(new BtnTabLikeClickListener(false, arrLTabBtns, cPerAccAuthInfo, udm, "log", "001" ));
			//VerticalLayout cLog = new VerticalLayout();
			//cLog.setSizeUndefined();
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
			
			
			
			/*btnLog.addClickListener(new BtnTabLikeClickListener(false, true, arrLTabBtns, cUserLogMenu, cPerAccAuthInfo, udm, 
					"activity_log", "001"));*/
			btnAddAgent.addClickListener(new BtnTabLikeClickListener(false, false, arrLAddUserSubTabs,  arrLSubTabBtns, cContent, aum,
					"account_change_log", "001", hasOp, boolEditStatus ));
			
			btnAddMerchant.addClickListener(new BtnTabLikeClickListener(false, false, arrLAddUserSubTabs, arrLSubTabBtns,  cContent, aum,
					"activity_log", "001", hasOp, boolEditStatus ));
			
			btnAddDealer.addClickListener(new BtnTabLikeClickListener(false, false, arrLAddUserSubTabs,  arrLSubTabBtns, cContent, aum,
					"account_change_log", "001", hasOp, boolEditStatus ));
			
			btnAddPartner.addClickListener(new BtnTabLikeClickListener(false, false, arrLAddUserSubTabs,  arrLSubTabBtns, cContent, aum,
					"account_change_log", "001", hasOp, boolEditStatus ));
			
			btnAddFAdmin.addClickListener(new BtnTabLikeClickListener(false, false, arrLAddUserSubTabs,  arrLSubTabBtns, cContent, aum,
					"account_change_log", "001", hasOp, boolEditStatus ));
			
			btnAddCCO.addClickListener(new BtnTabLikeClickListener(false, false, arrLAddUserSubTabs,  arrLSubTabBtns, cContent, aum,
					"account_change_log", "001", hasOp, boolEditStatus ));
			
			btnAddUser.addClickListener(new BtnTabLikeClickListener(false, true , arrLAddUserSubTabs, arrLTabBtns, cAddUserSubMenu, cContent, aum,
					"activity_log", "001", hasOp, boolEditStatus));
			//cUserLogMenu.setVisible(false);
			//cLog.addComponent(cUserLogMenu);
			
			
			
			return cAddUserSubMenu;
		}
		
		
		
			
			
			
		
		
		
		
		
			
			
			
			
		
		
	
	
	
	
}
