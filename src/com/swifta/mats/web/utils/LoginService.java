package com.swifta.mats.web.utils;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationrequest;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Authenticate;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AuthenticateE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AuthenticateResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AuthenticateResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Authenticationresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Credentials;

public class LoginService {
	private ProvisioningStub provisioningStub;
	private static final Logger logger = Logger.getLogger(LoginService.class);

	public String authenticateUser(String username, String password) {
		String statusCode = "", responseMessage = "";
		boolean status = false;

		AuthenticateE authenticate = new AuthenticateE();

		Authenticate authenticateParam = new Authenticate();
		logger.info("---------------Instantiate authenticate class");
		authenticateParam.setPassword(password);
		authenticateParam.setResourceid(username);
		authenticate.setAuthenticate(authenticateParam);
		logger.info("---------------After setting authenticate parameters");
		AuthenticateResponseE authenticateResponse = null;
		try {
			provisioningStub = new ProvisioningStub(
					"http://54.164.96.105:8283/services/Provisionservice/");
			logger.info("---------------Calling the authenticate method in the provisioning class");
			authenticateResponse = provisioningStub.authenticate(authenticate);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			logger.info("---------------Exception occured while authenticating");
			e.printStackTrace();
			status = false;
		}
		if (authenticateResponse != null) {
			AuthenticateResponse response = authenticateResponse
					.getAuthenticateResponse();
			if (response != null) {
				Authenticationresponse formattedResponse = response
						.get_return();
				if (formattedResponse != null) {

					responseMessage = formattedResponse.getResponsemessage()
							+ " Am I null?";
				} else {
					logger.info("---------------Authenticationresponse is nul");
				}
			} else {
				logger.info("---------------AuthenticateResponse is null");
			}
		} else {
			logger.info("---------------AuthenticateResponseE is null");
		}

		return responseMessage;
	}

	public boolean activateUser(String bankdomainid, String currency,
			String IDnumber, String resourceid, String SecurityAns,
			String firstPin, String confirmPin) throws RemoteException {
		String statusMessage;
		provisioningStub = new ProvisioningStub(
				"http://54.164.96.105:8283/services/ProvisioningService/");
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

}
