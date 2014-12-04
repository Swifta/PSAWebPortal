package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import org.apache.axis2.AxisFault;

import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setupservicefeesandcommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupservicefeesandcommissionE;

public class CommissionService {
	private ProvisioningStub provisioningStub;
	private static final Logger logger = Logger
			.getLogger(CommissionService.class.getName());

	public boolean setFeesAndCommission(String mmoId) {
		boolean status = false;
		String serviceFeeModel = "TIERED";
		String serviceFeeCondition = "TRANSACTIONTYPE";
		logger.info("--------------------------Inside Fees and Commission");
		SetupservicefeesandcommissionE setupservicefeesandcommissionE = new SetupservicefeesandcommissionE();
		Setupservicefeesandcommission setupservicefeesandcommission = new Setupservicefeesandcommission();
		// setupservicefeesandcommissionE.setSetupservicefeesandcommission(serviceFeeDetails);
		// setupservicefeesandcommission.setServicecommissiondetails(serviceCommissionDetails);
		setupservicefeesandcommission
				.setServicefeecondition(serviceFeeCondition);
		// setupservicefeesandcommission.setServicefeedetails(param);
		setupservicefeesandcommission.setServicefeemodel(serviceFeeModel);
		setupservicefeesandcommission.setSpaccountholderid(mmoId);
		try {
			provisioningStub = new ProvisioningStub();
			provisioningStub
					.setupservicefeesandcommission(setupservicefeesandcommissionE);
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status;
	}
}
