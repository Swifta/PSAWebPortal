package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class BtnTabLike extends Button{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3990984335383694700L;
	public BtnTabLike(String strCaption, String btnId){
		super(strCaption);
		this.setId(btnId);
		this.setStyleName("btn_tab_like" );
	}

	
	

}
