package com.swifta.mats.web.accountprofile;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class AccountProfile extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7229412704604360658L;
	
	public Label Addlabel() {
		// TODO Auto-generated method stub
		//setSizeFull();
		setMargin(true);
		Label lbel = new Label("Welcome to Account Profile tab!");
		return lbel;
	}

}
