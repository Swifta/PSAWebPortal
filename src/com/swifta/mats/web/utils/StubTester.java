package com.swifta.mats.web.utils;

import org.apache.log4j.Logger;

import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;

public class StubTester {
	private ProvisioningStub provisioningStub = null;
	private static final Logger logger = Logger.getLogger(StubTester.class);

	/*
	 * public void createAddAccountProvisioning(String accountType, String
	 * currency, String profileName, String resourceId) { String accountNumber =
	 * "", statusCode = "", responseMessage = ""; try { provisioningStub = new
	 * ProvisioningStub(); AddaccountE addaccount = new AddaccountE();
	 * Addaccount newAddaccount = new Addaccount();
	 * newAddaccount.setAccounttype(accountType);
	 * newAddaccount.setCurrency(currency);
	 * newAddaccount.setProfilename(profileName);
	 * newAddaccount.setResourceid(resourceId);
	 * addaccount.setAddaccount(newAddaccount); AddaccountResponseE response =
	 * provisioningStub .addaccount(addaccount); Addaccountrequestresponse
	 * finalResponse = response .getAddaccountResponse().get_return();
	 * accountNumber = finalResponse.getAccountnumber(); StatusCode statusC =
	 * finalResponse.getStatuscode(); statusCode = statusC.getValue();
	 * responseMessage = finalResponse.getResponsemessage();
	 * 
	 * } catch (AxisFault e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (RemoteException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } }
	 */
}
