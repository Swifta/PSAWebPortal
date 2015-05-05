package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.axis2.addressing.EndpointReference;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationrequest;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Credentials;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Kyc;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Webauthenticate;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.WebauthenticateE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.WebauthenticateResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.WebauthenticateResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Webauthenticationrequestresponse;

public class LoginService {
	private ProvisioningStub provisioningStub;
	private static String[] permissions;
	private static HashMap<String, String> hmUDetails;
	private static final Logger logger = Logger.getLogger(LoginService.class
			.getName());

	static ProvisioningStub matsStub;

	// static String esbendpoint =
	// "http://127.0.0.1:8280/services/Provisionservice";

	final static String esbendpoint = MatsWebPortalUI.conf.ESB;

	org.apache.axis2.client.ServiceClient _serviceClient = null;

	private static EndpointReference targetEPR = new EndpointReference(
			esbendpoint);

	public static String webauthenticate(String username, String password)
			throws Exception {
		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);

		WebauthenticateE webauthenticateE = new WebauthenticateE();
		Webauthenticate webauthenticate = new Webauthenticate();

		webauthenticate.setResourceid(username);
		webauthenticate.setPassword(password);

		webauthenticateE.setWebauthenticate(webauthenticate);

		WebauthenticateResponseE response = matsStub
				.webauthenticate(webauthenticateE);

		if (response != null) {
			WebauthenticateResponse response2 = response
					.getWebauthenticateResponse();

			if (response2 != null) {
				Webauthenticationrequestresponse response3 = response2
						.get_return();
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();
					permissions = response3.getPermission();
					setUserDetails(response3);

				}

				else {
					statusMessage = "Response3 is empty";
				}

			} else {
				statusMessage = "authentication request Response is empty";
			}

		} else {
			statusMessage = "authentication request Response is empty";
		}

		return statusMessage;

	}

	public boolean activateUser(String bankdomainid, String currency,
			String IDnumber, String resourceid, String SecurityAns,
			String firstPin, String confirmPin) throws RemoteException {
		String statusMessage;
		provisioningStub = new ProvisioningStub(
				"http://54.164.96.105:8283/services/Provisionservice");
		Credentials cre = new Credentials();
		cre.setFirstpin(firstPin);
		cre.setConfirmpin(confirmPin);

		ActivationrequestE actE = new ActivationrequestE();
		Activationrequest act = new Activationrequest();
		actE.setActivationrequest(act);
		act.setBankdomainid(bankdomainid);
		act.setCredential(cre);
		act.setCurrency(currency);
		act.setIdentificationno(IDnumber);
		act.setResourceid(resourceid);
		act.setSecurityquestionanswer(SecurityAns);
		ActivationrequestResponseE response = provisioningStub
				.activationrequest(actE);

		if (response != null) {
			ActivationrequestResponse response2 = response
					.getActivationrequestResponse();
			if (response2 != null) {
				Activationresponse response3 = response2.get_return();
				statusMessage = response3.getResponsemessage();

			} else {
				statusMessage = "Activation Response is empty";
			}
		} else {
			statusMessage = "Activation Response is empty";
		}
		boolean status = false;
		if (statusMessage.equalsIgnoreCase("true")) {
			status = true;
		}
		return status;
		// return statusMessage;
	}

	public static String[] getUserPermissions() {
		return permissions;
	}

	public static Map<String, String> getUserDetails() {
		if (hmUDetails == null)
			return Collections.emptyMap();
		return hmUDetails;

	}

	private static void setUserDetails(Webauthenticationrequestresponse response) {
		hmUDetails = new HashMap<>();
		Kyc kUD = response.getAccountholderdetail();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sdob = "Not Specified.";
		if (kUD == null) {
			System.out
					.println("KKKKKKKKKKKKKKKK<>KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");

			return;
		}

		sdob = kUD.getDateofbirth();

		try {
			if (sdob != null) {
				Date dob = sdf.parse(kUD.getDateofbirth());
				Calendar cal = Calendar.getInstance();
				cal.setTime(dob);
				sdob = cal.get(Calendar.DATE) + "/"
						+ (cal.get(Calendar.MONTH) + 1) + "/"
						+ cal.get(Calendar.YEAR);
			} else {
				sdob = "Not Specified.";
			}
		} catch (Exception e) {

		}

		hmUDetails.put("Status", kUD.getAccountholderstatus());
		hmUDetails.put("Country", kUD.getCountryname());
		hmUDetails.put("Date of Birth", sdob);
		hmUDetails.put("Email", kUD.getEmail());
		hmUDetails.put("First Name", kUD.getFirstname());
		hmUDetails.put("Gender", kUD.getGender());
		hmUDetails.put("ID No.", kUD.getIdentificationno());
		hmUDetails.put("ID Type", kUD.getIdentificationtype());
		hmUDetails.put("Last Name", kUD.getLastname());
		hmUDetails.put("Local Government", kUD.getLganame());
		hmUDetails.put("MSISDN", kUD.getMsisdn());
		hmUDetails.put("Occupation", kUD.getOccupation());
		hmUDetails.put("Profile Type", kUD.getProfile());
		hmUDetails.put("State", kUD.getState());
		hmUDetails.put("Street", kUD.getStreet());
		hmUDetails.put("Username", kUD.getUsername());

		/*
		 * hm.put("Username", rs.getString("un")); hm.put("Gender",
		 * rs.getString("gender")); hm.put("Occupation",
		 * rs.getString("occupation")); hm.put("Employer",
		 * rs.getString("employer")); hm.put("Date of Birth", sdob);
		 * hm.put("Status", rs.getString("status")); hm.put("First Name",
		 * rs.getString("fn")); hm.put("Profile Type", rs.getString("prof"));
		 * hm.put("Last Name", rs.getString("ln")); hm.put("ID Type",
		 * rs.getString("idtype")); hm.put("ID No.", rs.getString("id"));
		 * hm.put("Street", rs.getString("street")); hm.put("Issuer",
		 * rs.getString("issuer")); hm.put("Issue Date", sdoi);
		 * hm.put("Expiry Date", sdoe); hm.put("P-Mobile Phone No.",
		 * rs.getString("primarymobilenumber")); hm.put("P-Alt. Phone No.",
		 * rs.getString("primaryphonenumber")); hm.put("S-Mobile Phone No.",
		 * rs.getString("secondarymobilenumber")); hm.put("S-Alt. Phone No.",
		 * rs.getString("secondaryphonenumber")); hm.put("Country",
		 * rs.getString("country")); hm.put("State", rs.getString("state"));
		 * hm.put("Local Government", rs.getString("lg")); hm.put("City",
		 * rs.getString("city")); hm.put("Province", rs.getString("province"));
		 * hm.put("Postal Code", rs.getString("postalcode")); hm.put("MSISDN",
		 * rs.getString("msisdn")); hm.put("Email", rs.getString("email"));
		 * hm.put("Full Name", hm.get("First Name") + " " +
		 * hm.get("Last Name"));
		 */

	}
}