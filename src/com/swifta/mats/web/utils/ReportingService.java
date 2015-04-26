package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.HashMap;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.sub.mats.reporting.DataServiceFault;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getprofiles;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getprofiletypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profile;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profiles;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.ProfilesE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profiletype;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Profiletypes;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.ProfiletypesE;

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

}
