package com.swifta.mats.web.settings;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class AddIcons extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6579106765177346022L;

	public VerticalLayout ImagesClicking(String write, String ilogo) {
		ThemeResource iLogo = new ThemeResource(ilogo);
		Image image = new Image("", iLogo);

		VerticalLayout layout1 = new VerticalLayout();
		VerticalLayout layout2 = new VerticalLayout();
		VerticalLayout layout3 = new VerticalLayout();
		Label name = new Label(write);

		name.setSizeUndefined();
		name.setWidth("110px");
		name.setStyleName("lge");

		layout1.addComponent(image);
		layout1.addComponent(name);

		layout1.setHeight("180px");
		layout1.setWidth("130px");

		layout1.setStyleName("see_lay");
		layout1.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		layout1.setComponentAlignment(name, Alignment.BOTTOM_CENTER);

		/*
		 * layout1.addLayoutClickListener(new LayoutClickListener() {
		 * 
		 * private static final long serialVersionUID = 1L;
		 * 
		 * public void layoutClick(LayoutClickEvent event) { //
		 * Notification.show("I have been thoroughly clicked....!!"); } });
		 */

		return layout1;

	}

}
