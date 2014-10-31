package com.swifta.mats.web.usermanagement;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
	
public class WorkSpaceManageUser{
	/**
	 * 
	 */
	
	public static final String WORK_AREA = "";
	HorizontalLayout contentC;
	VerticalLayout uf;
	FormLayout searchC;
	VerticalLayout searchResultsC;
	AddUserModule aum;
	ManageUserModule mum;
	UserDetailsModule udm;
	VerticalLayout cParentLayout;
	VerticalLayout cuDetails;
	VerticalLayout mm;
	SearchUserModule sum;
	String curSessionUManage;
	
	//static final String WORK_AREA = "work_area";
	public final static  String SESSION_WORK_AREA_USER_TYPE = "user_type";
	public final static  String SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE = "Agent";
	public final static  String SESSION_VAR_WORK_AREA_USER_MERCHANT = "Merchant";
	public final static  String SESSION_VAR_WORK_AREA_USER_DEALER = "Dealer";
	public final static  String SESSION_VAR_WORK_AREA_USER_PARTNER = "Partner";
	public final static  String SESSION_VAR_WORK_AREA_USER_BA = "BA";
	public final static  String SESSION_VAR_WORK_AREA_USER_CCO = "CCO";
	
	public final static  String SESSION_WORK_AREA = "session_work_area";
	public final static  String SESSION_VAR_WORK_AREA_ADD_USER = "add_user";
	public final static  String SESSION_VAR_WORK_AREA_MANAGE_USER= "manage_user";
	private boolean wsmuInitStatus = false;
	
	
	
	public WorkSpaceManageUser(){
		setCoreUI();
	}
	
	public void setCoreUI(){
		UI.getCurrent().getSession().setAttribute(SESSION_WORK_AREA_USER_TYPE, SESSION_VAR_WORK_AREA_DEFAULT_USER_TYPE);
		//UI.getCurrent().getSession().setAttribute(SESSION_WORK_AREA, SESSION_VAR_WORK_AREA_MANAGE_USER);

		cParentLayout = new VerticalLayout();
		
		contentC = new HorizontalLayout();
		contentC.setWidthUndefined();
		contentC.setHeightUndefined();
		contentC.setStyleName("content_c");
		
		aum = new AddUserModule();
		mum = new ManageUserModule();
		sum = new SearchUserModule();		
		mm = mum.getManageUserMenu(wsmuInitStatus, false, false, aum);	
		mm.setSizeUndefined();
		
		cParentLayout.addComponent(mm);
		cParentLayout.setComponentAlignment(mm, Alignment.TOP_CENTER);
		cParentLayout.addComponent(contentC);
	}
	
	
	
	
	public VerticalLayout getWorkSpaceManageUser() {
		String curSessionUManage = (String) UI.getCurrent().getSession().getAttribute(ManageUserModule.SESSION_UMANAGE);
		searchC = (FormLayout)sum.sumModifier(curSessionUManage, contentC);
		return cParentLayout;
		
		
	}
	
	
	public void wsmuModifier(){
		
	String curSessionWorkArea = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA);
				
	if(curSessionWorkArea!= null && curSessionWorkArea.equals(SESSION_VAR_WORK_AREA_ADD_USER)){
		contentC.removeAllComponents();
		uf = aum.aumModifier(contentC);
		return;
		
	}else if(curSessionWorkArea!= null && curSessionWorkArea.equals(SESSION_VAR_WORK_AREA_MANAGE_USER)){
		String curSessionUManage = (String) UI.getCurrent().getSession().getAttribute(ManageUserModule.SESSION_UMANAGE);
		if(curSessionUManage != null && curSessionUManage.equals(ManageUserModule.SESSION_VAR_UMANAGE_SEARCH)){
			
			String strSessionSearch = (String) UI.getCurrent().getSession().getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);
			if(strSessionSearch != null){
				if(searchC != null)
					contentC.removeComponent(searchC);
				
				if(uf !=null)
					contentC.removeComponent(uf);
				
				if(cuDetails != null)
					contentC.removeComponent(cuDetails);
				
				if(searchResultsC != null)
					contentC.removeComponent(searchResultsC);
				    searchC = (FormLayout)sum.sumModifier(curSessionUManage, contentC);
				    return;
				
			}

		}else if(curSessionUManage != null && curSessionUManage.equals(ManageUserModule.SESSION_VAR_UMANAGE_SEARCH_RESULTS)){
			if(cuDetails != null){
				contentC.removeComponent(cuDetails);
			}
			searchResultsC = (VerticalLayout)sum.sumModifier(curSessionUManage, contentC);
			return;
			
		}else if(curSessionUManage != null && curSessionUManage.equals(ManageUserModule.SESSION_VAR_UMANAGE_USER_ACTIONS)){
			
			if(searchResultsC != null){
				contentC.removeComponent(searchResultsC);
			}
			String strUDM =	(String) UI.getCurrent().getSession().getAttribute(UserDetailsModule.SESSION_UDM);
			if(strUDM == null){
				udm = new UserDetailsModule();
				cuDetails = udm.udmModifier(contentC, udm);
				UI.getCurrent().getSession().setAttribute(UserDetailsModule.SESSION_UDM, "active");
				
			}else{
				cuDetails = udm.udmModifier(contentC, udm);
			}
			
			return;
		}
			

	}
		
	
	
	}
	
	

}