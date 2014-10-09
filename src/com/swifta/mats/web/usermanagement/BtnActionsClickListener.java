package com.swifta.mats.web.usermanagement;

import com.swifta.mats.web.WorkSpace;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;

import elemental.events.MouseEvent.Button;



public class BtnActionsClickListener implements ClickListener{
	
	
	
	private static final long serialVersionUID = -5997208801734416844L;
	
	public BtnActionsClickListener(){
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
			String strTableAndUID = event.getButton().getId();
			if(strTableAndUID.contains(SearchUserModule.tbUsers)){
				String arrTableAndUID[] = strTableAndUID.toString().split("_");
				UI.getCurrent().getSession().setAttribute(ManageUserModule.SESSION_UMANAGE, ManageUserModule.SESSION_VAR_UMANAGE_USER_ACTIONS);
				UI.getCurrent().getSession().setAttribute(SearchUserModule.SESSION_USER_TABLE, arrTableAndUID[0]);
				UI.getCurrent().getSession().setAttribute(SearchUserModule.SESSION_USER_TABLE_ROW_ID, arrTableAndUID[1]);
				UI.getCurrent().getSession().setAttribute(SearchUserModule.SESSION_USER_ACTION, arrTableAndUID[2]);
				if(WorkSpace.wsmu!=null)
					WorkSpace.wsmu.wsmuModifier();	
				
			}
		}
	
	
	
	
	
	
	

}
