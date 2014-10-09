package com.swifta.mats.web.usermanagement;

import com.swifta.mats.web.WorkSpace;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;

import elemental.events.MouseEvent.Button;



public class BtnActionsClickListener implements ClickListener{
	
	public static final String tbUsers = "user";
	public static final String SESSION_ACTIONS_TABLE = "session_actions_table";
	public static final String SESSION_ACTIONS_ROW_ID = "session_actions_table";
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5997208801734416844L;

	@Override
	public void buttonClick(ClickEvent event) {
			String strTableAndUID = event.getButton().getId();
			if(strTableAndUID.contains(tbUsers)){
				String arrTableAndUID[] = strTableAndUID.toString().split("_");
				UI.getCurrent().getSession().setAttribute(ManageUserModule.SESSION_UMANAGE, ManageUserModule.SESSION_VAR_UMANAGE_USER_DETAILS);
				UI.getCurrent().getSession().setAttribute(SESSION_ACTIONS_TABLE, arrTableAndUID[0]);
				UI.getCurrent().getSession().setAttribute(SESSION_ACTIONS_ROW_ID, arrTableAndUID[1]);
				if(WorkSpace.wsmu!=null)
					WorkSpace.wsmu.wsmuModifier();	
				System.out.println(FontAwesome.ADJUST);
			}
		}
	
	
	
	
	
	
	

}
