package com.swifta.mats.web.settings;

import java.util.ArrayList;

import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class WorkSpaceManageFeesAndComm {
	// public static final String SESSION_WSMP = "manage_commission_and_fees";
	// public static final String SESSION_WSMP_CUR_ACTION =
	// "manage_profile_cur_action";
	// static String SESSION_VAR_WSMP_PERSONAL = "personal";
	// public static String SESSION_VAR_WSMP_AUTH = "cur_user_auth";
	// static String SESSION_VAR_WSMP_LOG = "log";
	// public static String SESSION_VAR_WSMP_ACT_LOG = "act_log";
	// public static String SESSION_VAR_WSMP_ACC_LOG = "acc_log";

	// public static String SESSION_UDM_TABLE_LOG = "cur_user_log_table";
	// public static String SESSION_UDM_LOG = "cur_user_log";
	// public static String SESSION_UDM_IS_LOG = "cur_user_log";
	// public static String SESSION_VAR_UDM_LOG = "true";

	private VerticalLayout cParent;
	private VerticalLayout cMenu;
	private HorizontalLayout cContent;
	public FeesAndCommModule apm;
	VerticalLayout cC;
	public static ArrayList<String> prevSearchFrag = new ArrayList<>();

	public WorkSpaceManageFeesAndComm() {
		setCoreUI();
		UI.getCurrent()
				.getPage()
				.addUriFragmentChangedListener(
						new Page.UriFragmentChangedListener() {

							/**
					 * 
					 */
							private static final long serialVersionUID = 5491313193057079077L;

							@Override
							public void uriFragmentChanged(
									UriFragmentChangedEvent event) {
								modifier(event.getUriFragment());

							}
						});
	}

	private void setCoreUI() {
		cParent = new VerticalLayout();
		cParent.setSizeFull();

		cC = new VerticalLayout();
		cC.setSizeUndefined();

		cContent = new HorizontalLayout();
		cContent.setSizeFull();
		cContent.setStyleName("c_content");

		apm = new FeesAndCommModule();
		HorizontalLayout pf = apm.getFeesContainer(FeesAndCommModule.FEES);
		cContent.addComponent(pf);
		cContent.setComponentAlignment(pf, Alignment.TOP_LEFT);

		ManageFeesAndCommModule mpm = new ManageFeesAndCommModule();
		cMenu = mpm.getMenu(false, false, false);
		cC.addComponent(cMenu);
		cMenu.setSizeUndefined();
		cC.setComponentAlignment(cMenu, Alignment.TOP_LEFT);

	}

	public VerticalLayout getWorkSpaceAccountProfile(Button back) {
		cC.addComponent(back);
		cC.setComponentAlignment(back, Alignment.TOP_RIGHT);
		cC.addComponent(cContent);
		cC.setExpandRatio(cContent, 1.0f);
		cParent.addComponent(cC);
		cParent.setComponentAlignment(cC, Alignment.TOP_LEFT);
		return cParent;

	}

	private void modifier(String frag) {

		String action = null;
		if (frag.indexOf('?') == -1) {
			action = "Fees";
		} else {
			String param = frag.substring(frag.indexOf('?') + 1);
			if (param == null) {
				action = "Fees";
			} else if (param.trim().isEmpty()) {
				action = "Fees";
			} else {
				String aParam[] = param.split("&");
				String arr[] = aParam[0].split("=");

				if (arr.length < 2)
					action = "Fees";
				else
					action = aParam[0].split("=")[1];
			}

		}

		if (!prevSearchFrag.contains(frag)) {
			apm.apmModifier(action, cContent);
			prevSearchFrag.add(frag);
		}

	}

}
