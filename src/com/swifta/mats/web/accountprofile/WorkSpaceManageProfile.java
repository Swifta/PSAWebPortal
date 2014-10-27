package com.swifta.mats.web.accountprofile;
import com.swifta.mats.web.usermanagement.WorkSpaceManageUser;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class WorkSpaceManageProfile {
	public static final String SESSION_WSMP = "manage_profile";
	public static final String SESSION_WSMP_CUR_ACTION = "manage_profile_cur_action";
	public static String SESSION_VAR_WSMP_PERSONAL = "personal";
	public static String SESSION_VAR_WSMP_AUTH = "auth";
	public static String SESSION_VAR_WSMP_LOG = "log";
	public static String SESSION_VAR_WSMP_ACT_LOG = "act_log";
	public static String SESSION_VAR_WSMP_ACC_LOG = "acc_log";
	
	private VerticalLayout cParent;
	private VerticalLayout cMenu;
	private HorizontalLayout cContent;
	AccountProfileModule apm;
	
	public WorkSpaceManageProfile(){
		setCoreUI();
	}
	
	private void setCoreUI(){
		cParent = new VerticalLayout();
		cParent.setSizeFull();
		
		VerticalLayout cC = new VerticalLayout();
		cC.setSizeUndefined();
		
		cContent = new HorizontalLayout();
		cContent.setSizeFull();
		cContent.setStyleName("c_content");
		
		apm = new AccountProfileModule();
		HorizontalLayout pf = apm.getProfileContainer();
		cContent.addComponent(pf);
		cContent.setComponentAlignment(pf, Alignment.TOP_LEFT);
		
		ManageProfileModule mpm = new ManageProfileModule();
		cMenu= mpm.getMenu(cContent);
		cC.addComponent(cMenu);
		cMenu.setSizeUndefined();
		cC.setComponentAlignment(cMenu, Alignment.TOP_LEFT);
		
		
		cC.addComponent(cContent);
		cC.setExpandRatio(cContent, 1.0f);
		cParent.addComponent(cC);
		cParent.setComponentAlignment(cC, Alignment.TOP_CENTER);
		
		
	}
	
	public VerticalLayout getWorkSpaceAccountProfile(){
			return cParent;
		
	}
	
	public void wsmpModifier(){
		String strCurSession = (String) UI.getCurrent().getSession().getAttribute(SESSION_WSMP_CUR_ACTION);
		apm.apmModifier(strCurSession, cContent);
	}
	
}
