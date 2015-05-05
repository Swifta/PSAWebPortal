package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.sub.mats.reporting.DataServiceFault;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getministatementreport;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getprofiles;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getprofiletypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getthresholdtypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Gettransactionreport;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Gettransactionreportresponse;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Gettransactionreportresponses;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.GettransactionreportresponsesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Gettransactiontypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profile;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profiles;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.ProfilesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profiletype;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profiletypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.ProfiletypesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Thresholdtype;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Thresholdtypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.ThresholdtypesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.TransactionResponsesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Transactiontype;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Transactiontypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.TransactiontypesE;

public class ReportingService {
	private final MatsreportingserviceStub rservice;
	final static String esbendpoint = MatsWebPortalUI.conf.ESB;

	public ReportingService() throws AxisFault {
		rservice = new MatsreportingserviceStub(esbendpoint);
	}

	public Profile[] getProfiles() throws RemoteException, DataServiceFault {
		Getprofiles getProfiles = new Getprofiles();
		ProfilesE profileE = rservice.getprofiles(getProfiles);
		Profiles profiles = profileE.getProfiles();
		Profile[] arrProfiles = profiles.getProfile();
		if (arrProfiles == null)
			return new Profile[0];

		return arrProfiles;

	}

	public HashMap<String, String> getProfileTypes() throws RemoteException,
			DataServiceFault {
		Getprofiletypes getProfiles = new Getprofiletypes();
		ProfiletypesE profileE = rservice.getprofiletypes(getProfiles);

		Profiletypes profiles = profileE.getProfiletypes();
		Profiletype[] arrProfiles = profiles.getProfiletype();
		HashMap<String, String> hmProfiles = new HashMap<>();

		for (Profiletype profile : arrProfiles)
			hmProfiles
					.put(profile.getProfiletypeid(), profile.getProfilename());
		return hmProfiles;

	}

	public Map<String, String> getThresholdTypes(String ptid)
			throws RemoteException, DataServiceFault {
		Getthresholdtypes getthresholdtypes = new Getthresholdtypes();
		getthresholdtypes.setProfiletypeid(ptid);
		ThresholdtypesE thresholdtypesE = rservice
				.getthresholdtypes(getthresholdtypes);
		// thresholdtypesE.set
		// getthresholdtypes.setProfiletypeid("3");

		Thresholdtypes thtypes = thresholdtypesE.getThresholdtypes();
		Thresholdtype[] thresholdTypes = thtypes.getThresholdtype();
		if (thresholdTypes == null)
			return Collections.emptyMap();
		Map<String, String> hm = new HashMap<>();

		for (Thresholdtype tty : thresholdTypes)
			hm.put(tty.getThresholdtypeid(), tty.getThresholdtype());
		return hm;

	}

	public Map<String, String> getTransactionTypes() throws RemoteException,
			DataServiceFault {
		Gettransactiontypes gettransactiontypes = new Gettransactiontypes();

		TransactiontypesE transactiontypesE = rservice
				.gettransactiontypes(gettransactiontypes);

		Transactiontypes transactiontypes = transactiontypesE
				.getTransactiontypes();

		Transactiontype[] transactiontype = transactiontypes
				.getTransactiontype();
		if (transactiontype == null)
			return Collections.emptyMap();
		Map<String, String> hm = new HashMap<>();

		for (Transactiontype tty : transactiontype)
			hm.put(tty.getTransactiontypeid(), tty.getTransactiontype());
		return hm;

	}

	public Gettransactionreportresponse[] getTransactionReport()
			throws RemoteException, DataServiceFault {
		Gettransactionreport gtrp = new Gettransactionreport();
		GettransactionreportresponsesE gtrrE = rservice
				.gettransactionreport(gtrp);
		Gettransactionreportresponses gtrr = gtrrE
				.getGettransactionreportresponses();
		// String[] s = new String[]{};
		Gettransactionreportresponse[] trr = gtrr
				.getGettransactionreportresponse();
		if (trr == null)
			return new Gettransactionreportresponse[] {};
		return trr;

	}

	public Gettransactionreportresponse[] getTransactionSummaryReport()
			throws RemoteException, DataServiceFault {
		Gettransactionreport gtrp = new Gettransactionreport();
		GettransactionreportresponsesE gtrrE = rservice
				.gettransactionreport(gtrp);
		Gettransactionreportresponses gtrr = gtrrE
				.getGettransactionreportresponses();
		// String[] s = new String[]{};
		Gettransactionreportresponse[] trr = gtrr
				.getGettransactionreportresponse();
		if (trr == null)
			return new Gettransactionreportresponse[] {};
		return trr;

	}

	public Gettransactionreportresponse[] getFloatManagementReport()
			throws RemoteException, DataServiceFault {
		Gettransactionreport gtrp = new Gettransactionreport();
		GettransactionreportresponsesE gtrrE = rservice
				.gettransactionreport(gtrp);

		Gettransactionreportresponses gtrr = gtrrE
				.getGettransactionreportresponses();
		// String[] s = new String[]{};
		Gettransactionreportresponse[] trr = gtrr
				.getGettransactionreportresponse();
		if (trr == null)
			return new Gettransactionreportresponse[] {};
		return trr;

	}

	public Gettransactionreportresponse[] getFeesAndCommissionReport()
			throws RemoteException, DataServiceFault {
		Gettransactionreport gtrp = new Gettransactionreport();
		GettransactionreportresponsesE gtrrE = rservice
				.gettransactionreport(gtrp);

		Gettransactionreportresponses gtrr = gtrrE
				.getGettransactionreportresponses();
		// String[] s = new String[]{};
		Gettransactionreportresponse[] trr = gtrr
				.getGettransactionreportresponse();
		if (trr == null)
			return new Gettransactionreportresponse[] {};
		return trr;

	}

	public Gettransactionreportresponse[] getMiniStatementReport()
			throws RemoteException, DataServiceFault {
		Getministatementreport grp = new Getministatementreport();
		TransactionResponsesE trE = rservice.getministatementreport(grp);

		return null;

	}

}
