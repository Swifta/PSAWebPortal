package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.HashMap;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.sub.mats.reporting.DataServiceFault;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getprofiles;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getprofiletypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getthresholdtypes;
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
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Transactiontype;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Transactiontypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.TransactiontypesE;

public class ReportingService {
	private final MatsreportingserviceStub rservice;
	final static String esbendpoint = MatsWebPortalUI.conf.ESB;

	public ReportingService() throws AxisFault {
		rservice = new MatsreportingserviceStub(esbendpoint);
	}

	public HashMap<String, String> getProfiles() throws RemoteException,
			DataServiceFault {
		Getprofiles getProfiles = new Getprofiles();
		ProfilesE profileE = rservice.getprofiles(getProfiles);
		Profiles profiles = profileE.getProfiles();
		Profile[] arrProfiles = profiles.getProfile();
		HashMap<String, String> hmProfiles = new HashMap<>();
		for (Profile profile : arrProfiles)
			hmProfiles.put(profile.getProfilename(), profile.getProfileid());
		return hmProfiles;

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

	public void getThresholdTypes(String ptid) throws RemoteException,
			DataServiceFault {
		Getthresholdtypes getthresholdtypes = new Getthresholdtypes();
		getthresholdtypes.setProfiletypeid("3");
		ThresholdtypesE thresholdtypesE = rservice
				.getthresholdtypes(getthresholdtypes);
		// thresholdtypesE.set
		// getthresholdtypes.setProfiletypeid("3");

		Thresholdtypes thtypes = thresholdtypesE.getThresholdtypes();
		Thresholdtype[] thresholdTypes = thtypes.getThresholdtype();
		if (thresholdTypes == null) {
			System.out.println("NO THRESHOLDS");
			return;
		}

		for (Thresholdtype tty : thresholdTypes)
			System.out.println(tty.getThresholdtype() + ":----:"
					+ tty.getThresholdtypeid());

	}

	public void getTransactionTypes() throws RemoteException, DataServiceFault {
		Gettransactiontypes gettransactiontypes = new Gettransactiontypes();

		TransactiontypesE transactiontypesE = rservice
				.gettransactiontypes(gettransactiontypes);

		Transactiontypes transactiontypes = transactiontypesE
				.getTransactiontypes();

		Transactiontype[] transactiontype = transactiontypes
				.getTransactiontype();
		if (transactiontype == null) {
			System.out.println("No Transactions.");
			return;
		}

		for (Transactiontype ttype : transactiontype)
			System.out.println(ttype.getTransactiontype() + " : "
					+ ttype.getTransactiontypeid());

	}

}
