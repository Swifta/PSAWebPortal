package com.swifta.mats.web.transactions;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Transactions extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6777931988784969925L;
	
	public Label Addlabel() {
		// TODO Auto-generated method stub
		//setSizeFull();
		setMargin(true);
		Label lbel = new Label("Welcome to Transaction tab!");
		return lbel;
	}

}
