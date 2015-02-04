package com.swifta.mats.web.transactions;

import com.swifta.mats.web.Login;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TransView extends VerticalLayout implements View {

	private static final long serialVersionUID = 7453202011100914519L;
	public static final String WS = "dashboard";

	boolean isCriteriaChanged = false;
	String dCat;
	private TabSheet ts;
	private VerticalLayout tab = null;
	String id = null;

	public TransView(TabSheet ts) {
		ts.setSelectedTab(3);
		this.ts = ts;

		id = ts.getSelectedTab().getId();
		tab = (VerticalLayout) ts.getSelectedTab();
		if (id != null && id.equals("tx")) {
			addHeader();
			addMenu();
		} else {
			addHeader();
			addMenu();
			d();
			tab.setId("tx");
		}

	}

	@Override
	public void enter(ViewChangeEvent event) {

		ts.setSelectedTab(3);
		addMenu();

	}

	private void addHeader() {
		setMargin(true);
		Object user = UI.getCurrent().getSession().getAttribute("user");
		if (user == null)
			return;
		Button btnLogout = new Button("Logout");
		Label lbUsername = new Label("Hi, " + user.toString());
		lbUsername.setStyleName("lbel3");
		HorizontalLayout logoutC = new HorizontalLayout();
		logoutC.addComponent(lbUsername);
		logoutC.addComponent(btnLogout);
		logoutC.setStyleName("thy");

		addComponent(logoutC);
		setComponentAlignment(logoutC, Alignment.TOP_RIGHT);

		btnLogout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 3110441023351142376L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getSession().setAttribute("user", null);
				UI.getCurrent().getNavigator().navigateTo(Login.LOGIN);
				VaadinSession.getCurrent().close();

			}
		});

	}

	private void addMenu() {
		addComponent(ts);
	}

	private void d() {
		Transactions transaction = new Transactions();
		tab.addComponent(transaction.Addlabel());
	}

}
