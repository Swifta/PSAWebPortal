package com.swifta.mats.web.report;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Report extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 252829471857525213L;
	
	public Label Addlabel() {
		// TODO Auto-generated method stub
		setSizeFull();
		setMargin(true);
		Label lbel = new Label("Welcome to Report tab!");
		return lbel;
	}

}
