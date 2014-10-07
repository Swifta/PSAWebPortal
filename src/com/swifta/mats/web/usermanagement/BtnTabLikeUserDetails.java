package com.swifta.mats.web.usermanagement;

import java.util.ArrayList;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class BtnTabLikeUserDetails extends Button{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3990984335383694700L;
	public BtnTabLikeUserDetails(String strCaption, String btnId){
		super(strCaption);
		this.setId(btnId);
		
	}

	
	
	/*public void setArrLBtns(ArrayList<BtnTabLikeUserDetails> arrLBtns){
		this.arrLBtns = arrLBtns;
	}
	
	
	private  void setUDetailsContainer(){
		UserDetailsModule udm = new UserDetailsModule();
		container.addComponent(udm.getDetailsForm("Personal", "001"));
		
		
		
	}
	
	public HorizontalLayout getUDetailsContainer(){
		return this.container;
	}*/
	
	

}
