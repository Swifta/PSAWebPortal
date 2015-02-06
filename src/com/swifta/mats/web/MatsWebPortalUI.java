package com.swifta.mats.web;

import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("mats_web_portal")
@PreserveOnRefresh
public class MatsWebPortalUI extends UI {

	public static TabSheet ts;
	private HashMap<String, Integer> hm;
	private String vN = null;
	public static final String dbconn = "jdbc:mysql://173.194.251.79:3306/psadatasource";

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, closeIdleSessions = true, ui = MatsWebPortalUI.class, widgetset = "com.swifta.mats.web.widgetset.Mats_web_portalWidgetset")
	public static class Servlet extends VaadinServlet {

		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();
			// getService().addSessionDestroyListener(this);

			getService().setSystemMessagesProvider(
					new SystemMessagesProvider() {

						@Override
						public SystemMessages getSystemMessages(
								SystemMessagesInfo systemMessagesInfo) {
							CustomizedSystemMessages msg = new CustomizedSystemMessages();
							msg.setSessionExpiredNotificationEnabled(false);
							msg.setSessionExpiredMessage("Click HERE to login again.");

							return msg;
						}

					});

		}

	}

	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		hm = new HashMap<>();
		Initializer initializer = new Initializer();

		ts = initializer.getTS(hm);
		getNavigator().addView("", new Login());
		getNavigator().addView(Login.LOGIN, Login.class);
		getNavigator().addView(Main.WS, Main.class);

		getNavigator().setErrorProvider(new ErrorVIEW());

		getNavigator().addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {

				boolean isErrorView = event.getNewView() instanceof ErrorVIEW;
				boolean isLoggedIn = UI.getCurrent().getSession()
						.getAttribute("user") != null;
				View prevView = event.getOldView();
				if (prevView != null && prevView.equals(event.getNewView()))
					return false;

				if (isErrorView) {
					vN = null;
					if (prevView != null && isLoggedIn) {
						Notification.show("Resource of \""
								+ UI.getCurrent().getPage().getLocation()
								+ "\" does not exist.",
								Notification.Type.ERROR_MESSAGE);

						prevView.enter(event);

						return false;
					} else {

						getNavigator().navigateTo(Login.LOGIN);
						return false;
					}

				}

				vN = event.getViewName();

				boolean isLoginView = event.getNewView() instanceof Login;

				if (!isLoggedIn)
					VaadinSession.getCurrent().getSession()
							.setMaxInactiveInterval(3600);

				if (!isLoginView && !isLoggedIn) {
					vN = null;
					getNavigator().navigateTo(Login.LOGIN);
					return false;
				}

				if (isLoginView) {
					if (isLoggedIn) {
						vN = null;
						getNavigator().navigateTo(Main.WS);
						return false;
					} else {
						return true;
					}

				} else {
					if (!isLoggedIn) {
						vN = null;
						getNavigator().navigateTo(Login.LOGIN);
						return false;
					}
				}

				return true;

			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {

			}

		});

		UI.getCurrent()
				.getPage()
				.addUriFragmentChangedListener(
						new Page.UriFragmentChangedListener() {

							@Override
							public void uriFragmentChanged(

							UriFragmentChangedEvent event) {

								if (vN != null)
									enter();

							}
						});

	}

	private void enter() {

		UI.getCurrent().getNavigator().navigateTo(vN);

	}
}