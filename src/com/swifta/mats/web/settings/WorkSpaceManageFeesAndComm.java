package com.swifta.mats.web.settings;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class WorkSpaceManageFeesAndComm {
	public static final String SESSION_WSMP = "manage_commission_and_fees";
	public static final String SESSION_WSMP_CUR_ACTION = "manage_profile_cur_action";
	public static String SESSION_VAR_WSMP_PERSONAL = "personal";
	public static String SESSION_VAR_WSMP_AUTH = "cur_user_auth";
	public static String SESSION_VAR_WSMP_LOG = "log";
	public static String SESSION_VAR_WSMP_ACT_LOG = "act_log";
	public static String SESSION_VAR_WSMP_ACC_LOG = "acc_log";

	public static String SESSION_UDM_TABLE_LOG = "cur_user_log_table";
	public static String SESSION_UDM_LOG = "cur_user_log";
	public static String SESSION_UDM_IS_LOG = "cur_user_log";
	public static String SESSION_VAR_UDM_LOG = "true";

	private VerticalLayout cParent;
	private VerticalLayout cMenu;
	private HorizontalLayout cContent;
	public FeesAndCommModule apm;

	public WorkSpaceManageFeesAndComm() {
		setCoreUI();
	}

	private void setCoreUI() {
		cParent = new VerticalLayout();
		cParent.setSizeFull();

		VerticalLayout cC = new VerticalLayout();
		cC.setSizeUndefined();

		cContent = new HorizontalLayout();
		cContent.setSizeFull();
		cContent.setStyleName("c_content");

		apm = new FeesAndCommModule();
		HorizontalLayout pf = apm.getFeesContainer();
		cContent.addComponent(pf);
		cContent.setComponentAlignment(pf, Alignment.TOP_LEFT);

		ManageFeesAndCommModule mpm = new ManageFeesAndCommModule();
		cMenu = mpm.getMenu(false, false, false);
		cC.addComponent(cMenu);
		cMenu.setSizeUndefined();
		cC.setComponentAlignment(cMenu, Alignment.TOP_LEFT);

		cC.addComponent(cContent);
		cC.setExpandRatio(cContent, 1.0f);
		cParent.addComponent(cC);
		cParent.setComponentAlignment(cC, Alignment.TOP_CENTER);

	}

	public VerticalLayout getWorkSpaceAccountProfile() {
		return cParent;

	}

	public void wsmpModifier() {
		String strCurSession = (String) UI.getCurrent().getSession()
				.getAttribute(SESSION_WSMP_CUR_ACTION);
		apm.apmModifier(strCurSession, cContent);

	}

}
