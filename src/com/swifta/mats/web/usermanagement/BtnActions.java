package com.swifta.mats.web.usermanagement;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class BtnActions extends Button {

	private static final long serialVersionUID = -4946098576662520481L;

	public BtnActions(String strDescription) {
		this.setStyleName(ValoTheme.BUTTON_ICON_ONLY + " btn_link");
		this.setDescription(strDescription);
	}

}
