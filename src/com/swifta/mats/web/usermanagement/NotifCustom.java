package com.swifta.mats.web.usermanagement;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class NotifCustom {

	public static void show(String action, String msg) {
		create(action, msg);
	}

	private static void create(String action, String msg) {

		Window pop = new Window(action + " response");
		// pop.setStyleName();
		VerticalLayout cMessage = new VerticalLayout();
		cMessage.setMargin(true);
		Label lb = new Label(msg);
		cMessage.addComponent(lb);
		cMessage.setStyleName("c_notif " + ValoTheme.NOTIFICATION_FAILURE);
		cMessage.setComponentAlignment(lb, Alignment.MIDDLE_CENTER);
		pop.setContent(cMessage);
		UI.getCurrent().addWindow(pop);
		pop.setResizable(false);
		pop.setDraggable(false);
		pop.center();
	}

}
