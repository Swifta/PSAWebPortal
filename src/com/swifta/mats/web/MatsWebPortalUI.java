package com.swifta.mats.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.swifta.mats.web.usermanagement.ManageUserModule;
import com.swifta.mats.web.usermanagement.WorkSpaceManageUser;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("mats_web_portal")
@PreserveOnRefresh
public class MatsWebPortalUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, closeIdleSessions = true, ui = MatsWebPortalUI.class, widgetset = "com.swifta.mats.web.widgetset.Mats_web_portalWidgetset")
	public static class Servlet extends VaadinServlet {

		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();
			// getService().addSessionDestroyListener(this);

			/*
			 * getService().setSystemMessagesProvider( new
			 * SystemMessagesProvider() {
			 * 
			 * @Override public SystemMessages getSystemMessages(
			 * SystemMessagesInfo systemMessagesInfo) { CustomizedSystemMessages
			 * msg = new CustomizedSystemMessages();
			 * msg.setSessionExpiredNotificationEnabled(true);
			 * msg.setSessionExpiredMessage("Click HERE to login again.");
			 * 
			 * return msg; }
			 * 
			 * });
			 */

		}

	}

	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		// setContent(new WorkSpace());
		getNavigator().addView(Login.LOGIN, Login.class);
		getNavigator().addView(WorkSpace.WORK_SPACE, WorkSpace.class);
		setContent(new Login());
		UI.getCurrent()
				.getSession()
				.setAttribute(WorkSpaceManageUser.SESSION_WORK_AREA,
						WorkSpaceManageUser.SESSION_VAR_WORK_AREA_MANAGE_USER);

		UI.getCurrent()
				.getSession()
				.setAttribute(ManageUserModule.SESSION_UMANAGE,
						ManageUserModule.SESSION_VAR_UMANAGE_SEARCH);

		VaadinSession.getCurrent().getSession().setMaxInactiveInterval(60);

		getNavigator().addViewChangeListener(new ViewChangeListener() {

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