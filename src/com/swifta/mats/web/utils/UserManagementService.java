package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Date;

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

	// String endpoint =
	// "http://127.0.0.1:9760/Provisioning-1.0.0/services/provisioning";

	static String esbendpoint = "http://54.173.157.210:8283/services/Provisionservice";

	ProvisioningStub matsStub;

	org.apache.axis2.client.ServiceClient _serviceClient = null;

	private static EndpointReference targetEPR = new EndpointReference(
			esbendpoint);

	public String registerUser(String bankAccount, int bankCodeid,
			String bankdomainNameid, String clearingNumber, String currencyid,
			String email, String msisdn, int profileid, String securityQuest,
			String securityAns, String termscondition, String username,
			int countryid, Date dateofBirth, String employer, String firstname,
			String gender, int languageid, String lastname, int Lgaid,
			String middlename, String occupation, String prefix, int stateid,
			String suffix, String city, String postalcode,
			String streetAddress, String province, Date Expirydate,
			String idNumber, String idType, Date Issuedate, String Issue,
			String PrimaryEmail, String PrimaryMobilenumber,
			String PrimaryPhonenumber, String SecondaryEmail,
			String SecondaryMobilenumber, String SecondaryPhonenumber)
			throws RemoteException {
		// matsStub = new ProvisioningStub();

		Accountholderdetails accountholderdetails = new Accountholderdetails();
		matsStub = new ProvisioningStub(esbendpoint);
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
		accountholderdetails.setGender(ProvisioningStub.Gender.Factory
				.fromValue(gender));

		Identification identification = new Identification();
		identification.setExpirydate(utils.DateToCalendar(Expirydate));
		identification.setIdentificationNo(idNumber);

		// IdentificationType idType3 = IdentificationType.EMID;

		// matsStub.

		identification
				.setIdentificationType(ProvisioningStub.IdentificationType.Factory
						.fromValue(idType));
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
			String firstPin, String confirmPin) throws Exception {

		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);
		Credentials cre = new Credentials();
		cre.setFirstpin(firstPin);
		cre.setConfirmpin(confirmPin);

		ActivationrequestE actE = new ActivationrequestE();

		Activationrequest act = new Activationrequest();

		act.setBankdomainid(bankdomainid);
		act.setCredential(cre);
		act.setCurrency(currency);
		act.setIdentificationno(IDnumber);
		act.setResourceid(resourceid);
		act.setSecurityquestionanswer(SecurityAns);

		actE.setActivationrequest(act);
		ActivationrequestResponseE response = matsStub.activationrequest(actE);

		if (response != null) {
			ActivationrequestResponse response2 = response
					.getActivationrequestResponse();
			if (response2 != null) {
				Activationresponse response3 = response2.get_return();
				// System.out.println(response3.getResponseMessage());
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();
					if (statusMessage
							.equals("ACCOUNT_HOLDER_ACCOUNT_ACTIVATION_SUCCESSFUL")) {
						UserManagementService
								.provisioning(resourceid, firstPin);
					}
				}
			} else {
				statusMessage = "Activation Response is empty";
			}
		} else {
			statusMessage = "Activation Response is empty";
		}

		return statusMessage;
	}

	public static void provisioning(String username, String password) {
		try {
			ServiceClient sender = null;

			OMFactory fac = OMAbstractFactory.getOMFactory();

			OMNamespace omNs = fac.createOMNamespace(
					"http://service.ws.um.carbon.wso2.org", "ser");

			OMElement addUser = fac.createOMElement("activationrequest", omNs);

			OMElement userNameElement = fac.createOMElement("userName", null);
			userNameElement.addChild(fac
					.createOMText(userNameElement, username));

			OMElement credential1Element = fac.createOMElement("credential",
					null);
			credential1Element.addChild(fac.createOMText(credential1Element,
					password));

			OMElement profileNameElement = fac.createOMElement("profileName",
					null);
			profileNameElement.addChild(fac
					.createOMText(profileNameElement, ""));

			OMElement requirePasswordChangeElement = fac.createOMElement(
					"requirePasswordChange", null);
			requirePasswordChangeElement.addChild(fac.createOMText(
					requirePasswordChangeElement, "false"));

			addUser.addChild(userNameElement);
			addUser.addChild(credential1Element);
			addUser.addChild(profileNameElement);
			addUser.addChild(requirePasswordChangeElement);

			Options options = new Options();
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setAction("urn:addUser");

			options.setProperty(HTTPConstants.CHUNKED, Constants.VALUE_FALSE);

			sender = new ServiceClient();
			sender.setOptions(options);
			sender.fireAndForget(addUser);

		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String linkUser(String paraentaccountresourceid, String profileid,
			String initiatinguserresourceid, String childuserresourceid)
			throws RemoteException {
		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);

		LinkaccountrequestE linkaccountrequest = new LinkaccountrequestE();
		Linkaccountrequest linkrequest = new Linkaccountrequest();
		linkrequest.setChilduserresourceid(childuserresourceid);
		linkrequest.setParaentaccountresourceid(paraentaccountresourceid);
		linkrequest.setProfileid(profileid);
		linkrequest.setInitiatinguserresourceid(paraentaccountresourceid);

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
				"http://54.164.96.105:8283/services/Provisionservice");

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

	public String setDefaultAccount(String initiatinguserresourceid,
			String childuserresourceid, String paraentaccountresourceid)
			throws RemoteException {
		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);

		SetdefaultaccountE setdefaultaccounte = new SetdefaultaccountE();
		Setdefaultaccount setdefaultaccount = new Setdefaultaccount();
		setdefaultaccount.setParaentaccountresourceid(paraentaccountresourceid);
		setdefaultaccount.setChilduserresourceid(childuserresourceid);
		setdefaultaccount.setInitiatinguserresourceid(initiatinguserresourceid);

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

	public static void resetPassword(String username, String oldPassword,
			String newPassword) {
		try {
			ServiceClient sender = null;

			OMFactory fac = OMAbstractFactory.getOMFactory();

			OMNamespace omNs = fac.createOMNamespace(
					"http://service.ws.um.carbon.wso2.org", "ser");

			OMElement addUser = fac.createOMElement("activationrequest", omNs);

			OMElement userNameElement = fac.createOMElement("userName", null);
			userNameElement.addChild(fac
					.createOMText(userNameElement, "166735"));

			OMElement credential1Element = fac.createOMElement("credential",
					null);
			credential1Element.addChild(fac.createOMText(credential1Element,
					"modupe"));

			OMElement profileNameElement = fac.createOMElement("profileName",
					null);
			profileNameElement.addChild(fac.createOMText(profileNameElement,
					"me"));

			OMElement requirePasswordChangeElement = fac.createOMElement(
					"requirePasswordChange", null);
			requirePasswordChangeElement.addChild(fac.createOMText(
					requirePasswordChangeElement, "false"));

			addUser.addChild(userNameElement);
			addUser.addChild(credential1Element);
			addUser.addChild(profileNameElement);
			addUser.addChild(requirePasswordChangeElement);

			Options options = new Options();
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setAction("urn:addUser");

			options.setProperty(HTTPConstants.CHUNKED, Constants.VALUE_FALSE);

			sender = new ServiceClient();
			sender.setOptions(options);
			sender.fireAndForget(addUser);

		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
