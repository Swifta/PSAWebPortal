package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;

import com.swifta.mats.web.WorkSpace;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;



public class BtnTabLikeClickListener implements Button.ClickListener{
	private ArrayList<BtnTabLike>arrLTabBtns;
	private HorizontalLayout hTabContainer;
	private WorkSpaceManageUser  wsmu;
	private Window popup;
	private UserDetailsModule udm;
	private VerticalLayout cPopupMsg;
	private String strTbName;
	private String strUID;
	private boolean isModifier;
	private String[] strSession;
	private String[] strSessionVar;
	private String curBtnID;
	public BtnTabLikeClickListener(boolean isModifier, ArrayList<BtnTabLike>arrLTabBtns, HorizontalLayout tabContainer, UserDetailsModule udm, String strTbName, String strUID){
		this.arrLTabBtns = arrLTabBtns;
		
		this.hTabContainer = tabContainer;
		this.udm = udm;
		this.strTbName = strTbName;
		this.strUID = strUID;
		this.isModifier = isModifier;
	}
	
	
	public BtnTabLikeClickListener(boolean isModifier, ArrayList<BtnTabLike>arrLTabBtns, String[] strSession,String[] strSessionVar, WorkSpaceManageUser wsmu){
		this.arrLTabBtns = arrLTabBtns;
		this.strSession = strSession;
		this.strSessionVar = strSessionVar;
		this.isModifier = isModifier;
		this.wsmu = wsmu;
	}

	private static final long serialVersionUID = -6544444429248747390L;

	@Override
	public void buttonClick(ClickEvent event) {
			BtnTabLike curBtn = (BtnTabLike) event.getButton();		
			
			if(isModifier){
				if(UserDetailsModule.uDetailsEditStatus){
					UI.getCurrent().addWindow(getWarningPopWindow(curBtn));
				}else{
				for(int i = 0; i < strSession.length; i++){
				 UI.getCurrent().getSession().setAttribute(strSession[i],strSessionVar[i]);
				}
					
					if(WorkSpace.wsmu != null){
						WorkSpace.wsmu.wsmuModifier();
						curBtn.setDisableOnClick(true);
						setActiveTab(curBtn, arrLTabBtns);
					} 
				
				}
			
				
			}else{
				if(UserDetailsModule.uDetailsEditStatus){
					UI.getCurrent().addWindow(getWarningPopWindow(curBtn));
				}else{
					
					setActiveTab(curBtn, arrLTabBtns);
					hTabContainer.removeAllComponents();
					hTabContainer.addComponent(udm.getDetailsForm(strTbName, strUID));
				}	
			}		
	}
	
	
	private void setActiveTab(BtnTabLike curBtn, ArrayList<BtnTabLike>arrLTabBtns){
		curBtn.setStyleName("btn_tab_like btn_tab_like_active");
		for(BtnTabLike btn: arrLTabBtns){
			if(!curBtn.equals(btn)){
				btn.setStyleName("btn_tab_like");
				btn.setEnabled(true);
			}
		}
	}
	
	
	private Window getWarningPopWindow(BtnTabLike curBtn){
		curBtn.addStyleName("btn_tab_like");
		popup = new Window("Unsaved changes");
		popup.center();
		cPopupMsg = new VerticalLayout();
		cPopupMsg.setMargin(true);
		cPopupMsg.setSpacing(true);
		HorizontalLayout cUnsavedDataMsg = new HorizontalLayout();
		Label lbUnsavedDataMsg = new Label("All unsaved changes on this page will be lost. \rStay on this Page?");
		//Label lbUnsavedDataMsg = new Label("Page contains unsaved changes. Stay on this Page?");
		
		cUnsavedDataMsg.addComponent(lbUnsavedDataMsg);
		HorizontalLayout cPopupBtns = new HorizontalLayout();
		cPopupBtns.setSizeUndefined();
		cPopupBtns.setSpacing(true);
		
		Button btnLeavePage = new Button("Leave this page.");
		Button btnYes = new Button("Yes.");
		cPopupBtns.addComponent(btnYes);
		cPopupBtns.addComponent(btnLeavePage);
		
		cPopupMsg.addComponent(cUnsavedDataMsg );
		cPopupMsg.addComponent(cPopupBtns);
		cPopupMsg.setComponentAlignment(cUnsavedDataMsg, Alignment.MIDDLE_CENTER);
		cPopupMsg.setComponentAlignment(cPopupBtns, Alignment.BOTTOM_CENTER);
		
		
		popup.setContent(cPopupMsg);
		return popup;
	}
	
}
			
			
			
			
			
			
		
	
