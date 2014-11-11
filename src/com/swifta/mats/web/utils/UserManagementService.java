package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis2.AxisFault;

import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Accountholderdetails;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activation;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Address;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Identification;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Operationresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.PrimaryContactInfo;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Registration;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Registrationrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SecondaryContactInfo;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Securityquestions;

public class UserManagementService {
	ProvisioningStub provision;

	@SuppressWarnings("null")
	public Operationresponse CreateProfile(Address address, int countryID,
			Date DOB, String employer, String firstname,
			Identification identification, int gender, int lang,
			String lastname, int lga, String middlename, String occupation,
			String prefix, SecondaryContactInfo Scontact,
			PrimaryContactInfo Pcontact, int stateID, String suffix,
			String bankdomainname, int bankcodeID, String bankAccount,
			String clearingNumber, String currency, String email,
			String msisdn, int profileID, Securityquestions[] secQuestion,
			String TermsCondition, String username) throws RemoteException,
			AxisFault {
		provision = new ProvisioningStub();

		Calendar cal = utils.DateToCalendar(DOB);

		RegistrationE registration36 = new RegistrationE();
		Registration registrationDetails = null;
		Accountholderdetails AccountHolderDetails = null;
		AccountHolderDetails.setAddress(address);
		AccountHolderDetails.setCountryid(countryID);
		AccountHolderDetails.setDateofbirth(cal);
		AccountHolderDetails.setEmployer(employer);
		AccountHolderDetails.setFirstname(firstname);
		AccountHolderDetails.setGenderid(gender);
		AccountHolderDetails.setIdentification(identification);
		AccountHolderDetails.setLanguageid(lang);
		AccountHolderDetails.setLastname(lastname);
		AccountHolderDetails.setLgaid(lga);
		AccountHolderDetails.setMiddlename(middlename);
		AccountHolderDetails.setOccupation(occupation);
		AccountHolderDetails.setPrefix(prefix);
		AccountHolderDetails.setPrimarycontact(Pcontact);
		AccountHolderDetails.setSecondarycontact(Scontact);
		AccountHolderDetails.setStateid(stateID);
		AccountHolderDetails.setSuffix(suffix);

		registrationDetails.setAccountholderdetails(AccountHolderDetails);
		registrationDetails.setBankaccount(bankAccount);
		registrationDetails.setBankcodeid(bankcodeID);
		registrationDetails.setBankdomainname(bankdomainname);
		registrationDetails.setCurrency(currency);
		registrationDetails.setClearingnumber(clearingNumber);
		registrationDetails.setEmail(email);
		registrationDetails.setMsisdn(msisdn);
		registrationDetails.setProfileid(profileID);
		registrationDetails.setSecurityquestions(secQuestion);
		registrationDetails.setTermscondition(TermsCondition);
		registrationDetails.setUsername(username);

		registration36.setRegistration(registrationDetails);
		Registrationrequestresponse response = provision
				.registration(registration36).getRegistrationResponse()
				.get_return();
		return response;

	}

	public Activationrequestresponse activateUser() throws RemoteException {
		Activation act = new Activation();
		ActivationE actE = new ActivationE();
		actE.setActivation(act);
		provision.activation(actE);
		return provision.activation(actE).getActivationResponse().get_return();
	}

}
