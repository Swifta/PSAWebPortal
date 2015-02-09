package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationrequest;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Credentials;

public class LoginService {
	private ProvisioningStub provisioningStub;
	private static final Logger logger = Logger.getLogger(LoginService.class
			.getName());

	// static String esbendpoint =
	// "http://127.0.0.1:8280/services/Provisionservice";

	final static String esbendpoint = MatsWebPortalUI.conf.ESB;

	org.apache.axis2.client.ServiceClient _serviceClient = null;

	private static EndpointReference targetEPR = new EndpointReference(
			esbendpoint);

	public Boolean authenticateUser(String username, String password)
			throws AxisFault {

		boolean status = false;

		ServiceClient sender = null;

		OMFactory fac = OMAbstractFactory.getOMFactory();

		OMNamespace omNs = fac.createOMNamespace(
				"http://service.ws.um.carbon.wso2.org", "ser");

		OMElement authenticate = fac.createOMElement("authenticate", omNs);

		OMElement userNameElement = fac.createOMElement("userName", null);
		userNameElement.addChild(fac.createOMText(userNameElement, username));

		OMElement credential1Element = fac.createOMElement("credential", null);
		credential1Element.addChild(fac.createOMText(credential1Element,
				password));

		authenticate.addChild(userNameElement);
		authenticate.addChild(credential1Element);

		Options options = new Options();
		options.setTo(targetEPR);
		options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		options.setAction("urn:authenticate");

		options.setProperty(HTTPConstants.CHUNKED, Constants.VALUE_FALSE);

		sender = new ServiceClient();
		sender.setOptions(options);
		OMElement result = sender.sendReceive(authenticate);

		OMElement firstelement = result.getFirstElement();

		if (firstelement.getText().equals("true")) {
			status = true;
		}

		return status;

		// String statusCode = "", responseMessage = "";

		//
		// AuthenticateE authenticate = new AuthenticateE();
		//
		// Authenticate authenticateParam = new Authenticate();
		// logger.info("---------------Instantiate authenticate class");
		// authenticateParam.setPassword(password);
		// authenticateParam.setResourceid(username);
		// authenticate.setAuthenticate(authenticateParam);
		// logger.info("---------------After setting authenticate parameters");
		// AuthenticateResponseE authenticateResponse = null;
		// try {
		// logger.info("---------------After initiating the authenticate parameters");
		// provisioningStub = new ProvisioningStub(esbendpoint);
		// logger.info("---------------Calling the authenticate method in the provisioning class");
		// authenticateResponse = provisioningStub.authenticate(authenticate);
		// logger.info("---------------After Calling the authenticate method in the provisioning class");
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// logger.info("---------------Exception occured while authenticating");
		// e.printStackTrace();
		// status = false;
		// }
		// if (authenticateResponse != null) {
		// logger.info("---------------AuthenticationresponseE is not null");
		// AuthenticateResponse response = authenticateResponse
		// .getAuthenticateResponse();
		// if (response != null) {
		// logger.info("---------------Authenticationresponse is not null");
		// Authenticationresponse formattedResponse = response
		// .get_return();
		// if (formattedResponse != null) {
		// logger.info("---------------formatted Response is not null "
		// + formattedResponse.getResponseMessage());
		//
		// responseMessage = formattedResponse.getResponsemessage();
		// logger.info("---------------Response is..."
		// + responseMessage);
		// } else {
		// logger.info("---------------Authenticationresponse is null");
		// }
		// } else {
		// logger.info("---------------AuthenticateResponse is null");
		// }
		// } else {
		// logger.info("---------------AuthenticateResponseE is null");
		// }
		// if (responseMessage.equalsIgnoreCase("true")) {
		// status = true;
		// }

		// return status;
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

}