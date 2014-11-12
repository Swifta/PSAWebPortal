package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Date;

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
		matsStub.Accountholderdetails accountholderdetails = new Accountholderdetails();
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
		matsStub = new ProvisioningStub();
		Credentials cre = new Credentials();
		cre.setFirstpin(firstPin);
		cre.setConfirmpin(confirmPin);

		Activation act = new Activation();
		act.setBankdomainid(bankdomainid);
		act.setCredential(cre);
		act.setCurrency(currency);
		act.setIdentificationno(IDnumber);
		act.setResourceid(resourceid);
		act.setSecurityquestionanswer(SecurityAns);

		ActivationE actE = new ActivationE();
		actE.setActivation(act);
		ActivationResponseE response = matsStub.activation(actE);

		if (response != null) {
			ActivationResponse response2 = response.getActivationResponse();
			if (response2 != null) {
				Activationrequestresponse response3 = response2.get_return();
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
		matsStub = new ProvisioningStub();

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
		matsStub = new ProvisioningStub();

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
				SetDefaultaccountrequestresponse response3 = response2
						.get_return();
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
		matsStub = new ProvisioningStub();

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
