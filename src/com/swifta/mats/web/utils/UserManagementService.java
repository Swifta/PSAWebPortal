package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Date;

import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Accountholderdetails;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Address;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Credentials;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Identification;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.IdentificationType;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Linkaccountrequest;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.LinkaccountrequestE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.LinkaccountrequestResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.LinkaccountrequestResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Linkaccountresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.PrimaryContactInfo;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Registration;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Registrationrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SecondaryContactInfo;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Securityquestions;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetDefaultaccountrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setdefaultaccount;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetdefaultaccountE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetdefaultaccountResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetdefaultaccountResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setparentaccount;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetparentaccountE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetparentaccountResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetparentaccountResponseE;

import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Accountholderdetails;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationrequest;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Address;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Credentials;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Identification;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.IdentificationType;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Linkaccountrequest;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.LinkaccountrequestE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.LinkaccountrequestResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.LinkaccountrequestResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Linkaccountresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.PrimaryContactInfo;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Registration;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Registrationrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SecondaryContactInfo;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Securityquestions;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetDefaultaccountrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setdefaultaccount;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetdefaultaccountE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetdefaultaccountResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetdefaultaccountResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setparentaccount;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetparentaccountE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetparentaccountResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetparentaccountResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setparentrequestresponse;

public class UserManagementService {
	ProvisioningStub matsStub;

	public String registerUser(String bankAccount, int bankCodeid,
			String bankdomainNameid, String clearingNumber, String currencyid,
			String email, String msisdn, int profileid, String securityQuest,
			String securityAns, String termscondition, String username,
			int countryid, Date dateofBirth, String employer, String firstname,
			int genderid, int languageid, String lastname, int Lgaid,
			String middlename, String occupation, String prefix, int stateid,
			String suffix, String city, String postalcode,
			String streetAddress, String province, Date Expirydate,
			String idNumber, String idType, Date Issuedate, String Issue,
			String PrimaryEmail, String PrimaryMobilenumber,
			String PrimaryPhonenumber, String SecondaryEmail,
			String SecondaryMobilenumber, String SecondaryPhonenumber)
			throws RemoteException {
		matsStub = new ProvisioningStub();

		Accountholderdetails accountholderdetails = new Accountholderdetails();
		matsStub = new ProvisioningStub(
				"http://54.164.96.105:8283/services/ProvisioningService/");
		Accountholderdetails accountholderdetails = new Accountholderdetails();
		Address address = new Address();
		address.setCity(city);
		address.setPostalCode(postalcode);
		address.setProvince(province);
		address.setStreetaddress(streetAddress);

		accountholderdetails.setAddress(address);
		accountholderdetails.setCountryid(countryid);
		accountholderdetails.setDateofbirth(utils.DateToCalendar(dateofBirth));
		accountholderdetails.setEmployer(employer);
		accountholderdetails.setFirstname(firstname);
		accountholderdetails.setGenderid(genderid);

		Identification identification = new Identification();
		identification.setExpirydate(utils.DateToCalendar(Expirydate));
		identification.setIdentificationNo(idNumber);

		IdentificationType idType3 = null;

		identification.setIdentificationType(idType3);
		identification.setIssueDate(String.valueOf(Issuedate));
		identification.setIssuer(Issue);

		accountholderdetails.setIdentification(identification);
		accountholderdetails.setLanguageid(languageid);
		accountholderdetails.setLastname(lastname);
		accountholderdetails.setLgaid(Lgaid);
		accountholderdetails.setMiddlename(middlename);
		accountholderdetails.setOccupation(occupation);
		accountholderdetails.setPrefix(prefix);
		accountholderdetails.setStateid(stateid);
		accountholderdetails.setSuffix(suffix);

		PrimaryContactInfo PrimaryContact = new PrimaryContactInfo();
		PrimaryContact.setEmail(PrimaryEmail);
		PrimaryContact.setMobilenumber(PrimaryMobilenumber);
		PrimaryContact.setName(firstname);
		PrimaryContact.setPhonenumber(PrimaryPhonenumber);
		accountholderdetails.setPrimarycontact(PrimaryContact);

		SecondaryContactInfo SecondaryContact = new SecondaryContactInfo();
		SecondaryContact.setEmail(SecondaryEmail);
		SecondaryContact.setMobilenumber(SecondaryMobilenumber);
		SecondaryContact.setName(firstname);
		SecondaryContact.setPhonenumber(SecondaryPhonenumber);
		accountholderdetails.setSecondarycontact(SecondaryContact);

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
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();
				} else {
					statusMessage = "Response3 is empty";
				}

			} else {
				statusMessage = "Response2 is empty";
			}
		} else {
			statusMessage = "Response1 is empty";
		}

		return statusMessage;

	}

	public String activateUser(String bankdomainid, String currency,
			String IDnumber, String resourceid, String SecurityAns,
			String firstPin, String confirmPin) throws RemoteException {
		String statusMessage = "";
		matsStub = new ProvisioningStub(
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
		ActivationrequestResponseE response = matsStub.activationrequest(actE);

		if (response != null) {
			ActivationrequestResponse response2 = response
					.getActivationrequestResponse();
			if (response2 != null) {
				Activationresponse response3 = response2.get_return();
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();
				} else {
					statusMessage = "Response3 is empty";
				}
			} else {
				statusMessage = "Activation Response is empty";
			}
		} else {
			statusMessage = "Activation Response is empty";
		}

		return statusMessage;
	}

	public String linkUser(String parentresourceid, String profileid,
			String reason, String userresourceid) throws RemoteException {
		String statusMessage = "";
		matsStub = new ProvisioningStub(
				"http://54.164.96.105:8283/services/ProvisioningService/");

		LinkaccountrequestE linkaccountrequest = new LinkaccountrequestE();
		Linkaccountrequest linkrequest = new Linkaccountrequest();
		linkrequest.setParaentaccountresourceid(parentresourceid);
		linkrequest.setProfileid(profileid);
		linkrequest.setReason(reason);
		linkrequest.setUserresourceid(userresourceid);

		linkaccountrequest.setLinkaccountrequest(linkrequest);

		LinkaccountrequestResponseE response = matsStub
				.linkaccountrequest(linkaccountrequest);

		if (response != null) {
			LinkaccountrequestResponse response2 = response
					.getLinkaccountrequestResponse();
			if (response2 != null) {
				Linkaccountresponse response3 = response2.get_return();
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();
				} else {
					statusMessage = "Response3 is empty";
				}
			} else {
				statusMessage = "Link request Response is empty";
			}
		} else {
			statusMessage = "Link request Response is empty";
		}

		return statusMessage;
	}

	public String setParent(String parentid, String reason, String resourceid)
			throws RemoteException {
		String statusMessage = "";
		matsStub = new ProvisioningStub(
				"http://54.164.96.105:8283/services/ProvisioningService/");

		SetparentaccountE setparentaccount = new SetparentaccountE();
		Setparentaccount sparentaccount = new Setparentaccount();

		sparentaccount.setParaentaccountresourceid(parentid);
		sparentaccount.setReason(reason);
		sparentaccount.setUserresourceid(resourceid);

		setparentaccount.setSetparentaccount(sparentaccount);

		SetparentaccountResponseE response = matsStub
				.setparentaccount(setparentaccount);

		if (response != null) {
			SetparentaccountResponse response2 = response
					.getSetparentaccountResponse();
			if (response2 != null) {
				Setparentrequestresponse response3 = response2.get_return();
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();
				} else {
					statusMessage = "Response3 is empty";
				}
			} else {
				statusMessage = "setParent Response is empty";
			}
		} else {
			statusMessage = "setParent Response is empty";
		}

		return statusMessage;
	}

	public String setDefaultAccount(String parentid, String reason,
			String userresourceid) throws RemoteException {
		String statusMessage = "";
		matsStub = new ProvisioningStub(
				"http://54.164.96.105:8283/services/ProvisioningService/");

		SetdefaultaccountE setdefaultaccounte = new SetdefaultaccountE();
		Setdefaultaccount setdefaultaccount = new Setdefaultaccount();
		setdefaultaccount.setParaentaccountresourceid(parentid);
		setdefaultaccount.setReason(reason);
		setdefaultaccount.setUserresourceid(userresourceid);

		setdefaultaccounte.setSetdefaultaccount(setdefaultaccount);

		SetdefaultaccountResponseE response = matsStub
				.setdefaultaccount(setdefaultaccounte);

		if (response != null) {
			SetdefaultaccountResponse response2 = response
					.getSetdefaultaccountResponse();
			if (response2 != null) {
				SetDefaultaccountrequestresponse response3 = response2
						.get_return();
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();
				} else {
					statusMessage = "Response3 is empty";
				}
			} else {
				statusMessage = "setDefaultAccount Response is empty";
			}
		} else {
			statusMessage = "setDefaultAccount Response is empty";
		}

		return statusMessage;
	}

}
