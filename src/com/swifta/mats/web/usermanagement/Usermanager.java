package com.swifta.mats.web.usermanagement;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Usermanager extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4979037459171897511L;
	
	public Label Addlabel() {
		// TODO Auto-generated method stub
		setSizeFull();
		setMargin(true);
		Label lbel = new Label("Welcome to User Management tab!");
		return lbel;
	}

}
