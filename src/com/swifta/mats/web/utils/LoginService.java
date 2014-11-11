package com.swifta.mats.web.utils;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Authenticate;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AuthenticateE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AuthenticateResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AuthenticateResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Authenticationresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.StatusCode;

public class LoginService {
	private ProvisioningStub provisioningStub;
	private static final Logger logger = Logger.getLogger(LoginService.class);

	public boolean authenticateUser(String username, String password) {
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
			provisioningStub = new ProvisioningStub();
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
					StatusCode statusC = formattedResponse.getStatuscode();
					if (statusC != null) {
						statusCode = statusC.getValue();
						logger.info("---------------Status code is not null");
					} else {
						logger.info("---------------Status code is null");
					}
					responseMessage = formattedResponse.getResponsemessage();
				} else {
					logger.info("---------------Authenticationresponse is nul");
				}
			} else {
				logger.info("---------------AuthenticateResponse is null");
			}
		} else {
			logger.info("---------------AuthenticateResponseE is null");
		}
		if (statusCode.equalsIgnoreCase("true")
				|| responseMessage.equalsIgnoreCase("true")) {
			status = true;
		} else {
			status = false;
		}
		return status;
	}

}
