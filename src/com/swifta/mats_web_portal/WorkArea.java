package com.swifta.mats_web_portal;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
	
public class WorkArea extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5603637229125243561L;
	public static final String WORK_AREA = "";
	private Embedded emb;
	private Button btnLogout;
	//static final String WORK_AREA = "work_area";
	@Override
	public void enter(ViewChangeEvent event) {
		setSizeFull();
		setMargin(false);
		setStyleName("parent_layout");
		
		btnLogout = new Button("Logout");
		
		ThemeResource systemLogo = new ThemeResource("img/logo.jpg");
		emb = new Embedded(null, systemLogo);
		emb.setWidth("120px");
		emb.setHeight("120px");
		
		Label lbUsername = new Label(UI.getCurrent().getSession().getAttribute("user").toString());
		
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
		logoutC.addComponent(lbUsername);
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
		
		HorizontalLayout contentC = new HorizontalLayout();
		contentC.setWidthUndefined();
		contentC.setHeightUndefined();
		contentC.setStyleName("content_c");
		
		HorizontalLayout mCC  = new HorizontalLayout();
		//VerticalLayout mCC = new VerticalLayout();
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
		
		
		
		addComponent(g);
		setComponentAlignment(g, Alignment.MIDDLE_CENTER);
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		
		/*VerticalLayout cLogo = new VerticalLayout();
		cLogo.setSizeUndefined();
		cLogo.setStyleName("logo_c");
		cLogo.setMargin(true);
		cLogo.addComponent(emb);*/
		
		/*HorizontalLayout cHeader = new HorizontalLayout();
		cHeader.setWidth("100%");
		cHeader.setHeightUndefined();
		cHeader.setStyleName("c_header");
		cHeader.addComponent(logoC);
		cHeader.addComponent(logoutC);
		cHeader.setComponentAlignment(logoutC, Alignment.TOP_RIGHT);
		
		addComponent(cHeader);
		addComponent(mCC);
		setExpandRatio(mCC, 1.0f);*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		if(event.getParameters().toString().equals("add_user")){
			AddUserForm auf = new AddUserForm();
			VerticalLayout uf = auf.getAddUserForm();
			contentC.addComponent(uf);
			contentC.setComponentAlignment(uf, Alignment.MIDDLE_CENTER);
			contentC.setSpacing(false);
			contentC.setMargin(true);
			contentC.setSizeFull();
		}
		
		String curURIFragment = event.getParameters().toString();
		
		if(curURIFragment.equals("manage_user")){
			ManageUserModule mum = new ManageUserModule();
			
			VerticalLayout searchC = mum.getSearchContainer();
			//VerticalLayout operationC = mum.getUserOperationContainer();
			
			
			contentC.addComponent(searchC);
			//contentC.addComponent(operationC);
			//contentC.addComponent(curActivityC);
			
			
			contentC.setComponentAlignment(searchC, Alignment.MIDDLE_CENTER);
			//contentC.setComponentAlignment(operationC, Alignment.TOP_LEFT);
			//contentC.setComponentAlignment(curActivityC, Alignment.TOP_LEFT);
			
			
			
			contentC.setSizeFull();;
			contentC.setSpacing(false);
			contentC.setMargin(true);
			
		}
		
		if(curURIFragment.equals("search_user_results")){
			ManageUserModule mum = new ManageUserModule();
			
			VerticalLayout searchC = mum.getSearchContainer();
			
			//VerticalLayout operationC = mum.getUserOperationContainer();
			VerticalLayout searchResultsC = mum.getSearchResults();
			searchC.setSizeUndefined();
			searchResultsC.setSizeUndefined();
			
			
			contentC.addComponent(searchC);
			contentC.addComponent(searchResultsC);
			//contentC.addComponent(curActivityC);
			
			
			contentC.setComponentAlignment(searchC, Alignment.TOP_LEFT);
			contentC.setComponentAlignment(searchResultsC, Alignment.TOP_CENTER);
			//contentC.setComponentAlignment(curActivityC, Alignment.TOP_LEFT);
			
			
			
			contentC.setSizeUndefined();
			contentC.setMargin(new MarginInfo(true, false, true, false));
			contentC.setSpacing(false);
			
		}
		
		
		
		
		/*if(UI.getCurrent().getSession().getAttribute(ManageUserModule.UMANAGE_SESSION_DETAILS) != null && curURIFragment.equals("user_details")){
			ManageUserModule mum = new ManageUserModule();
			
			VerticalLayout detailsC = mum.getDetailsContainer();
			VerticalLayout operationC = mum.getUserOperationContainer();
			
			
			contentC.addComponent(detailsC);
			contentC.addComponent(operationC);
			//contentC.addComponent(curActivityC);
			
			
			contentC.setComponentAlignment(detailsC, Alignment.TOP_RIGHT);
			contentC.setComponentAlignment(operationC, Alignment.TOP_RIGHT);
			//contentC.setComponentAlignment(curActivityC, Alignment.TOP_LEFT);
			
			
			
			contentC.setSizeFull();;
			contentC.setMargin(true);
			contentC.setSpacing(true);
			
		}*/
		
		
		
		
		
		
		
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
				UI.getCurrent().getSession().setAttribute(ManageUserModule.UMANAGE_SESSION_SEARCH, null);
				UI.getCurrent().getSession().setAttribute(ManageUserModule.UMANAGE_SESSION_DETAILS, null);
				UI.getCurrent().getNavigator().navigateTo(WORK_AREA);
				
			}
		});
	
		return vmbar;
	}
	
	

}