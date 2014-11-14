package com.swifta.mats.web.report;

import com.vaadin.ui.VerticalLayout;

public class Report extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 252829471857525213L;

	public VerticalLayout Addlabel() {
		Reportform formlayer = new Reportform();
		formlayer.reportformat();
		VerticalLayout formlayout = formlayer;
		return formlayout;
	}

}
