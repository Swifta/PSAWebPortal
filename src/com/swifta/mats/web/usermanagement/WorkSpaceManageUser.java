package com.swifta.mats.web.usermanagement;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
	
public class WorkSpaceManageUser{
	/**
	 * 
	 */
	
	public static final String WORK_AREA = "";
	private Embedded emb;
	private Button btnLogout;
	HorizontalLayout contentC;
	VerticalLayout uf;
	VerticalLayout searchC;
	VerticalLayout searchResultsC;
	ManageUserModule mum;
	VerticalLayout cParentLayout;
	//static final String WORK_AREA = "work_area";
	public final static  String SESSION_WORK_AREA = "session_work_area";
	public final static  String SESSION_VAR_WORK_AREA_ADD_USER = "add_user";
	public final static  String SESSION_VAR_WORK_AREA_MANAGE_USER= "manage_user";
	private boolean wsmuInitStatus = false;
	VerticalLayout cuDetails;
	
	
	public WorkSpaceManageUser(){
		setCoreUI();
	}
	
	public void setCoreUI(){
		cParentLayout = new VerticalLayout();
		/*parent_layout settings */
		cParentLayout.setSizeFull();
		cParentLayout.setMargin(false);
		cParentLayout.setStyleName("parent_layout");
		
		/*logo*/
		
		btnLogout = new Button("Logout");
		
		ThemeResource systemLogo = new ThemeResource("img/logo.png");
		emb = new Embedded(null, systemLogo);
		emb.setWidth("120px");
		emb.setHeight("120px");
		
		//Label lbUsername = new Label(Login.CUR_SESSION_OBJ.getAttribute("user").toString());
		
		HorizontalLayout c1 = new HorizontalLayout();
		VerticalLayout c4 = new VerticalLayout();
		
		c1.setStyleName("c_1");
		c1.setSizeUndefined();
		
		c4.setStyleName("c_4");
		c4.setWidth("100%");
		c4.setHeight("100%");
		
		
		VerticalLayout logoC = new VerticalLayout();
		logoC.setSizeUndefined();
		logoC.setStyleName("logo_c");
		logoC.setMargin(true);
		logoC.addComponent(emb);
		c1.addComponent(logoC);
		
		
		
		HorizontalLayout logoutC = new HorizontalLayout();
		logoutC.setSizeUndefined();
		logoutC.setSpacing(true);
		logoutC.setMargin(true);
		logoutC.setStyleName("logout_c");
		//logoutC.addComponent(lbUsername);
		logoutC.addComponent(btnLogout);
		c4.addComponent(logoutC);
		c4.setComponentAlignment(logoutC, Alignment.TOP_RIGHT);
		
		VerticalLayout curActivityC = new VerticalLayout();
		//curActivityC.setWidth("100px");
		//curActivityC.setHeight("100px");
		curActivityC.setSizeUndefined();
		curActivityC.setMargin(false);
		curActivityC.setSpacing(false);
		
		curActivityC.setStyleName("cur_activity_c");
		//c4.addComponent(curActivityC);
		//c4.setComponentAlignment(curActivityC, Alignment.BOTTOM_RIGHT);
		
		VerticalLayout curActivityStrip = new VerticalLayout();
		curActivityStrip.setWidth("100%");
		
		curActivityStrip.setHeight("5px");
		curActivityStrip.setStyleName("cur_activity_strip");
		curActivityC.addComponent(curActivityStrip);
		
		/*VerticalLayout curActivityConn = new VerticalLayout();
		curActivityConn.setWidth("20px");
		curActivityConn.setHeight("50px");
		curActivityConn.setStyleName("cur_activity_conn");
		curActivityC.addComponent(curActivityConn);
		curActivityC.setComponentAlignment(curActivityConn, Alignment.TOP_CENTER);*/
		
		
		Embedded imgCurActivity = new Embedded(null, new ThemeResource("img/add_user_small.png"));
		imgCurActivity.setSizeUndefined();
		curActivityC.addComponent(imgCurActivity);
		curActivityC.setComponentAlignment(imgCurActivity, Alignment.TOP_CENTER);
		
		
		//////////////////////////////////////////////////////////////////////
		VerticalLayout menuC = new VerticalLayout();
		CustomLayout vmb = createAdminMenu();
		
		menuC.setWidth("130px");
		menuC.setHeight("100%");
		menuC.setStyleName("menu_c");
		
		vmb.setWidth("100%");
		vmb.setHeight("100%");
		
		HorizontalLayout vmbC = new HorizontalLayout();
		vmbC.setWidth("100%");
		vmbC.setHeight("100%");
		vmbC.setStyleName("vmb_c");
		
		vmbC.addComponent(vmb);
		menuC.addComponent(vmbC);
		
		contentC = new HorizontalLayout();
		contentC.setWidthUndefined();
		contentC.setHeightUndefined();
		contentC.setStyleName("content_c");
		
		HorizontalLayout mCC  = new HorizontalLayout();
		
		mCC.setSizeFull();
		mCC.setStyleName("menu_and_content_c");
		
		mCC.addComponent(menuC);
		mCC.addComponent(contentC);
		mCC.setExpandRatio(menuC, 0.0f);
		mCC.setExpandRatio(contentC, 1.0f);
		
		GridLayout g = new GridLayout(2,2);
		g.setWidth("100%");
		g.setHeightUndefined();
		g.setStyleName("g");
		
		g.addComponent(c4, 1,0);
		g.addComponent(c1, 0,0);
		g.addComponent(mCC, 0,1,1,1);
		
		g.setRowExpandRatio(0, 0.0f);
		g.setRowExpandRatio(1, 1.0f);
		
		g.setColumnExpandRatio(0, 0.0f);
		g.setColumnExpandRatio(1, 1.0f);
		
		
		
		
		//addComponent(g);
		//setComponentAlignment(g, Alignment.MIDDLE_CENTER);
		
		AddUserForm auf = new AddUserForm();
		uf = auf.getAddUserForm();
		mum = new ManageUserModule();
		searchC = mum.getSearchContainer();
		searchResultsC = mum.getSearchResults();
		searchResultsC.setSizeUndefined();
		cParentLayout.addComponent(mum.addManageUserMenu(wsmuInitStatus));
		
		
	}
	
	
	
	
	public VerticalLayout getWorkSpaceManageUser() {
		
		String curSessionWorkArea = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA);
		String curSessionUManage = (String) UI.getCurrent().getSession().getAttribute(ManageUserModule.SESSION_UMANAGE);
		if(curSessionWorkArea != null && curSessionWorkArea.equals(SESSION_VAR_WORK_AREA_MANAGE_USER)){
			
			contentC.addComponent(searchC);
			if(curSessionUManage != null && curSessionUManage.equals(ManageUserModule.SESSION_VAR_UMANAGE_SEARCH)){
				contentC.setComponentAlignment(searchC, Alignment.MIDDLE_CENTER);
				contentC.setSizeFull();;
				contentC.setSpacing(false);
				contentC.setMargin(true);
				
			}
		
			
			
		}	
			
		
		
		
		cParentLayout.addComponent(contentC);
		cParentLayout.setExpandRatio(contentC, 1.0f);
		//if(!wsmuInitStatus){
			return cParentLayout;
		//}else{
			//return null;
		//}
		
	}
	
	public CustomLayout createAdminMenu(){
		CustomLayout vmbar = new CustomLayout("VMBar_fitting");
		
		/*MenuItemClickable lbASpecific = new MenuItemClickable("Specific", "Specific");
		
		vmbar.addComponent(lbASpecific, "location_specific_accounts");
		
		MenuItemClickable lbAOnline = new MenuItemClickable("Online","Online");
		vmbar.addComponent(lbAOnline, "location_online_accounts");
		
		MenuItemClickable lbAOffline = new MenuItemClickable("Offline","Offline");
		vmbar.addComponent(lbAOffline, "location_offline_accounts");
	
		MenuItemClickable lbAActive = new MenuItemClickable("Active","Active");
		vmbar.addComponent(lbAActive, "location_active_accounts");
		
		
		MenuItemClickable lbASuspended = new MenuItemClickable("Suspended","Suspended");
		vmbar.addComponent(lbASuspended, "location_suspended_accounts");
		
		
		MenuItemClickable lbABlocked = new MenuItemClickable("Blocked","Blocked");
		vmbar.addComponent(lbABlocked, "location_blocked_accounts");
		
		MenuItemClickable lbAMisceleneous = new MenuItemClickable("Misceleneous","Misceleneous"); 
		vmbar.addComponent(lbAMisceleneous, "location_misceleneous_accounts_info");*/
		
		MenuItemClickable lbUAddNew = new MenuItemClickable("Add New","add_user");
		vmbar.addComponent(lbUAddNew, "location_new_user");
		
		MenuItemClickable lbUManage = new MenuItemClickable("Manage", "manage_user");
		vmbar.addComponent(lbUManage, "location_manage_user");
		
		/*MenuItemClickable lbUEdit = new MenuItemClickable("Edit", "edit_user");
		vmbar.addComponent(lbUEdit, "location_edit_user");
		
		MenuItemClickable lbULink = new MenuItemClickable("Link", "link_user");
		vmbar.addComponent(lbULink, "location_link_user");
		
		MenuItemClickable lbUDelete = new MenuItemClickable("Delete", "delete_user");
		vmbar.addComponent(lbUDelete, "location_delete_user");*/
		
		
		
		btnLogout.addClickListener( new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 3110441023351142376L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getSession().setAttribute("user", null);
				UI.getCurrent().getSession().setAttribute(ManageUserModule.SESSION_UMANAGE, null);
				UI.getCurrent().getSession().setAttribute(SESSION_WORK_AREA, null);
				UI.getCurrent().getNavigator().navigateTo(WORK_AREA);
				
			}
		});
	
		return vmbar;
	}
	
	
	public void wsmuModifier(){
		
		String curSessionWorkArea = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA);
		String curSessionUManage = (String) UI.getCurrent().getSession().getAttribute(ManageUserModule.SESSION_UMANAGE);

		if( curSessionWorkArea != null && curSessionWorkArea.equals(SESSION_VAR_WORK_AREA_MANAGE_USER)){
			contentC.removeComponent(uf);
			contentC.addComponent(searchC);
			if(curSessionUManage != null && curSessionUManage.equals(ManageUserModule.SESSION_VAR_UMANAGE_SEARCH)){
				contentC.setComponentAlignment(searchC, Alignment.MIDDLE_CENTER);
				contentC.setSizeFull();;
				contentC.setSpacing(false);
				contentC.setMargin(true);
				
			}
		
			
			
		}	
		
		
			
	if(curSessionWorkArea!= null && curSessionWorkArea.equals(SESSION_VAR_WORK_AREA_ADD_USER)){
		contentC.removeAllComponents();
		contentC.addComponent(uf);
		contentC.setComponentAlignment(uf, Alignment.MIDDLE_CENTER);
		contentC.setSpacing(false);
		contentC.setMargin(true);
		contentC.setSizeFull();
	}
		
	if(curSessionWorkArea!= null && curSessionWorkArea.equals(SESSION_VAR_WORK_AREA_MANAGE_USER)){
		
			if(curSessionUManage != null && curSessionUManage.equals(ManageUserModule.SESSION_VAR_UMANAGE_SEARCH_RESULTS)){
				if(cuDetails != null){
					contentC.removeComponent(cuDetails);
				}
					contentC.addComponent(searchC);
					searchC.setSizeUndefined();
					contentC.setComponentAlignment(searchC, Alignment.TOP_RIGHT);
					
					contentC.addComponent(searchResultsC);
					contentC.setComponentAlignment(searchResultsC, Alignment.TOP_LEFT);
					
					contentC.setSizeUndefined();
					contentC.setMargin(new MarginInfo(true, false, true, false));
					contentC.setSpacing(false);
			
			}
			
			
			if(curSessionUManage != null && curSessionUManage.equals(ManageUserModule.SESSION_VAR_UMANAGE_USER_DETAILS)){
				contentC.removeComponent(searchResultsC);
				searchC.setSizeUndefined();
				//searchC.setSizeFull();
				//contentC.setComponentAlignment(searchC, Alignment.TOP_LEFT);
				contentC.setComponentAlignment(searchC, Alignment.TOP_RIGHT);
								
				cuDetails = mum.getUserDetailsContainer();
				
				contentC.addComponent(cuDetails);
				//cuDetails.setSizeUndefined();
				contentC.setComponentAlignment(cuDetails, Alignment.TOP_CENTER);
				contentC.setExpandRatio(cuDetails, 1.0f);
				
				contentC.setSizeFull();
				contentC.setMargin(new MarginInfo(true, false, true, false));
				contentC.setSpacing(false);
				contentC.setStyleName("content_c");
				
			}
	}	
	}
	
	

}