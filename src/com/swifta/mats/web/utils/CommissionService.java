package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import org.apache.axis2.AxisFault;

import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.StatusCode;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceCommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceFees;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceFeesInterfaceChoice_type0;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Servicefeeandcomissionrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setupservicefeesandcommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupservicefeesandcommissionE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupservicefeesandcommissionResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupservicefeesandcommissionResponseE;

public class CommissionService {
	private ProvisioningStub provisioningStub;
	private static final Logger logger = Logger
			.getLogger(CommissionService.class.getName());

	public boolean setFeesAndCommission(String mmoId, int transactionTypeId,
			ServiceCommission[] serviceCommissionArray,
			ServiceFees[] serviceFeesArray) {
		boolean status = false;
		String serviceFeeModel = "TIERED";
		String serviceFeeCondition = "TRANSACTIONTYPE";
		logger.info("--------------------------Inside Fees and Commission");
		SetupservicefeesandcommissionE setupservicefeesandcommissionE = new SetupservicefeesandcommissionE();
		Setupservicefeesandcommission setupservicefeesandcommission = new Setupservicefeesandcommission();
		setupservicefeesandcommissionE
				.setSetupservicefeesandcommission(setupservicefeesandcommission);
		logger.info("--------------------------After setting setup fees and commission"
				+ setupservicefeesandcommission);
		ServiceCommission newServiceCommission = new ServiceCommission();

		for (int i = 0; i < serviceCommissionArray.length; i++) {
			logger.info("--------------------------Iterating commission:::"
					+ serviceCommissionArray[i].getCommissionfee());
			logger.info("--------------------------Iterating commission:::"
					+ serviceCommissionArray[i].getCommissionfeetype());
			logger.info("--------------------------Iterating commission:::"
					+ serviceCommissionArray[i].getMaximumamount());
			logger.info("--------------------------Iterating commission:::"
					+ serviceCommissionArray[i].getMinimumamount());
			logger.info("--------------------------Iterating commission:::"
					+ serviceCommissionArray[i].getServicecommissioncondition());
			logger.info("--------------------------Iterating commission:::"
					+ serviceCommissionArray[i].getServicecommissionmodeltype());
			// either cashin cashout ID
			logger.info("--------------------------Iterating commission:::"
					+ serviceCommissionArray[i].getTransactiontypeid());
		}

		setupservicefeesandcommission
				.setServicecommissiondetails(serviceCommissionArray);
		logger.info("--------------------------After setting commission array "
				+ serviceCommissionArray);
		logger.info("--------------------------After setting commission array size is"
				+ serviceCommissionArray.length);
		for (int i = 0; i < serviceFeesArray.length; i++) {
			ServiceFeesInterfaceChoice_type0 feeType = new ServiceFeesInterfaceChoice_type0();

			ServiceFeesInterfaceChoice_type0[] feeTypeArray = new ServiceFeesInterfaceChoice_type0[1];
			feeTypeArray[0] = feeType;
			logger.info("--------------------------Iterating service fee:::"
					+ serviceFeesArray[i].getMaximumamount());
			logger.info("--------------------------Iterating commission:::"
					+ serviceFeesArray[i].getMinimumamount()); //
			logger.info("--------------------------Iterating service fee:::"
					+ serviceFeesArray[i].getServicefee());
			feeTypeArray = serviceFeesArray[i]
					.getServiceFeesInterfaceChoice_type0();
			for (int j = 0; j < feeTypeArray.length; j++) {
				logger.info("--------------------------Iterating service fee type:::"
						+ feeTypeArray[j].getMaximumamount());
				logger.info("--------------------------Iterating service fee type:::"
						+ feeTypeArray[j].getMinimumamount());
				logger.info("--------------------------Iterating service fee type:::"
						+ feeTypeArray[j].getServicefee());
				logger.info("--------------------------Iterating service fee type:::"
						+ feeTypeArray[j].getTransactiontypeid());
				logger.info("--------------------------Iterating service fee type:::"
						+ feeTypeArray[j].getServicefeetype());
			}

			logger.info("--------------------------Iterating service fee:::"
					+ serviceFeesArray[i].getServicefeetype());
			logger.info("--------------------------Iterating service fee:::"
					+ serviceFeesArray[i].getTransactiontypeid());

		}

		setupservicefeesandcommission
				.setServicefeecondition(serviceFeeCondition);
		logger.info("--------------------------After setting service fee condition "
				+ serviceFeeCondition);
		setupservicefeesandcommission.setServicefeedetails(serviceFeesArray);
		logger.info("--------------------------After setting service fees array "
				+ serviceFeesArray);
		logger.info("--------------------------After setting service fees array size "
				+ serviceFeesArray.length);
		setupservicefeesandcommission.setServicefeemodel(serviceFeeModel);
		logger.info("--------------------------After service fee model "
				+ serviceFeeModel);
		setupservicefeesandcommission.setSpaccountholderid(mmoId);
		logger.info("--------------------------After setting mmoId " + mmoId);

		SetupservicefeesandcommissionResponseE feesAndCommissionResponseE = new SetupservicefeesandcommissionResponseE();
		try {
			provisioningStub = new ProvisioningStub();
			feesAndCommissionResponseE = provisioningStub
					.setupservicefeesandcommission(setupservicefeesandcommissionE);
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (feesAndCommissionResponseE != null) {
			logger.info("--------------------------feesAndCommissionResponseE is not null");
			SetupservicefeesandcommissionResponse setupservicefeesandcommissionResponse = feesAndCommissionResponseE
					.getSetupservicefeesandcommissionResponse();
			if (setupservicefeesandcommissionResponse != null) {
				logger.info("--------------------------setupservicefeesandcommissionResponse is not null");
				Servicefeeandcomissionrequestresponse response = setupservicefeesandcommissionResponse
						.get_return();
				if (response != null) {
					logger.info("--------------------------response is not null");
					if (response.getStatuscode().equals(StatusCode.COMPLETED)) {
						status = true;
					}
				} else {
					logger.info("--------------------------response is null");

				}
			} else {
				logger.info("--------------------------setupservicefeesandcommissionResponse is null");
			}

		} else {
			logger.info("--------------------------feesAndCommissionResponseE is null");
		}

		return status;
	}
}
