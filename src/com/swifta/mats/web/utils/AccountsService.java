package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import org.apache.axis2.AxisFault;

import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Addaccount;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddaccountE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Addaccountrequestresponse;

public class AccountsService {
	private static final Logger logger = Logger.getLogger(AccountsService.class
			.getName());
	ProvisioningStub provision;

	@SuppressWarnings("null")
	public Addaccountrequestresponse CreateAccount(String profileID,
			String referenceProfile, String accountType)
			throws RemoteException, AxisFault {
		provision = new ProvisioningStub();
		AddaccountE addaccount64 = new AddaccountE();
		Addaccount parameter = null;
		parameter.setAccountprofileid(profileID);
		parameter.setAccountreferenceprofile(referenceProfile);
		parameter.setAccounttype(accountType);

		addaccount64.setAddaccount(parameter);

		Addaccountrequestresponse response = provision.addaccount(addaccount64)
				.getAddaccountResponse().get_return();
		return response;

	}
}
