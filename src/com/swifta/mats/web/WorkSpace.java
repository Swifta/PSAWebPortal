package com.swifta.mats.web;

import com.swifta.mats.web.accountprofile.AccountProfile;
import com.swifta.mats.web.dashboard.Dashboard;
import com.swifta.mats.web.report.Report;
import com.swifta.mats.web.settings.Settings;
import com.swifta.mats.web.transactions.Transactions;
import com.swifta.mats.web.usermanagement.Usermanager;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class WorkSpace extends VerticalLayout implements View {
	
	private Embedded emb;
	private Button btnLogout;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4370608410523325533L;
	/**
	 * 
	 */
	
	
	public static final String WORK_SPACE = "";
	VerticalLayout dashboard6 = new VerticalLayout();
	TabSheet tabsheet1 = new TabSheet();
	
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		//setSizeFull();
		setMargin(true);
		//setStyleName("parent_layout");
		
				btnLogout = new Button("Logout");
								
				Label lbUsername = new Label("Hi, "+UI.getCurrent().getSession().getAttribute("user").toString());
				lbUsername.setStyleName("lbel3");
				HorizontalLayout logoutC = new HorizontalLayout();
				
				
				
				logoutC.addComponent(lbUsername);
				logoutC.addComponent(btnLogout);
				logoutC.setStyleName("thy");
				
				addComponent(logoutC);
				setComponentAlignment(logoutC, Alignment.TOP_RIGHT);
				
				
		
		final VerticalLayout layout = new VerticalLayout();
		
		
		
		
		
		
        tabsheet1.setHeight("560px");
		tabsheet1.setSizeFull();
		tabsheet1.addStyleName("tabref");
	
		//Tab1 Dashboard
		VerticalLayout dashboard1 = new VerticalLayout();
		dashboard1.setImmediate(true);
		dashboard1.setHeight("500px");
		dashboard1.setWidth("1000px");
		dashboard1.setCaption("Test1");
		Dashboard dash = new Dashboard();
		dashboard1.addComponent(dash.Addlabel());
		tabsheet1.addTab(dashboard1,"Dashboard", null);
		
		//Tab2 Report
		VerticalLayout dashboard2 = new VerticalLayout();
		dashboard2.setImmediate(true);
		dashboard2.setHeight("500px");
		dashboard2.setWidth("1000px");
		dashboard2.setCaption("Test2");
		Report rep = new Report();
		dashboard2.addComponent(rep.Addlabel());
		tabsheet1.addTab(dashboard2,"Report", null);
		
		
		//Tab3 UserManager
		VerticalLayout dashboard3 = new VerticalLayout();
		dashboard3.setImmediate(true);
		dashboard3.setHeight("500px");
		dashboard3.setCaption("Test3");
		Usermanager usermanage = new Usermanager();
		dashboard3.addComponent(usermanage.Addlabel());
		tabsheet1.addTab(dashboard3,"User Management", null);
		
		VerticalLayout dashboard4 = new VerticalLayout();
		dashboard4.setImmediate(true);
		dashboard4.setHeight("500px");
		dashboard4.setWidth("1000px");
		dashboard4.setCaption("Test4");
		AccountProfile account = new AccountProfile();
		dashboard4.addComponent(account.Addlabel());
		tabsheet1.addTab(dashboard4,"Account Profile", null);
		
		VerticalLayout dashboard5 = new VerticalLayout();
		dashboard5.setImmediate(true);
		dashboard5.setHeight("500px");
		dashboard5.setWidth("1000px");
		dashboard5.setCaption("Test5");
		Transactions transaction = new Transactions();
		dashboard5.addComponent(transaction.Addlabel());
		
		tabsheet1.addTab(dashboard5,"Transactions", null);
		
		
		
		dashboard6.setImmediate(true);
		dashboard6.setHeight("500px");
		dashboard6.setWidth("1000px");
		dashboard6.setCaption("Test6");
		Settings setting = new Settings();
		dashboard6.addComponent(setting.Addlabel());
		tabsheet1.addTab(dashboard6,"Settings", null);
		//HorizontalLayout lay1 = new HorizontalLayout();
		
		
		
		layout.addComponent(tabsheet1);
		
		
		// TODO Auto-generated method stub
	    
		
		/*tabsheet1.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
			
			
			public void selectedTabChange(SelectedTabChangeEvent event) {
				// TODO Auto-generated method stub
				if(event.getTabSheet().getSelectedTab().getCaption() == "Test6" ){
					
					String het = tabsheet1.getSelectedTab().getCaption();
					Label lbel = new Label(het);
					Dashboard dash = new Dashboard();
					dashboard6.addComponent(dash.Addlabel());
					dashboard6.addComponent(lbel);
				}
			}
		} );*/
		btnLogout.addClickListener( new Button.ClickListener() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 3110441023351142376L;
		
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().getSession().setAttribute("user", null);
						UI.getCurrent().getSession().setAttribute(ManageUserModule.UMANAGE_SESSION_SEARCH, null);
						UI.getCurrent().getSession().setAttribute(ManageUserModule.UMANAGE_SESSION_DETAILS, null);
						UI.getCurrent().getNavigator().navigateTo(WORK_SPACE);
						
					}
				});
	    
	    
		addComponent(layout);
		setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		
		
	}

}
