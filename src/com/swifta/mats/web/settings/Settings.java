package com.swifta.mats.web.settings;



import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class Settings extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2467595854268829523L;
	
	public HorizontalLayout Addlabel() {
		// TODO Auto-generated method stub
		setSizeFull();
		setMargin(true);
		AddIcons icon1 = new AddIcons();
		HorizontalLayout laying = new HorizontalLayout();
		VerticalLayout het1 = icon1.ImagesClicking("Account Management","img/use.png");
		VerticalLayout het2 = icon1.ImagesClicking("Permissions","img/permission.png");
		VerticalLayout het3 = icon1.ImagesClicking("Authentication","img/auth.png");
		VerticalLayout het4 = icon1.ImagesClicking("Fees/ Commission","img/fees.png");
		VerticalLayout het5 = icon1.ImagesClicking("Transfer","img/transfer.png");
		VerticalLayout het6 = icon1.ImagesClicking("Threshold","img/threshold.png");
		
		laying.addComponent(het1);
		laying.addComponent(het2);
		laying.addComponent(het3);
		laying.addComponent(het4);
		laying.addComponent(het5);
		laying.addComponent(het6);
		
		
		return laying;
		
	}
	

}
