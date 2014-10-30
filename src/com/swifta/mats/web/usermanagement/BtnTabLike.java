package com.swifta.mats.web.usermanagement;

import com.vaadin.ui.Button;

public class BtnTabLike extends Button{
public static BtnTabLike btnTabPrev;
public static BtnTabLike btnTabCur;


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
