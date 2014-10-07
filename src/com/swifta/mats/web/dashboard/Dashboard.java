package com.swifta.mats.web.dashboard;


import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Dashboard extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866681927023505574L;

	public Label Addlabel() {
		// TODO Auto-generated method stub
		setSizeFull();
		setMargin(true);
		Label lbel = new Label("Welcome to Dashboard tab!");
		return lbel;
	}
	
	

}
