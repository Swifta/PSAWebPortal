package com.swifta.mats.web;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class StartView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5655787374268675230L;

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to our start view");
		
	}

}
