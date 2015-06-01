package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import org.apache.axis2.wsdl.WSDLConstants;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Accountholderdetails;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationrequest;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationrequestResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Activationresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddProfile;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddProfileE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddProfileResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddProfileResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddProfileThreshold;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddProfileThresholdE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddProfileThresholdResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddProfileThresholdResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddProfileThresholdrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddProfilerequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Address;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Changepassword;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ChangepasswordE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ChangepasswordResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ChangepasswordResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Changepasswordrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Credentials;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeleteProfileThreshold;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeleteProfileThresholdE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeleteProfileThresholdResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeleteProfileThresholdResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeleteProfileThresholdrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditProfile;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditProfileE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditProfileResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditProfileResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditProfileThreshold;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditProfileThresholdE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditProfileThresholdResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditProfileThresholdResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditProfileThresholdrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditProfilerequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Identification;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Linkaccount;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.LinkaccountE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.LinkaccountResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.LinkaccountResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Linkaccountrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.PasswordReset;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.PasswordResetE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.PasswordResetResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.PasswordResetResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.PasswordResetrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.PrimaryContactInfo;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Registration;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Registrationrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RemoveProfile;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RemoveProfileE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RemoveProfilePermission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RemoveProfilePermissionE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RemoveProfilePermissionResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RemoveProfilePermissionResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RemoveProfilePermissionrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RemoveProfileResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RemoveProfileResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RemoveProfilerequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SecondaryContactInfo;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Securityquestions;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceFees;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetDefaultaccountrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetProfilePermission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetProfilePermissionE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetProfilePermissionResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetProfilePermissionResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetProfilePermissionrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setdefaultaccount;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetdefaultaccountE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetdefaultaccountResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetdefaultaccountResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setparentaccount;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetparentaccountE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetparentaccountResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetparentaccountResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setparentrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setupservicefees;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupservicefeesE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.UnLinkaccountrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Unlinkaccount;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.UnlinkaccountE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.UnlinkaccountResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.UnlinkaccountResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Webauthenticate;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.WebauthenticateE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.WebauthenticateResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.WebauthenticateResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Webauthenticationrequestresponse;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getactivepermissionbyprofileid;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getnonactivepermissionbyprofileid;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getpermission;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Getpermissions;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.GetpermissionsE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Viewstatementbyagentid;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Viewstatementbyagentresponse;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.Viewstatementbyagentresponses;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub.ViewstatementbyagentresponsesE;
import com.vaadin.ui.UI;

public class UserManagementService {

	// String endpoint =
	// "http://127.0.0.1:9760/Provisioning-1.0.0/services/provisioning";

	// static String esbendpoint =
	// "http://127.0.0.1:8280/services/Provisionservice";

	final static String esbendpoint = MatsWebPortalUI.conf.ESB;
	final static String loggedInUser = UI.getCurrent().getSession()
			.getAttribute("user").toString();

	static ProvisioningStub matsStub;
	static MatsreportingserviceStub matsReportstub;

	org.apache.axis2.client.ServiceClient _serviceClient = null;

	private static EndpointReference targetEPR = new EndpointReference(
			esbendpoint);

	public static String registerUser(String bankAccount, int bankCodeid,
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

		System.out.println(idType);

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

		registration.setLoggedinUser(loggedInUser);
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

	public static String activateUser(String bankdomainid, String currency,
			String IDnumber, String resourceid, String SecurityAns,
			String firstPin, String confirmPin) {

		String statusMessage = "";
		try {

			matsStub = new ProvisioningStub(esbendpoint);
			Credentials cre = new Credentials();
			cre.setFirstpin(firstPin);
			cre.setConfirmpin(confirmPin);

			ActivationrequestE actE = new ActivationrequestE();

			Activationrequest act = new Activationrequest();

			act.setLoggedinUser(loggedInUser);
			act.setBankdomainid(bankdomainid);
			act.setCredential(cre);
			act.setCurrency(currency);
			act.setIdentificationno(IDnumber);
			act.setResourceid(resourceid);
			act.setSecurityquestionanswer(SecurityAns);

			actE.setActivationrequest(act);
			ActivationrequestResponseE response = matsStub
					.activationrequest(actE);

			if (response != null) {
				ActivationrequestResponse response2 = response
						.getActivationrequestResponse();
				if (response2 != null) {
					Activationresponse response3 = response2.get_return();
					// System.out.println(response3.getResponseMessage());
					if (response3 != null) {
						statusMessage = response3.getResponsemessage();
					}
				} else {
					statusMessage = "Activation Response is empty";
				}
			} else {
				statusMessage = "Activation Response is empty";
			}
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				System.out
						.println("HTTP status code: "
								+ matsStub
										._getServiceClient()
										.getLastOperationContext()
										.getMessageContext(
												WSDLConstants.MESSAGE_LABEL_IN_VALUE)
										.getProperty(
												HTTPConstants.MC_HTTP_STATUS_CODE));
				Integer statuscode = (Integer) matsStub
						._getServiceClient()
						.getLastOperationContext()
						.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE)
						.getProperty(HTTPConstants.MC_HTTP_STATUS_CODE);
				if (statuscode == 202) {
					statusMessage = "ACCOUNT_HOLDER_ACCOUNT_ACTIVATION_SUCCESSFUL";
				} else {
					statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				}
			} catch (Exception e1) {
				statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				e1.printStackTrace();
			}
		}

		return statusMessage;
	}

	public static String linkUser(String paraentaccountresourceid,
			int profileid, String initiatinguserresourceid,
			String childuserresourceid) throws RemoteException {
		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);

		LinkaccountE linkaccountrequest = new LinkaccountE();

		Linkaccount linkrequest = new Linkaccount();
		linkrequest.setLoggedinUser(UI.getCurrent().getSession()
				.getAttribute("user").toString());
		linkrequest.setChilduserresourceid(childuserresourceid);
		linkrequest.setParaentaccountresourceid(paraentaccountresourceid);
		linkrequest.setProfileid(profileid);
		linkrequest.setInitiatinguserresourceid(paraentaccountresourceid);

		linkaccountrequest.setLinkaccount(linkrequest);

		LinkaccountResponseE response = matsStub
				.linkaccount(linkaccountrequest);

		if (response != null) {
			LinkaccountResponse response2 = response.getLinkaccountResponse();
			if (response2 != null) {
				Linkaccountrequestresponse response3 = response2.get_return();
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

	public static String setParent(String parentid, String reason,
			String resourceid) throws RemoteException {
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

	public static String setDefaultAccount(String initiatinguserresourceid,
			String childuserresourceid, String paraentaccountresourceid)
			throws RemoteException {
		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);

		SetdefaultaccountE setdefaultaccounte = new SetdefaultaccountE();
		Setdefaultaccount setdefaultaccount = new Setdefaultaccount();

		setdefaultaccount.setLoggedinUser(UI.getCurrent().getSession()
				.getAttribute("user").toString());
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

	public static String passwordReset(String username, String newPassword)
			throws Exception {

		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);

		PasswordResetE passwordResetE = new PasswordResetE();
		PasswordReset passwordReset = new PasswordReset();

		// passwordReset.setLoggedinUser(UI.getCurrent().getSession()
		// .getAttribute("user").toString());

		passwordReset.setLoggedinUser(loggedInUser);
		passwordReset.setUsername(username);
		passwordReset.setConfirmnewpassword(newPassword);

		passwordResetE.setPasswordReset(passwordReset);

		PasswordResetResponseE response = matsStub
				.passwordReset(passwordResetE);

		if (response != null) {
			PasswordResetResponse response2 = response
					.getPasswordResetResponse();
			if (response2 != null) {
				PasswordResetrequestresponse response3 = response2.get_return();
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

	public static String unlockUserAccount(String initiatinguser,
			String username) throws AxisFault {

		ServiceClient sender = new ServiceClient();

		String statusMessage = "";
		try {

			OMFactory fac = OMAbstractFactory.getOMFactory();

			OMNamespace omNs = fac.createOMNamespace(
					"http://services.mgt.identity.carbon.wso2.org", "ser");

			OMElement unlockUserAccountElem = fac.createOMElement(
					"unlockUserAccount", omNs);

			OMElement userNameElement = fac.createOMElement("userName", omNs);
			userNameElement.addChild(fac
					.createOMText(userNameElement, username));

			unlockUserAccountElem.addChild(userNameElement);

			Options options = new Options();
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setAction("urn:unlockUserAccount");

			options.setProperty(HTTPConstants.CHUNKED, Constants.VALUE_FALSE);

			sender = new ServiceClient();
			sender.setOptions(options);
			OMElement result = sender.sendReceive(unlockUserAccountElem);

			OMElement firstelement = result.getFirstElement();

			statusMessage = firstelement.getFirstElement().getText();

			System.out.println("From util: " + statusMessage);

			System.out.println("HTTP status code: "
					+ sender.getLastOperationContext()
							.getMessageContext(
									WSDLConstants.MESSAGE_LABEL_IN_VALUE)
							.getProperty(HTTPConstants.MC_HTTP_STATUS_CODE));

			return statusMessage;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			try {
				System.out
						.println("HTTP status code: "
								+ sender.getLastOperationContext()
										.getMessageContext(
												WSDLConstants.MESSAGE_LABEL_IN_VALUE)
										.getProperty(
												HTTPConstants.MC_HTTP_STATUS_CODE));
				Integer statuscode = (Integer) sender
						.getLastOperationContext()
						.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE)
						.getProperty(HTTPConstants.MC_HTTP_STATUS_CODE);
				if (statuscode == 202) {
					statusMessage = "ACCOUNT UNLOCKED SUCCESSFULLY";
				} else {
					statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER1";
				}
			} catch (Exception e1) {
				statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				e1.printStackTrace();
			}
		}
		return statusMessage;

	}

	public static String lockUserAccountByAdmin(String initiatinguser,
			String username) throws AxisFault {

		ServiceClient sender = new ServiceClient();

		String statusMessage = "";
		try {

			OMFactory fac = OMAbstractFactory.getOMFactory();

			OMNamespace omNs = fac.createOMNamespace(
					"http://services.mgt.identity.carbon.wso2.org", "ser");

			OMElement lockUserAccountElem = fac.createOMElement(
					"lockUserAccount", omNs);

			OMElement userNameElement = fac.createOMElement("userName", omNs);
			userNameElement.addChild(fac
					.createOMText(userNameElement, username));

			lockUserAccountElem.addChild(userNameElement);

			Options options = new Options();
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setAction("urn:lockUserAccount");

			options.setProperty(HTTPConstants.CHUNKED, Constants.VALUE_FALSE);

			sender = new ServiceClient();
			sender.setOptions(options);
			OMElement result = sender.sendReceive(lockUserAccountElem);

			OMElement firstelement = result.getFirstElement();

			statusMessage = firstelement.getFirstElement().getText();
			System.out.println("From util: " + statusMessage);

			System.out.println("HTTP status code: "
					+ sender.getLastOperationContext()
							.getMessageContext(
									WSDLConstants.MESSAGE_LABEL_IN_VALUE)
							.getProperty(HTTPConstants.MC_HTTP_STATUS_CODE));

			return statusMessage;

		} catch (Exception e) {

			try {
				System.out
						.println("HTTP status code: "
								+ sender.getLastOperationContext()
										.getMessageContext(
												WSDLConstants.MESSAGE_LABEL_IN_VALUE)
										.getProperty(
												HTTPConstants.MC_HTTP_STATUS_CODE));
				Integer statuscode = (Integer) sender
						.getLastOperationContext()
						.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE)
						.getProperty(HTTPConstants.MC_HTTP_STATUS_CODE);
				if (statuscode == 202) {
					statusMessage = "ACCOUNT LOCKED SUCCESSFULLY";
				} else {
					statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER1";
				}
			} catch (Exception e1) {
				statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				e1.printStackTrace();
			}
		}
		return statusMessage;

	}

	public static String unlinkUser(String paraentaccountresourceid,
			int profileid, String initiatinguserresourceid,
			String childuserresourceid) throws RemoteException {
		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);

		UnlinkaccountE unlinkaccountrequest = new UnlinkaccountE();
		Unlinkaccount unlinkrequest = new Unlinkaccount();
		unlinkrequest.setLoggedinUser(UI.getCurrent().getSession()
				.getAttribute("user").toString());
		unlinkrequest.setChilduserresourceid(childuserresourceid);
		unlinkrequest.setParaentaccountresourceid(paraentaccountresourceid);
		unlinkrequest.setProfileid(profileid);
		unlinkrequest.setInitiatinguserresourceid(paraentaccountresourceid);

		unlinkaccountrequest.setUnlinkaccount(unlinkrequest);

		UnlinkaccountResponseE response = matsStub
				.unlinkaccount(unlinkaccountrequest);

		if (response != null) {
			UnlinkaccountResponse response2 = response
					.getUnlinkaccountResponse();
			if (response2 != null) {
				UnLinkaccountrequestresponse response3 = response2.get_return();
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();
				}

				else {
					statusMessage = "Response3 is empty";
				}

			} else {
				statusMessage = "unLink request Response is empty";
			}

		} else {
			statusMessage = "unLink request Response is empty";
		}

		return statusMessage;
	}

	public static String addProfile(String profilename, int profiletypeid)
			throws Exception {

		String statusMessage = "";
		try {

			matsStub = new ProvisioningStub(esbendpoint);

			AddProfileE addProfileE = new AddProfileE();
			AddProfile addProfile = new AddProfile();

			// addProfile.setLoggedinUser(UI.getCurrent().getSession()
			// .getAttribute("user").toString());

			addProfile.setLoggedinUser(loggedInUser);
			addProfile.setProfilename(profilename);
			addProfile.setProfiletypeid(profiletypeid);

			addProfileE.setAddProfile(addProfile);

			AddProfileResponseE response = matsStub.addProfile(addProfileE);

			if (response != null) {
				AddProfileResponse response2 = response.getAddProfileResponse();
				if (response2 != null) {
					AddProfilerequestresponse response3 = response2
							.get_return();
					if (response3 != null) {
						statusMessage = response3.getResponsemessage();
					}

					else {
						statusMessage = "Response3 is empty";
					}

				} else {
					statusMessage = "addprofile request Response is empty";
				}

			} else {
				statusMessage = "addprofile request Response is empty";
			}

		} catch (Exception e) {
			try {
				System.out
						.println("HTTP status code: "
								+ matsStub
										._getServiceClient()
										.getLastOperationContext()
										.getMessageContext(
												WSDLConstants.MESSAGE_LABEL_IN_VALUE)
										.getProperty(
												HTTPConstants.MC_HTTP_STATUS_CODE));
				Integer statuscode = (Integer) matsStub
						._getServiceClient()
						.getLastOperationContext()
						.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE)
						.getProperty(HTTPConstants.MC_HTTP_STATUS_CODE);
				if (statuscode == 202) {
					statusMessage = "Profile added successfully";
				} else {
					statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				}
			} catch (Exception e1) {
				statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				e1.printStackTrace();
			}
		}

		return statusMessage;

	}

	public static String editProfile(String profilename, String oldprofilename,
			int profileid) throws Exception {
		String statusMessage = "";

		try {
			matsStub = new ProvisioningStub(esbendpoint);

			EditProfileE editProfileE = new EditProfileE();
			EditProfile editProfile = new EditProfile();

			// editProfile.setLoggedinUser(UI.getCurrent().getSession()
			// .getAttribute("user").toString());
			editProfile.setLoggedinUser(loggedInUser);
			editProfile.setProfilename(profilename);
			editProfile.setOldprofilename(oldprofilename);
			editProfile.setProfileid(profileid);

			editProfileE.setEditProfile(editProfile);

			EditProfileResponseE response = matsStub.editProfile(editProfileE);

			if (response != null) {
				EditProfileResponse response2 = response
						.getEditProfileResponse();
				if (response2 != null) {
					EditProfilerequestresponse response3 = response2
							.get_return();
					if (response3 != null) {
						statusMessage = response3.getResponsemessage();
					}

					else {
						statusMessage = "Response3 is empty";
					}

				} else {
					statusMessage = "editprofile request Response is empty";
				}

			} else {
				statusMessage = "editprofile request Response is empty";
			}
		} catch (Exception e) {
			try {
				System.out
						.println("HTTP status code: "
								+ matsStub
										._getServiceClient()
										.getLastOperationContext()
										.getMessageContext(
												WSDLConstants.MESSAGE_LABEL_IN_VALUE)
										.getProperty(
												HTTPConstants.MC_HTTP_STATUS_CODE));
				Integer statuscode = (Integer) matsStub
						._getServiceClient()
						.getLastOperationContext()
						.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE)
						.getProperty(HTTPConstants.MC_HTTP_STATUS_CODE);
				if (statuscode == 202) {
					statusMessage = "Profile renamed successfully";
				} else {
					statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				}
			} catch (Exception e1) {
				statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				e1.printStackTrace();
			}

		}

		return statusMessage;

	}

	public static String removeProfile(String profilename, int profileid)
			throws Exception {
		String statusMessage = "";

		try {
			matsStub = new ProvisioningStub(esbendpoint);

			RemoveProfileE removeProfileE = new RemoveProfileE();
			RemoveProfile removeProfile = new RemoveProfile();

			// removeProfile.setLoggedinUser(UI.getCurrent().getSession()
			// .getAttribute("user").toString());
			removeProfile.setLoggedinUser(loggedInUser);
			removeProfile.setProfilename(profilename);
			removeProfile.setProfileid(profileid);

			removeProfileE.setRemoveProfile(removeProfile);

			RemoveProfileResponseE response = matsStub
					.removeProfile(removeProfileE);

			if (response != null) {
				RemoveProfileResponse response2 = response
						.getRemoveProfileResponse();
				if (response2 != null) {
					RemoveProfilerequestresponse response3 = response2
							.get_return();
					if (response3 != null) {
						statusMessage = response3.getResponsemessage();
					}

					else {
						statusMessage = "Response3 is empty";
					}

				} else {
					statusMessage = "removeprofile request Response is empty";
				}

			} else {
				statusMessage = "removeprofile request Response is empty";
			}
		} catch (Exception e) {
			try {
				System.out
						.println("HTTP status code: "
								+ matsStub
										._getServiceClient()
										.getLastOperationContext()
										.getMessageContext(
												WSDLConstants.MESSAGE_LABEL_IN_VALUE)
										.getProperty(
												HTTPConstants.MC_HTTP_STATUS_CODE));
				Integer statuscode = (Integer) matsStub
						._getServiceClient()
						.getLastOperationContext()
						.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE)
						.getProperty(HTTPConstants.MC_HTTP_STATUS_CODE);
				if (statuscode == 202) {
					statusMessage = "Profile removed successfully";
				} else {
					statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				}
			} catch (Exception e1) {
				statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				e1.printStackTrace();
			}
		}
		return statusMessage;

	}

	public static String removeProfilePermission(String profilename,
			int profileid, String[] action) throws Exception {
		String statusMessage = "";
		try {
			matsStub = new ProvisioningStub(esbendpoint);

			RemoveProfilePermissionE removeProfilePermissionE = new RemoveProfilePermissionE();
			RemoveProfilePermission removeProfilePermission = new RemoveProfilePermission();

			removeProfilePermission.setLoggedinUser(loggedInUser);
			removeProfilePermission.setProfilename(profilename);
			removeProfilePermission.setProfileid(profileid);
			removeProfilePermission.setOperation(action);

			removeProfilePermissionE
					.setRemoveProfilePermission(removeProfilePermission);

			RemoveProfilePermissionResponseE response = matsStub
					.removeProfilePermission(removeProfilePermissionE);

			if (response != null) {
				RemoveProfilePermissionResponse response2 = response
						.getRemoveProfilePermissionResponse();
				if (response2 != null) {
					RemoveProfilePermissionrequestresponse response3 = response2
							.get_return();
					if (response3 != null) {
						statusMessage = response3.getResponsemessage();
					}

					else {
						statusMessage = "Response3 is empty";
					}

				} else {
					statusMessage = "removeprofilepermission request Response is empty";
				}

			} else {
				statusMessage = "removeprofilepermission request Response is empty";
			}
		} catch (Exception e) {
			try {
				System.out
						.println("HTTP status code: "
								+ matsStub
										._getServiceClient()
										.getLastOperationContext()
										.getMessageContext(
												WSDLConstants.MESSAGE_LABEL_IN_VALUE)
										.getProperty(
												HTTPConstants.MC_HTTP_STATUS_CODE));
				Integer statuscode = (Integer) matsStub
						._getServiceClient()
						.getLastOperationContext()
						.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE)
						.getProperty(HTTPConstants.MC_HTTP_STATUS_CODE);
				if (statuscode == 202) {
					statusMessage = "Profile permission removed successfully";
				} else {
					statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				}
			} catch (Exception e1) {
				statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				e1.printStackTrace();
			}
		}

		return statusMessage;

	}

	public static String setProfilePermission(String profilename,
			int profileid, String[] action) throws Exception {
		String statusMessage = "";
		try {
			matsStub = new ProvisioningStub(esbendpoint);

			SetProfilePermissionE setProfilePermissionE = new SetProfilePermissionE();
			SetProfilePermission setProfilePermission = new SetProfilePermission();

			setProfilePermission.setLoggedinUser(loggedInUser);
			setProfilePermission.setProfilename(profilename);
			setProfilePermission.setProfileid(profileid);
			setProfilePermission.setOperation(action);

			setProfilePermissionE.setSetProfilePermission(setProfilePermission);

			SetProfilePermissionResponseE response = matsStub
					.setProfilePermission(setProfilePermissionE);

			if (response != null) {
				SetProfilePermissionResponse response2 = response
						.getSetProfilePermissionResponse();
				if (response2 != null) {
					SetProfilePermissionrequestresponse response3 = response2
							.get_return();
					if (response3 != null) {
						statusMessage = response3.getResponsemessage();
					}

					else {
						statusMessage = "Response3 is empty";
					}

				} else {
					statusMessage = "setprofilepermission request Response is empty";
				}

			} else {
				statusMessage = "setprofilepermission request Response is empty";
			}
		} catch (Exception e) {
			try {
				System.out
						.println("HTTP status code: "
								+ matsStub
										._getServiceClient()
										.getLastOperationContext()
										.getMessageContext(
												WSDLConstants.MESSAGE_LABEL_IN_VALUE)
										.getProperty(
												HTTPConstants.MC_HTTP_STATUS_CODE));
				Integer statuscode = (Integer) matsStub
						._getServiceClient()
						.getLastOperationContext()
						.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE)
						.getProperty(HTTPConstants.MC_HTTP_STATUS_CODE);
				if (statuscode == 202) {
					statusMessage = "Profile permission configured successfully";
				} else {
					statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				}
			} catch (Exception e1) {
				statusMessage = "REQUEST CANNOT BE COMPLETED AT THIS MOMENT, TRY AGAIN LATER";
				e1.printStackTrace();
			}
		}

		return statusMessage;

	}

	public static String addProfileThreshold(String profilename,
			int[] transactiontypeids, int profileid, int thresholdtypeid,
			int profiletypeid, String value) throws Exception {

		String statusMessage = "";
		try {

			matsStub = new ProvisioningStub(esbendpoint);

			AddProfileThresholdE addProfileThresholdE = new AddProfileThresholdE();
			AddProfileThreshold addProfileThreshold = new AddProfileThreshold();

			addProfileThreshold.setLoggedinUser(UI.getCurrent().getSession()
					.getAttribute("user").toString());
			addProfileThreshold.setProfilename(profilename);
			addProfileThreshold.setProfileid(profileid);
			addProfileThreshold.setAction(transactiontypeids);
			addProfileThreshold.setThresholdtypeid(thresholdtypeid);
			addProfileThreshold.setProfiletypeid(profiletypeid);
			addProfileThreshold.setValue(value);

			addProfileThresholdE.setAddProfileThreshold(addProfileThreshold);

			AddProfileThresholdResponseE response = matsStub
					.addProfileThreshold(addProfileThresholdE);

			if (response != null) {
				AddProfileThresholdResponse response2 = response
						.getAddProfileThresholdResponse();
				if (response2 != null) {
					AddProfileThresholdrequestresponse response3 = response2
							.get_return();
					if (response3 != null) {
						statusMessage = response3.getResponsemessage();
					}

					else {
						statusMessage = "Response3 is empty";
					}

				} else {
					statusMessage = "addprofilethreshold request Response is empty";
				}

			} else {
				statusMessage = "addprofilethreshold request Response is empty";
			}

		} catch (Exception e) {

		}

		return statusMessage;

	}

	public static String editProfileThreshold(String profilename,
			int[] transactiontypeids, int profileid, int thresholdtypeid,
			int profiletypeid, String value) throws Exception {

		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);

		EditProfileThresholdE editProfileThresholdE = new EditProfileThresholdE();
		EditProfileThreshold editProfileThreshold = new EditProfileThreshold();

		editProfileThreshold.setLoggedinUser(UI.getCurrent().getSession()
				.getAttribute("user").toString());
		editProfileThreshold.setProfilename(profilename);
		editProfileThreshold.setProfileid(profileid);
		editProfileThreshold.setAction(transactiontypeids);
		editProfileThreshold.setThresholdtypeid(thresholdtypeid);
		editProfileThreshold.setProfiletypeid(profiletypeid);
		editProfileThreshold.setValue(value);

		editProfileThresholdE.setEditProfileThreshold(editProfileThreshold);

		EditProfileThresholdResponseE response = matsStub
				.editProfileThreshold(editProfileThresholdE);

		if (response != null) {
			EditProfileThresholdResponse response2 = response
					.getEditProfileThresholdResponse();
			if (response2 != null) {
				EditProfileThresholdrequestresponse response3 = response2
						.get_return();
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();
				}

				else {
					statusMessage = "Response3 is empty";
				}

			} else {
				statusMessage = "addprofilethreshold request Response is empty";
			}

		} else {
			statusMessage = "addprofilethreshold request Response is empty";
		}

		return statusMessage;

	}

	public static String deleteProfileThreshold(int[] transactiontypeids,
			int profileid) throws Exception {

		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);

		DeleteProfileThresholdE deleteProfileThresholdE = new DeleteProfileThresholdE();
		DeleteProfileThreshold deleteProfileThreshold = new DeleteProfileThreshold();

		deleteProfileThreshold.setLoggedinUser(UI.getCurrent().getSession()
				.getAttribute("user").toString());
		deleteProfileThreshold.setProfileid(profileid);
		deleteProfileThreshold.setAction(transactiontypeids);

		deleteProfileThresholdE
				.setDeleteProfileThreshold(deleteProfileThreshold);

		DeleteProfileThresholdResponseE response = matsStub
				.deleteProfileThreshold(deleteProfileThresholdE);

		if (response != null) {
			DeleteProfileThresholdResponse response2 = response
					.getDeleteProfileThresholdResponse();
			if (response2 != null) {
				DeleteProfileThresholdrequestresponse response3 = response2
						.get_return();
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();
				}

				else {
					statusMessage = "Response3 is empty";
				}

			} else {
				statusMessage = "addprofilethreshold request Response is empty";
			}

		} else {
			statusMessage = "addprofilethreshold request Response is empty";
		}

		return statusMessage;

	}

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

	public static String changepassword(String username, String oldpassword,
			String newpassword) throws Exception {
		String statusMessage = "";
		matsStub = new ProvisioningStub(esbendpoint);

		ChangepasswordE changepasswordE = new ChangepasswordE();
		Changepassword changepassword = new Changepassword();

		changepassword.setLoggedinUser(loggedInUser);
		changepassword.setUsername(username);
		changepassword.setOldcredentials(oldpassword);
		changepassword.setNewcredentials(newpassword);

		changepasswordE.setChangepassword(changepassword);

		ChangepasswordResponseE response = matsStub
				.changepassword(changepasswordE);

		if (response != null) {
			ChangepasswordResponse response2 = response
					.getChangepasswordResponse();
			if (response2 != null) {
				Changepasswordrequestresponse response3 = response2
						.get_return();
				if (response3 != null) {
					statusMessage = response3.getResponsemessage();

				}

				else {
					statusMessage = "Response3 is empty";
				}

			} else {
				statusMessage = "changepassword request Response is empty";
			}

		} else {
			statusMessage = "changepassword request Response is empty";
		}

		return statusMessage;

	}

	// reporting section - please relocate

	public static Map<String, String> getnonactiveprofilepermission(
			int profileid) throws Exception {
		// String statusMessage = "";
		matsReportstub = new MatsreportingserviceStub(esbendpoint);

		Getnonactivepermissionbyprofileid getnonactivepermissionbyprofileid = new Getnonactivepermissionbyprofileid();

		getnonactivepermissionbyprofileid.setProfileid(profileid);

		GetpermissionsE getpermissionsE = matsReportstub
				.getnonactivepermissionbyprofileid(getnonactivepermissionbyprofileid);

		Getpermissions getpermissions = getpermissionsE.getGetpermissions();
		Getpermission[] perms = getpermissions.getGetpermission();
		if (perms == null)
			return Collections.emptyMap();

		HashMap<String, String> hm = new HashMap<>();

		for (Getpermission perm : perms)
			hm.put(perm.getPermissionsaction(), perm.getPermissions());

		return hm;

	}

	public static Map<String, String> getactiveprofilepermission(int profileid)
			throws Exception {
		// String statusMessage = "";
		matsReportstub = new MatsreportingserviceStub(esbendpoint);

		Getactivepermissionbyprofileid getnonactivepermissionbyprofileid = new Getactivepermissionbyprofileid();

		getnonactivepermissionbyprofileid.setProfileid(profileid);

		GetpermissionsE getpermissionsE = matsReportstub
				.getactivepermissionbyprofileid(getnonactivepermissionbyprofileid);

		HashMap<String, String> hm = new HashMap<>();

		Getpermissions getpermissions = getpermissionsE.getGetpermissions();
		Getpermission[] perms = getpermissions.getGetpermission();
		if (perms == null)
			return Collections.emptyMap();

		for (Getpermission getpermission : perms)
			hm.put(getpermission.getPermissionsaction(),
					getpermission.getPermissions());
		return hm;

	}

	public static void setupfeesBySp(String user, Integer sp, Integer ttid,
			Integer sct, ServiceFees[] sfdetails) throws RemoteException {
		matsStub = new ProvisioningStub(esbendpoint);
		Setupservicefees setupservicefees = new Setupservicefees();

		setupservicefees.setLoggedinUser(user);
		setupservicefees.setUserresourceid(sp);
		setupservicefees.setTransactiontypeid(ttid);
		setupservicefees.setServiceconfigtype(sct);
		setupservicefees.setServicefeedetails(sfdetails);

		SetupservicefeesE ssfe = new SetupservicefeesE();
		ssfe.setSetupservicefees(setupservicefees);
		matsStub.setupservicefees(ssfe);
	}

	public static void viewstatement(String loggedInUser, String agentid1,
			String agentid2, String filteron, String fromdate, String todate)
			throws Exception {

		matsReportstub = new MatsreportingserviceStub(esbendpoint);

		Viewstatementbyagentid viewstatementbyagentid = new Viewstatementbyagentid();
		viewstatementbyagentid.setLoggedInUser(loggedInUser);
		viewstatementbyagentid.setAgentid1(agentid1);
		viewstatementbyagentid.setAgentid2(agentid1);
		viewstatementbyagentid.setFilteron(filteron);
		viewstatementbyagentid.setFromdate(fromdate);
		viewstatementbyagentid.setTodate(todate);

		ViewstatementbyagentresponsesE response = matsReportstub
				.viewstatementbyagentid(viewstatementbyagentid);

		if (response != null) {
			Viewstatementbyagentresponses viewstatementbyagentresponses = response
					.getViewstatementbyagentresponses();

			for (Viewstatementbyagentresponse viewstatementbyagentresponse : viewstatementbyagentresponses
					.getViewstatementbyagentresponse()) {
				System.out.println(viewstatementbyagentresponse
						.getTransactiontype());
				System.out.println(viewstatementbyagentresponse.getAmount());
				System.out.println(viewstatementbyagentresponse.getReceiver());
				System.out.println(viewstatementbyagentresponse.getStatus());
			}

		}

	}

}
