package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.sub.mats.reporting.DataServiceFault;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Commissionfee;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Existingservicefee;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getavailableservicefee;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getcommissionbysp;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getexistingthresholdsettings;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getfeesandcommissionreport;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getfeesandcommissionreportresponse;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getfeesandcommissionreportresponses;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.GetfeesandcommissionreportresponsesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getfeesandcommissionsummaryreport;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getfeesandcommissionsummaryreportresponse;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getfeesandcommissionsummaryreportresponses;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.GetfeesandcommissionsummaryreportresponsesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getfloatmanagementfloatresponse;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getfloatmanagementreport;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getministatementreport;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getprofiles;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getprofiletypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getservicefeebysp;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getserviceproviders;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getthresholdsetting;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getthresholdtypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Gettransactionreport;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Gettransactionreportresponse;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Gettransactionreportresponses;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.GettransactionreportresponsesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Gettransactionsummaryreport;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Gettransactionsummaryreportresponse;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Gettransactiontypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profile;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profiles;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.ProfilesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profiletype;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profiletypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.ProfiletypesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Servicefee;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Serviceprovider;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Thresholdtype;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Thresholdtypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.ThresholdtypesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.TransactionResponse;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.TransactionResponses;
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

		// System.out.println(ptid);

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

		for (Transactiontype tty : transactiontype) {
			hm.put(tty.getTransactiontype(), tty.getTransactiontypeid());
			System.err.println("FROM BACKEND TT: " + tty.getTransactiontype());
			System.err.println("FROM BACKEND TTID "
					+ tty.getTransactiontypeid());
		}

		return hm;

	}

	public Gettransactionreportresponse[] getTransactionReport(String d1,
			String d2) throws RemoteException, DataServiceFault {
		Gettransactionreport gtrp = new Gettransactionreport();
		gtrp.setFromdate(d1);
		gtrp.setTodate(d2);
		GettransactionreportresponsesE gtrrE = rservice
				.gettransactionreport(gtrp);

		Gettransactionreportresponses gtrr = gtrrE
				.getGettransactionreportresponses();

		Gettransactionreportresponse[] trr = gtrr
				.getGettransactionreportresponse();

		if (trr == null)
			return new Gettransactionreportresponse[] {};
		return trr;

	}

	public Gettransactionsummaryreportresponse[] getTransactionSummaryReport(
			String sd, String ed) throws RemoteException, DataServiceFault {
		Gettransactionsummaryreport gtrp = new Gettransactionsummaryreport();
		gtrp.setFromdate(sd);
		gtrp.setTodate(ed);
		Gettransactionsummaryreportresponse[] trr = rservice
				.gettransactionsummaryreport(gtrp)
				.getGettransactionsummaryreportresponses()
				.getGettransactionsummaryreportresponse();

		if (trr == null)
			return new Gettransactionsummaryreportresponse[] {};
		return trr;

	}

	public Getfloatmanagementfloatresponse[] getFloatManagementReport(
			String sd, String ed) throws RemoteException, DataServiceFault {

		Getfloatmanagementreport gfmrp = new Getfloatmanagementreport();
		gfmrp.setFromdate(sd);
		gfmrp.setTodate(ed);

		Getfloatmanagementfloatresponse[] fmr = rservice
				.getfloatmanagementreport(gfmrp)
				.getGetfloatmanagementfloatresponses()
				.getGetfloatmanagementfloatresponse();

		if (fmr == null)
			return new Getfloatmanagementfloatresponse[] {};
		return fmr;

	}

	public Getfeesandcommissionreportresponse[] getFeesAndCommissionReport(
			String d1, String d2) throws RemoteException, DataServiceFault {
		Getfeesandcommissionreport gfcrp = new Getfeesandcommissionreport();
		gfcrp.setFromdate(d1);
		gfcrp.setTodate(d2);

		GetfeesandcommissionreportresponsesE gfcrrE = rservice
				.getfeesandcommissionreport(gfcrp);

		Getfeesandcommissionreportresponses rps = gfcrrE
				.getGetfeesandcommissionreportresponses();
		Getfeesandcommissionreportresponse[] rp = rps
				.getGetfeesandcommissionreportresponse();
		if (rp == null)
			return new Getfeesandcommissionreportresponse[] {};

		return rp;

	}

	public Getfeesandcommissionsummaryreportresponse[] getFeesAndCommissionSummaryReport(
			String d1, String d2) throws RemoteException, DataServiceFault {
		Getfeesandcommissionsummaryreport gfcrp = new Getfeesandcommissionsummaryreport();
		gfcrp.setFromdate(d1);
		gfcrp.setTodate(d2);
		GetfeesandcommissionsummaryreportresponsesE gfcrrE = rservice
				.getfeesandcommissionsummaryreport(gfcrp);

		Getfeesandcommissionsummaryreportresponses rps = gfcrrE
				.getGetfeesandcommissionsummaryreportresponses();
		Getfeesandcommissionsummaryreportresponse[] rp = rps
				.getGetfeesandcommissionsummaryreportresponse();
		if (rp == null)
			return new Getfeesandcommissionsummaryreportresponse[] {};
		return rp;

	}

	public TransactionResponse[] getMiniStatementReport()
			throws RemoteException, DataServiceFault {
		Getministatementreport grp = new Getministatementreport();

		TransactionResponsesE trE = rservice.getministatementreport(grp);
		TransactionResponses trs = trE.getTransactionResponses();
		TransactionResponse[] tr = trs.getTransactionResponse();
		if (tr == null)
			return new TransactionResponse[] {};
		return tr;

	}

	public Map<String, String> getServiceProviders() throws RemoteException,
			DataServiceFault {
		Getserviceproviders getserviceproviders = new Getserviceproviders();

		Serviceprovider[] arrServiceProvider = rservice
				.getserviceproviders(getserviceproviders).getServiceproviders()
				.getServiceprovider();
		if (arrServiceProvider == null)
			return Collections.emptyMap();
		Map<String, String> hm = new HashMap<>();
		for (Serviceprovider sp : arrServiceProvider)
			hm.put(sp.getUsername(), sp.getAccountholderid());
		return hm;
	}

	public Servicefee[] getFeesBySP(String user, Integer spid)
			throws RemoteException, DataServiceFault {
		Getservicefeebysp getservicefeebysp = new Getservicefeebysp();
		getservicefeebysp.setLoggedInUser(user);
		getservicefeebysp.setSpaccountholderid(spid);
		Servicefee[] arrServicefee = rservice
				.getservicefeebysp(getservicefeebysp).getServicefees()
				.getServicefee();

		if (arrServicefee == null)
			return new Servicefee[] {};

		return arrServicefee;
	}

	public Map<String, String> getAvailableFees(String user)
			throws RemoteException, DataServiceFault {
		Getavailableservicefee getavailableservicefee = new Getavailableservicefee();
		getavailableservicefee.setLoggedInUser(user);

		Existingservicefee[] arrEf = rservice
				.getavailableservicefee(getavailableservicefee)
				.getExistingservicefees().getExistingservicefee();
		// arrEf[0].get

		if (arrEf == null)
			return Collections.emptyMap();
		Map<String, String> hm = new HashMap<>();
		for (Existingservicefee esf : arrEf)
			hm.put(esf.getTransactionfee(),
					String.valueOf(esf.getServicefeepropertiesid()));
		return hm;
	}

	public Commissionfee[] getCommissionBySp(String user, Integer spid)
			throws RemoteException, DataServiceFault {
		Getcommissionbysp getcommissionbysp = new Getcommissionbysp();

		getcommissionbysp.setLoggedInUser(user);
		getcommissionbysp.setSpaccountholderid(spid);
		Commissionfee[] cf = rservice.getcommissionbysp(getcommissionbysp)
				.getCommissionfees().getCommissionfee();

		if (cf == null)
			return new Commissionfee[] {};
		return cf;

	}

	public Map<String, ArrayList<HashMap<String, String>>> getExistingThresholds(
			String pid) throws RemoteException, DataServiceFault {
		Getexistingthresholdsettings getthresholdsettings = new Getexistingthresholdsettings();
		getthresholdsettings.setProfileid(pid);
		Getthresholdsetting[] ts = rservice
				.getexistingthresholdsettings(getthresholdsettings)
				.getGetthresholdsettings().getGetthresholdsetting();
		if (ts == null)
			return Collections.emptyMap();

		Map<String, ArrayList<HashMap<String, String>>> hmTT = new HashMap<>();

		int x = 0;

		for (Getthresholdsetting t : ts) {
			x++;
			String ttid = t.getTransactiontypeid();
			if (!hmTT.containsKey(ttid)) {
				ArrayList<HashMap<String, String>> arr = new ArrayList<>();
				// HashMap<String, HashMap<String, String>> hmTT = new
				// HashMap<>();
				HashMap<String, String> hmThreshold = new HashMap<>();
				hmThreshold.put("threshold_type_id", t.getThresholdtypeid());
				hmThreshold.put("limit", t.getValue());
				hmThreshold.put("profile_type_id", t.getProfiletypeid());

				arr.add(hmThreshold);
				hmTT.put(ttid, arr);

				continue;
			}

			HashMap<String, String> hmThreshold = new HashMap<>();
			hmThreshold.put("threshold_type_id", t.getThresholdtypeid());
			hmThreshold.put("profile_type_id", t.getProfiletypeid());
			hmThreshold.put("limit", t.getValue());
			hmTT.get(ttid).add(hmThreshold);

		}

		System.out.println("Threshold Count: " + x);

		return hmTT;

	}
}
