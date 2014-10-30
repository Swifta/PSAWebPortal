package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;

import com.swifta.mats.web.WorkSpace;
import com.swifta.mats.web.accountprofile.ManageProfileModule;
import com.swifta.mats.web.accountprofile.WorkSpaceManageProfile;
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
	//private HorizontalLayout hTabContainer;
	private Window popup;
	private Object udm;
	private VerticalLayout cPopupMsg;
	private String strTbName;
	private String strUID;
	
	private String[] arrSessions;
	private String[] arrSessionVars;
	private boolean hasSubMenu = false;
	private HorizontalLayout cSubMenu;
	private ArrayList<HorizontalLayout> arrLSubTabs;
	BtnTabLike curBtn;
	private boolean hasOp;
	boolean boolEditStatus = false;
	private String strUserType;
	
	
	//Original subMenu
	public BtnTabLikeClickListener(
			boolean hasSubMenu,
			ArrayList<BtnTabLike>arrLTabBtns,
			HorizontalLayout cSubMenu,
			HorizontalLayout hTabContainer,
			Object udm, String strTbName,
			String strUID, boolean hasOp,
			boolean boolEditStatus){
		
		this.hasSubMenu = hasSubMenu;
		this.cSubMenu = cSubMenu;
		this.arrLTabBtns = arrLTabBtns;
		//this.hTabContainer = hTabContainer;
		this.strTbName = strTbName;
		this.strUID = strUID;
		this.udm = udm;
		
		this.boolEditStatus = boolEditStatus;
		this.hasOp = hasOp;
		
		
		
	}
	
	
	//HasSubMenu constructor
	public BtnTabLikeClickListener(
			boolean hasSubMenu,
			ArrayList<HorizontalLayout> arrLSubTabs,
			ArrayList<BtnTabLike>arrLTabBtns,
			HorizontalLayout cSubMenu,
			HorizontalLayout hTabContainer,
			Object udm, String strTbName,
			String strUID, boolean hasOp,
			boolean boolEditStatus){
		
		this.hasSubMenu = hasSubMenu;
		this.cSubMenu = cSubMenu;
		this.arrLTabBtns = arrLTabBtns;
		//this.hTabContainer = hTabContainer;
		this.strTbName = strTbName;
		this.strUID = strUID;
		this.udm = udm;
		
		this.boolEditStatus = boolEditStatus;
		this.hasOp = hasOp;
		this.arrLSubTabs = arrLSubTabs;
		
		
		
	}
	
	/*public BtnTabLikeClickListenerx(
			boolean hasSubMenu,
			ArrayList<HorizontalLayout> arrLSubTabs, 
			ArrayList<BtnTabLike>arrLTabBtns,
			HorizontalLayout tabContainer,
			Object udm,
			String strTbName,
			String strUID,
			boolean hasOp,
			boolean boolEditStatus,
			String strUserType){
		this.arrLTabBtns = arrLTabBtns;
		this.hTabContainer = tabContainer;
		this.udm = udm;
		this.strTbName = strTbName;
		this.strUID = strUID;
		
		this.hasSubMenu = hasSubMenu;
		this.arrLSubTabs = arrLSubTabs;
		this.boolEditStatus = boolEditStatus;
		this.hasOp = hasOp;
		this.strUserType = strUserType;
		
		
	}*/

	
	//Original No submenu constructor
	public BtnTabLikeClickListener(
			boolean hasSubMenu,
			ArrayList<HorizontalLayout> arrLSubTabs, 
			ArrayList<BtnTabLike>arrLTabBtns,
			HorizontalLayout tabContainer,
			Object udm,
			String strTbName,
			String strUID,
			boolean hasOp,
			boolean boolEditStatus){
		this.arrLTabBtns = arrLTabBtns;
		//this.hTabContainer = tabContainer;
		this.udm = udm;
		this.strTbName = strTbName;
		this.strUID = strUID;
		this.hasSubMenu = hasSubMenu;
		this.arrLSubTabs = arrLSubTabs;
		this.boolEditStatus = boolEditStatus;
		this.hasOp = hasOp;
		
		
		
	}
	
	
	//No subMenu
	public BtnTabLikeClickListener(
			boolean hasSubMenu,
			ArrayList<HorizontalLayout> arrLSubTabs, 
			ArrayList<BtnTabLike>arrLTabBtns,
			
			HorizontalLayout cSubMenu,
			Object udm,
			String strTbName,
			String strUID,
			boolean hasOp,
			boolean boolEditStatus,
			String[] arrSessions,
			String[] arrSessionVars){
		
		this.arrLTabBtns = arrLTabBtns;
		this.arrSessions = arrSessions;
		this.arrSessionVars = arrSessionVars;
		
		this.arrLTabBtns = arrLTabBtns;
		this.cSubMenu = cSubMenu;
		this.udm = udm;
		this.strTbName = strTbName;
		this.strUID = strUID;
		this.hasSubMenu = hasSubMenu;
		this.arrLSubTabs = arrLSubTabs;
		this.boolEditStatus = boolEditStatus;
		this.hasOp = hasOp;
		
	}
	
	
	
	
	

	private static final long serialVersionUID = -6544444429248747390L;

	@Override
	public void buttonClick(ClickEvent event) {
			curBtn = (BtnTabLike) event.getButton();
					  modifyMenuContent();	
	}
	
	
	private void setActiveTab(BtnTabLike curBtn, ArrayList<BtnTabLike>arrLTabBtns){
		curBtn.setStyleName("btn_tab_like btn_tab_like_active");
		for(BtnTabLike btn: arrLTabBtns){
			if(!curBtn.equals(btn)){
				btn.setStyleName("btn_tab_like");
				btn.setEnabled(true);
			}else{
				btn.setEnabled(false);
			}
		}
		
	}
	
	public void selectActiveUserType(){
		
		if(BtnTabLike.btnTabPrev == null)return;
		
		String strCurBtnCap = BtnTabLike.btnTabCur.getCaption();
		//String strPrevBtnCap = BtnTabLike.btnTabPrev.getCaption();
		BtnTabLike btn = null;
		
		BtnTabLike.btnTabPrev.setStyleName("btn_tab_like");
		BtnTabLike.btnTabPrev.setEnabled(true);
		
		for(HorizontalLayout h: arrLSubTabs){
			for(int i = 0; i < h.getComponentCount(); i++){
				
				if(h.getStyleName().equals("c_sub_menu_visible")){
					btn = (BtnTabLike)h.getComponent(i);
					if(strCurBtnCap.equals(btn.getCaption())){
						btn.setStyleName("btn_tab_like btn_tab_like_active");
						btn.setEnabled(false);
					}else{
						btn.setStyleName("btn_tab_like");
						btn.setEnabled(true);
					}
				}
					
			}
		}
		
		
	}
	
	
	private Window getWarningPopWindow(){
		curBtn.addStyleName("btn_tab_like");
		popup = new Window("Unsaved changes");
		popup.center();
		cPopupMsg = new VerticalLayout();
		cPopupMsg.setMargin(true);
		cPopupMsg.setSpacing(true);
		HorizontalLayout cUnsavedDataMsg = new HorizontalLayout();
		Label lbUnsavedDataMsg = new Label("All unsaved changes on this page will be lost. \rStay on this Page?");
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
		
		btnYes.addClickListener(new Button.ClickListener() {
			
		
			private static final long serialVersionUID = -4241921726926290840L;

			@Override
			public void buttonClick(ClickEvent event) {
				popup.close();
			}
		});
		
		btnLeavePage.addClickListener(new Button.ClickListener() {
			
		
			private static final long serialVersionUID = 7890239257486668503L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserDetailsModule.uDetailsEditStatus = false;
				popup.close();
				
				if(hasSubMenu){
					modifyMenuContent();
				}
				
				
					
					if(udm instanceof ManageProfileModule){
						//Notification.show("Hello....");
						for(int i = 0; i < arrSessions.length; i++){
							 UI.getCurrent().getSession().setAttribute(arrSessions[i],arrSessionVars[i]);
							}
						if(WorkSpace.wsmp != null) 
							WorkSpace.wsmp.wsmpModifier();
								setActiveTab(curBtn, arrLTabBtns);
					}else{	
					
						if(WorkSpace.wsmu != null){
							for(int i = 0; i < arrSessions.length; i++){
								 UI.getCurrent().getSession().setAttribute(arrSessions[i],arrSessionVars[i]);
								}
							WorkSpace.wsmu.wsmuModifier();
			
							setActiveTab(curBtn, arrLTabBtns);
						} 
					}
				
					
					/*/x/setActiveTab(curBtn, arrLTabBtns);
					hTabContainer.removeAllComponents();
					if(udm instanceof UserDetailsModule){
						
						hTabContainer.addComponent(((UserDetailsModule)udm).getDetailsForm(strTbName, strUID, hasOp, boolEditStatus));
					}else{
						//Notification.show("Leave me alone.");
					}/x/*/
					//hTabContainer.addComponent(udm.getDetailsForm(strTbName, strUID, hasOp, boolEditStatus));
				
				
			}
		});
		
		
		popup.setContent(cPopupMsg);
		return popup;
	}
	
	private void modifyMenuContent(){
		
		
		if(UserDetailsModule.uDetailsEditStatus){
			UI.getCurrent().addWindow(getWarningPopWindow());
			return;
		}
		
		if(hasSubMenu){
			if(arrLSubTabs != null){
				for(HorizontalLayout sm: arrLSubTabs){
					sm.setStyleName("c_sub_menu_invisible");
				}
				
				
				/*
				 * Next line is important for only one reason...
				 * 1. Ensure that child Menu does not hide its Parent
				 */
				curBtn.getParent().setStyleName("c_sub_menu_visible");
				cSubMenu.setStyleName("c_sub_menu_visible");
				
				strUserType = (String)UI.getCurrent().getSession().getAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE);
				arrSessionVars[0] = strUserType;
				selectActiveUserType();	
			}
		}else if(!hasSubMenu){
			BtnTabLike.btnTabPrev = BtnTabLike.btnTabCur;
			BtnTabLike.btnTabCur = this.curBtn;
			
			
		}
		
		
		setActiveTab(curBtn, arrLTabBtns);
		for(int i = 0; i < arrSessions.length; i++){
			
			 UI.getCurrent().getSession().setAttribute(arrSessions[i], arrSessionVars[i]);
			 
		}
		
		UI.getCurrent().getSession().setAttribute(ManageUserModule.SESSION_UMANAGE, ManageUserModule.SESSION_VAR_UMANAGE_SEARCH);
		if(WorkSpace.wsmu != null)
			WorkSpace.wsmu.wsmuModifier();
		
		
		//if(hTabContainer == null)return;		
		//hTabContainer.removeAllComponents();
			/*xxxx/if(udm instanceof UserDetailsModule){
				//hTabContainer.addComponent(((UserDetailsModule)udm).getDetailsForm(strTbName, strUID, hasOp, boolEditStatus));
			}else if(udm instanceof AddUserModule){
				
				for(int i = 0; i < arrSessions.length; i++){
					 UI.getCurrent().getSession().setAttribute(arrSessions[i], arrSessionVars[i]);
				}
				
				UI.getCurrent().getSession().setAttribute(WorkSpaceManageUser.SESSION_WORK_AREA, WorkSpaceManageUser.SESSION_VAR_WORK_AREA_ADD_USER);
				if(WorkSpace.wsmu != null)
					WorkSpace.wsmu.wsmuModifier();
				
				
								
			}else if(udm instanceof ManageUserModule){
				
								
				for(int i = 0; i < arrSessions.length; i++){
					 UI.getCurrent().getSession().setAttribute(arrSessions[i], arrSessionVars[i]);
				}
				
				UI.getCurrent().getSession().setAttribute(WorkSpaceManageUser.SESSION_WORK_AREA, WorkSpaceManageUser.SESSION_VAR_WORK_AREA_MANAGE_USER);
				UI.getCurrent().getSession().setAttribute(ManageUserModule.SESSION_UMANAGE, ManageUserModule.SESSION_VAR_UMANAGE_SEARCH);
				
				if(WorkSpace.wsmu != null)
					WorkSpace.wsmu.wsmuModifier();
				
				
			}else if(udm instanceof ManageProfileModule){
				
				UI.getCurrent().getSession().setAttribute(WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION, WorkSpaceManageProfile.SESSION_VAR_WSMP_ACT_LOG);
				if(WorkSpace.wsmp != null)
					WorkSpace.wsmp.wsmpModifier();
				
				
		}xxxxx*/
			
		
				
	}
	
	
	private void modifyContent(){
		
		if(UserDetailsModule.uDetailsEditStatus){
			UI.getCurrent().addWindow(getWarningPopWindow());
			return;
		}
		BtnTabLike.btnTabPrev = BtnTabLike.btnTabCur;
		BtnTabLike.btnTabCur = this.curBtn;
		
		if(arrLSubTabs != null){
			for(HorizontalLayout sm: arrLSubTabs){
				sm.setStyleName("c_sub_menu_invisible");
			}
			
			
			/*
			 * Next line is important for only one reason...
			 * 1. Ensure that child Menu does not hide Parent Menu
			 */
			curBtn.getParent().setStyleName("c_sub_menu_visible");
		}
			
				setActiveTab(curBtn, arrLTabBtns);
			
				if(udm instanceof UserDetailsModule){
					///hTabContainer.addComponent(((UserDetailsModule)udm).getDetailsForm(strTbName, strUID, hasOp, boolEditStatus));
				}else if(udm instanceof AddUserModule){
					UI.getCurrent().getSession().setAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE, strUserType);
					UI.getCurrent().getSession().setAttribute(WorkSpaceManageUser.SESSION_WORK_AREA, WorkSpaceManageUser.SESSION_VAR_WORK_AREA_ADD_USER);
					if(WorkSpace.wsmu != null)
						WorkSpace.wsmu.wsmuModifier();
					
									
				}else if(udm instanceof ManageUserModule){
					
					UI.getCurrent().getSession().setAttribute(WorkSpaceManageUser.SESSION_WORK_AREA_USER_TYPE, strUserType);
					UI.getCurrent().getSession().setAttribute(WorkSpaceManageUser.SESSION_WORK_AREA, WorkSpaceManageUser.SESSION_VAR_WORK_AREA_MANAGE_USER);
					UI.getCurrent().getSession().setAttribute(ManageUserModule.SESSION_UMANAGE, ManageUserModule.SESSION_VAR_UMANAGE_SEARCH);
					
					if(WorkSpace.wsmu != null)
						WorkSpace.wsmu.wsmuModifier();
					
				}else if(udm instanceof ManageProfileModule){
					
					UI.getCurrent().getSession().setAttribute(WorkSpaceManageProfile.SESSION_WSMP_CUR_ACTION, WorkSpaceManageProfile.SESSION_VAR_WSMP_ACT_LOG);
					if(WorkSpace.wsmp != null)
						WorkSpace.wsmp.wsmpModifier();
					
					
			}
		
		}
	
	
	
	
	
	
	
	
	
	
}
			
			
			
			
			
			
		
	
