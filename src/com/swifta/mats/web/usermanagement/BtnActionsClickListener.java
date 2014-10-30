package com.swifta.mats.web.usermanagement;

import java.util.List;

import com.swifta.mats.web.WorkSpace;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;



public class BtnActionsClickListener implements ClickListener{
	
	
	
	private static final long serialVersionUID = -5997208801734416844L;
	private boolean isPopup = false;
	public BtnActionsClickListener(boolean isPopup, List<Object> arrLCPopups){
		this.isPopup = isPopup;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
			String strTableAndUID = event.getButton().getId();
			
					String arrID[] = strTableAndUID.toString().split("_");
					
					UI.getCurrent().getSession().setAttribute(ManageUserModule.SESSION_UMANAGE, ManageUserModule.SESSION_VAR_UMANAGE_USER_ACTIONS);
					UI.getCurrent().getSession().setAttribute(UserDetailsModule.SESSION_UDM_TABLE, arrID[1]);
					UI.getCurrent().getSession().setAttribute(UserDetailsModule.SESSION_UDM_ID, arrID[2]);
					UI.getCurrent().getSession().setAttribute(UserDetailsModule.SESSION_UDM_UNAME, arrID[3]);
						if(WorkSpace.wsmu!=null)
							WorkSpace.wsmu.wsmuModifier();	
					
				}
			
	}
	
	
	
	
	
	
	


