package com.swifta.mats_web_portal;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;



public class MenuItemClickable extends HorizontalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8462407485011099214L;
	Label lb;
	public MenuItemClickable(String caption, String role){
		final String curRole = role;
		lb = new Label(caption);
		lb.setStyleName("label_menu_item");
		addComponent(lb);
		setSizeFull();
		super.addLayoutClickListener(new LayoutClickListener(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 2293010608049670769L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if(event.getChildComponent() == lb){
					
					
					if(curRole == "add_user"){
						UI.getCurrent().getNavigator().navigateTo(WorkAreax.WORK_AREA+"/"+curRole);
						
					}
					
					if(curRole == "manage_user"){
						UI.getCurrent().getNavigator().navigateTo(WorkAreax.WORK_AREA+"/"+curRole);
					}
				}
			}
			
		});
	}
	
	
}
