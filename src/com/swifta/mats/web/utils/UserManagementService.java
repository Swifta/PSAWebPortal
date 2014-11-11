package com.swifta.mats.web.utils;

import java.rmi.RemoteException;

import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Accountholderdetails;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Registration;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Registrationrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Securityquestions;

public class UserManagementService {
	ProvisioningStub matsStub;

	public String registerProfile(Accountholderdetails accountholderdetails,
			String bankAccount, int bankCodeid, String bankdomainNameid,
			String clearingNumber, String currencyid, String email,
			String msisdn, int profileid, String securityQuest,
			String securityAns, String termscondition, String username)
			throws RemoteException {
		matsStub = new ProvisioningStub();
		Registration registration = new Registration();

		String statusMessage = "";

		registration.setAccountholderdetails(accountholderdetails);
		registration.setBankaccount(bankAccount);
		registration.setBankcodeid(bankCodeid);
		registration.setBankdomainnameid(bankdomainNameid);
		registration.setClearingnumber(clearingNumber);
		registration.setCurrencyid(currencyid);
		registration.setEmail(email);
		registration.setMsisdn(msisdn);
		registration.setProfileid(profileid);

		Securityquestions[] sec = new Securityquestions[1];
		Securityquestions securityQuestion = new Securityquestions();
		securityQuestion.setAnswer(securityAns);
		securityQuestion.setQuestion(securityQuest);
		sec[0] = securityQuestion;

		registration.setSecurityquestions(sec);
		registration.setTermscondition(termscondition);
		registration.setUsername(username);

		RegistrationE registrationE = new RegistrationE();
		registrationE.setRegistration(registration);
		RegistrationResponseE response = matsStub.registration(registrationE);
		if (response != null) {
			RegistrationResponse response2 = response.getRegistrationResponse();
			if (response2 != null) {
				Registrationrequestresponse response3 = response2.get_return();
				statusMessage = response3.getResponsemessage();

			} else {
				statusMessage = "Response is empty";
			}
		} else {
			statusMessage = "Response is empty";
		}

		return statusMessage;

	}

}
