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
	public VerticalLayout getSearchContainer(){
				VerticalLayout searchContainer = new VerticalLayout();
				searchContainer.setSizeUndefined();
				searchContainer.setSpacing(false);
				searchContainer.setStyleName("c_search_user");
				searchContainer.setMargin(new MarginInfo(false, true, false, true));
		
				FormLayout searchForm = new FormLayout();
				searchForm.setSizeUndefined();
				searchForm.setSpacing(true);
				searchForm.setMargin(false);
				searchForm.setStyleName("search_user_form");
				
				Embedded emb = new Embedded(null,new ThemeResource("img/search_user_icon.png"));
				emb.setDescription("Search users");
				emb.setStyleName("search_user_img");
				emb.setSizeUndefined();
				
				
				Label lbSearch = new Label("Search users by: ");
				lbSearch.setSizeUndefined();
				lbSearch.setStyleName("label_search_user");
				lbSearch.setSizeUndefined();
				
				VerticalLayout searchUserHeader = new VerticalLayout();
				searchUserHeader.setHeightUndefined();
				searchUserHeader.setMargin(false);
				searchUserHeader.setSpacing(true);
				searchUserHeader.addComponent(emb);
				searchUserHeader.addComponent(lbSearch);
				searchContainer.addComponent(searchUserHeader);
				searchUserHeader.setStyleName("search_user_header");
				
				TextField tfUid = new TextField();
				tfUid.setCaption("User ID");
				
				tfUname = new TextField();
				tfUname.setCaption("Username");
				
				TextField tfFn = new TextField();
				tfFn.setCaption("First Name");
				
				TextField tfLn = new TextField();
				tfLn.setCaption("Last Name");
				
				TextField tfOthers = new TextField();
				tfOthers.setCaption("Others");
				
				Button btnSearch = new Button("Search");
				btnSearch.setIcon(FontAwesome.SEARCH);
				//ThemeResource r = new ThemeResource("img/search_small.png");
				
				//btnSearch.setIcon(r);
				//btnSearch.setHeight("60px");
				//btnSearch.setWidth("183px");
				//searchForm.addComponent(lbSearch);
				searchForm.addComponent(searchUserHeader);
				searchForm.addComponent(tfUid);
				searchForm.addComponent(tfUname);
				searchForm.addComponent(tfFn);
				searchForm.addComponent(tfLn);
				searchForm.addComponent(tfOthers);
				searchForm.addComponent(btnSearch);
				searchContainer.addComponent(searchForm);
				searchContainer.setComponentAlignment(searchForm, Alignment.TOP_RIGHT);
				
				
				
				btnSearch.addClickListener(new Button.ClickListener() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = -5894920456172825127L;
		
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().getSession().setAttribute(SESSION_UMANAGE, SESSION_VAR_UMANAGE_SEARCH_RESULTS);
						UI.getCurrent().getSession().setAttribute(SESSION_UMANAGE, SESSION_VAR_UMANAGE_SEARCH_RESULTS);
						
						if(WorkSpace.wsmu != null)
							WorkSpace.wsmu.wsmuModifier();
					}
				});
				
			

		
		
		return searchContainer;
	}
	
	
	public VerticalLayout getSearchResults(){
		VerticalLayout searchContainer = new VerticalLayout();
		
		VerticalLayout searchResultsContainer = new VerticalLayout();
		searchResultsContainer.setSizeUndefined();
		searchResultsContainer.setSpacing(true);
		searchResultsContainer.setMargin(new MarginInfo(false, true, true, true));
		
		
		VerticalLayout searchUserHeader = new VerticalLayout();
		searchUserHeader.setWidth("100%");
		searchUserHeader.setHeightUndefined();
		searchUserHeader.setMargin(true);
		searchUserHeader.setSpacing(true);
		searchUserHeader.setStyleName("search_user_header");
		searchContainer.setStyleName("c_u_search_results");
		
		Embedded emb = new Embedded(null,new ThemeResource("img/search_user_1.png"));
		emb.setDescription("Search users");
		emb.setStyleName("search_user_img");
		searchUserHeader.addComponent(emb);
		searchUserHeader.setComponentAlignment(emb, Alignment.TOP_RIGHT);
		
		Button btnSearch = new Button();
		btnSearch.setDescription("Search");
		//ThemeResource r = new ThemeResource("img/search_small.png");
		//btnSearch.setIcon(r);
		btnSearch.setIcon(FontAwesome.SEARCH);
		searchUserHeader.addComponent(btnSearch);
		searchUserHeader.setComponentAlignment(btnSearch, Alignment.TOP_RIGHT);
		
		Label lbSearchResults = new Label("Search Results...");
		lbSearchResults.setSizeUndefined();
		lbSearchResults.setStyleName("label_search_results");
		searchUserHeader.addComponent(lbSearchResults);
		searchUserHeader.setComponentAlignment(lbSearchResults, Alignment.TOP_RIGHT);
		
		//searchContainer.addComponent(searchUserHeader);
		
		
		SearchUserModule sum = new SearchUserModule();
		IndexedContainer container = sum.queryBackEnd();
		
		
		PagedTableCustom tb = new PagedTableCustom("Search results for: \"admin\"(Summary)");
		
		
		tb.setContainerDataSource(container);
		tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE_O);
		//tb.setPageLength(5);
		tb.setStyleName("tb_u_search_results");
		
		HorizontalLayout pnUserSearchResults = tb.createControls();
		pnUserSearchResults.setSizeFull();
		pnUserSearchResults.setMargin(false);
		pnUserSearchResults.setSpacing(false);
		
		//int pageCur = tb.getCurrentPage();
		//tb.previousPage();
		//tb.nextPage();
		//int pageTotal = tb.getTotalAmountOfPages();
		
		searchResultsContainer.addComponent(pnUserSearchResults);
		searchResultsContainer.addComponent(tb);
		//tb.setHeight("219px");
		
		VerticalLayout actionBulkC = new VerticalLayout();
		actionBulkC.setWidth("100%");
		actionBulkC.setStyleName("c_action_bulk");
		
		CheckBox chkAll = new CheckBox();
		chkAll.setCaption("Select All");
		
		ComboBox cmbBulk = new ComboBox("Bulk Action");
		cmbBulk.addItem("Delete");
		cmbBulk.addItem("Link");
		
		Button btnBulkOk = new Button("Apply");
		btnBulkOk.setStyleName("btn_link");
		HorizontalLayout cBulk = new HorizontalLayout();
		cBulk.setSizeUndefined();
		cBulk.addComponent(cmbBulk);
		cBulk.addComponent(btnBulkOk);
		cBulk.setComponentAlignment(btnBulkOk, Alignment.BOTTOM_RIGHT);
		
		
		actionBulkC.addComponent(chkAll);
		actionBulkC.addComponent(cBulk);
		
		
		
		
		searchResultsContainer.addComponent(actionBulkC);
		searchContainer.addComponent(searchResultsContainer);
		searchContainer.setComponentAlignment(searchResultsContainer, Alignment.TOP_RIGHT);
		
		
		
		
		
		btnSearch.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 7182067560976379938L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getSession().setAttribute(SESSION_UMANAGE, SESSION_VAR_UMANAGE_SEARCH_RESULTS);
				//UI.getCurrent().getNavigator().navigateTo(WorkSpaceManageUser.WORK_AREA+"/manage_user");
				//WorkSpace.wsmu.getWorkSpaceManageUser();
				//WorkSpace.wsmu.getWorkSpaceManageUser();
				
				
			}
		});
		
		
		
		return searchContainer;
	}
		
	
	

	
	public VerticalLayout getUserDetailsContainer(String strTbName, String strUID){
			udm = new UserDetailsModule();
		
			VerticalLayout cUDetails = new VerticalLayout();
			cUDetails.setMargin(new MarginInfo(false, true, true, true));
			
			cUDetails.setStyleName("c_u_details");
			cUDetails.setSizeFull();
			
			
			VerticalLayout cCHeader = new VerticalLayout();
			cCHeader.setSizeUndefined();
			cCHeader.setStyleName("c_c_header");
			cCHeader.setMargin(new MarginInfo(false, true, false, false));
			
			Label lbCHeader = new Label("Details of Sevo");
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
			HorizontalLayout cLog = getUserLogSubMenu(btnLog, cUserLogMenu, arrLSubTabs);
			cLog.setSizeUndefined();
			cTabLike.addComponent(cLog);
			cTabLike.setComponentAlignment(cLog, Alignment.TOP_RIGHT);
			
			
			cCHeader.addComponent(lbCHeader);
			cCHeader.addComponent(cTabLike);
			
			btnPersonal.setStyleName("btn_tab_like btn_tab_like_active");
			
			
			
			
			
			//btnAccount.addClickListener(new BtnTabLikeClickListener());
			//btnAuth.addClickListener(new BtnTabLikeClickListener());
			
		
			
			cUDetails.addComponent(cCHeader);
			cUDetails.setComponentAlignment(cCHeader, Alignment.TOP_LEFT);
			
			cUDetails.addComponent(cPerAccAuthInfo);
			cUDetails.setComponentAlignment(cPerAccAuthInfo, Alignment.TOP_LEFT);
			cUDetails.setExpandRatio(cPerAccAuthInfo, 1.0f);
			
			
			uDetailsForm = udm.getDetailsForm("personal", "001");
			cPerAccAuthInfo.addComponent(uDetailsForm);
			btnPersonal.setEnabled(false);
			//curIDDetails = btnPersonal.getId();
			
			
			
			btnPersonal.addClickListener(new BtnTabLikeClickListener(false, false, arrLSubTabs, arrLTabBtns, cPerAccAuthInfo, udm,
					"personal", "001" ));
			btnAccount.addClickListener(new BtnTabLikeClickListener(false, false, arrLSubTabs,  arrLTabBtns, cPerAccAuthInfo, udm,
					"account", "001" ));
			btnAuth.addClickListener(new BtnTabLikeClickListener(false, false, arrLSubTabs, arrLTabBtns, cPerAccAuthInfo, udm,
					"auth", "001" ));
			
			
			
			
			
			
			
		return cUDetails; 
	}
	
	
	

		
		public HorizontalLayout addManageUserMenu(boolean boolAddHeaderStatus){
			
			if(!boolAddHeaderStatus){
				HorizontalLayout cManageUserMenu = new HorizontalLayout();
				cManageUserMenu.setStyleName("c_u_manage_menu");
				cManageUserMenu.setSizeUndefined();
				
				BtnTabLike btnManageU = new BtnTabLike("Manage", btnIDManageUser);
				btnManageU.setStyleName("btn_tab_like btn_tab_like_active");
				
				BtnTabLike btnAddU = new BtnTabLike("Add New", btnIDAddUser);
				cManageUserMenu.addComponent(btnManageU);
				cManageUserMenu.addComponent(btnAddU);
				
				final ArrayList<BtnTabLike> arrLTabBtns = new ArrayList<BtnTabLike>();
				arrLTabBtns.add(btnAddU);
				arrLTabBtns.add(btnManageU);
				
				
				WorkSpaceManageUser wsmu = WorkSpace.wsmu;
				
				btnManageU.addClickListener(new BtnTabLikeClickListener(true, arrLTabBtns, new String[]{WorkSpaceManageUser.SESSION_WORK_AREA, ManageUserModule.SESSION_UMANAGE}, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_MANAGE_USER, ManageUserModule.SESSION_VAR_UMANAGE_SEARCH}));
				btnManageU.setEnabled(false);
				
				btnAddU.addClickListener(new BtnTabLikeClickListener(true, arrLTabBtns, new String[]{WorkSpaceManageUser.SESSION_WORK_AREA}, new String[]{WorkSpaceManageUser.SESSION_VAR_WORK_AREA_ADD_USER}));
				
				
		
				
				
				
				return cManageUserMenu;
			}else{
				return null;
			}
			
		}
		
		
		private HorizontalLayout getUserLogSubMenu(BtnTabLike btnLog, HorizontalLayout cUserLogMenu, ArrayList<HorizontalLayout> arrLSubTabs){
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
					"activity_log", "001" ));
			btnAccChangeLog.addClickListener(new BtnTabLikeClickListener(false, false, arrLSubTabs,  arrLSubTabBtns, cPerAccAuthInfo, udm,
					"account_change_log", "001" ));
			
			btnLog.addClickListener(new BtnTabLikeClickListener(false, true, arrLTabBtns, cUserLogMenu, cPerAccAuthInfo, udm,
					"activity_log", "001"));
			//cUserLogMenu.setVisible(false);
			//cLog.addComponent(cUserLogMenu);
			
			
			
			return cUserLogMenu;
		}
		
		
			
			
			
		
		
		
		
		
			
			
			
			
		
		
	
	
	
	
}
