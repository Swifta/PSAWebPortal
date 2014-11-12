package com.swifta.mats.web;

import javax.servlet.annotation.WebServlet;

import com.swifta.mats.web.usermanagement.ManageUserModule;
import com.swifta.mats.web.usermanagement.WorkSpaceManageUser;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("mats_web_portal")
// @PreserveOnRefresh
public class MatsWebPortalUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MatsWebPortalUI.class, widgetset = "com.swifta.mats.web.widgetset.Mats_web_portalWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		getNavigator().addView(Login.LOGIN, Login.class);
		getNavigator().addView(WorkSpace.WORK_SPACE, WorkSpace.class);
		// getNavigator().addView(WorkSpaceManageUser.WORK_AREA,
		// WorkSpaceManageUser.class);

		// Setting Default Sessions and Session Variable
		UI.getCurrent()
				.getSession()
				.setAttribute(WorkSpaceManageUser.SESSION_WORK_AREA,
						WorkSpaceManageUser.SESSION_VAR_WORK_AREA_MANAGE_USER);

		UI.getCurrent()
				.getSession()
				.setAttribute(ManageUserModule.SESSION_UMANAGE,
						ManageUserModule.SESSION_VAR_UMANAGE_SEARCH);

		UI.getCurrent().getNavigator()
				.addViewChangeListener(new ViewChangeListener() {

					@Override
					public boolean beforeViewChange(ViewChangeEvent event) {
						boolean isLoggedIn = UI.getCurrent().getSession()
								.getAttribute("user") != null;
						boolean isLoginView = event.getNewView() instanceof Login;
						if (isLoggedIn && isLoginView) {
							return false;
						} else if (!isLoggedIn && !isLoginView) {
							getNavigator().navigateTo(Login.LOGIN);
							return false;

						}
						return true;

					}

					@Override
					public void afterViewChange(ViewChangeEvent event) {

					}

				});

	}

}