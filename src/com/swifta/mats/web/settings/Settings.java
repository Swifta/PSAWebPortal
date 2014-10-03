package com.swifta.mats.web.settings;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Settings extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2467595854268829523L;
	
	public Label Addlabel() {
		// TODO Auto-generated method stub
		setSizeFull();
		setMargin(true);
		Label lbel = new Label("Welcome to Settings tab!");
		return lbel;
	}

}
