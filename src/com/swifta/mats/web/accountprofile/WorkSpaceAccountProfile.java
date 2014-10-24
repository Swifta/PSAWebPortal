package com.swifta.mats.web.accountprofile;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class WorkSpaceAccountProfile {
	private VerticalLayout cParent;
	private VerticalLayout cMenu;
	private HorizontalLayout cContent;
	AccountProfileModule apm;
	
	public WorkSpaceAccountProfile(){
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
	
	public void wsapModifier(){
		String strCurSession = (String) UI.getCurrent().getSession().getAttribute(ManageProfileModule.SESSION_MPM);
		//Notification.show(strCurSession);
		apm.apmModifier(strCurSession, cContent);
	}
	
}
