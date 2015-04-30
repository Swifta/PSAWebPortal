package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
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
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Webauthenticate;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.WebauthenticateE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.WebauthenticateResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.WebauthenticateResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Webauthenticationrequestresponse;

public class LoginService {
	private ProvisioningStub provisioningStub;
	private static String[] permissions;
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

}