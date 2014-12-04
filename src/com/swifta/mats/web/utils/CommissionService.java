package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import org.apache.axis2.AxisFault;

import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceCommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceFees;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setupservicefeesandcommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupservicefeesandcommissionE;
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

		/*
		 * BigDecimal minAmount = new BigDecimal(0.0), maxAmount = new
		 * BigDecimal( 0.0), serviceFee = new BigDecimal(0.0), commissionFee =
		 * new BigDecimal( 0.0); int commissionCount = 4; ServiceCommission[]
		 * serviceCommission = new ServiceCommission[commissionCount];
		 * ServiceCommission newServiceCommission = new ServiceCommission();
		 * 
		 * for (int i = 0; i < commissionCount; i++) {
		 * newServiceCommission.setCommissionfee(commissionFee);
		 * newServiceCommission.setCommissionfeetype("PERCENT");
		 * newServiceCommission.setMaximumamount(minAmount);
		 * newServiceCommission.setMinimumamount(maxAmount);
		 * newServiceCommission.setServicecommissioncondition("FEE");
		 * newServiceCommission.setServicecommissionmodeltype("NOTAPPLICABLE");
		 * // either cashin cashout ID
		 * newServiceCommission.setTransactiontypeid(transactionTypeId);
		 * serviceCommission[i] = newServiceCommission; }
		 */
		setupservicefeesandcommission
				.setServicecommissiondetails(serviceCommissionArray);

		/*
		 * int feeCount = 4; ServiceFees[] serviceFeesArra = new
		 * ServiceFees[feeCount]; ServiceFees serviceFees = new ServiceFees();
		 * for (int i = 0; i < feeCount; i++) { ServiceFeesInterfaceChoice_type0
		 * feeType = new ServiceFeesInterfaceChoice_type0();
		 * 
		 * String serviceFeeType = ServiceFeematrix.PERCENT.toString();
		 * feeType.setMaximumamount(maxAmount);
		 * feeType.setMinimumamount(minAmount);
		 * feeType.setServicefee(serviceFee);
		 * feeType.setTransactiontypeid(transactionTypeId);
		 * feeType.setServicefeetype(ServiceFeematrix.PERCENT);
		 * 
		 * ServiceFeesInterfaceChoice_type0[] feeTypeArray = new
		 * ServiceFeesInterfaceChoice_type0[1]; feeTypeArray[0] = feeType;
		 * serviceFees.setMaximumamount(maxAmount);
		 * serviceFees.setMinimumamount(minAmount); //
		 * serviceFees.setServicefee();
		 * serviceFees.setServiceFeesInterfaceChoice_type0(feeTypeArray);
		 * serviceFees.setServicefeetype(serviceFeeType);
		 * serviceFees.setTransactiontypeid(transactionTypeId);
		 * 
		 * serviceFeesArra[i] = serviceFees; }
		 */
		setupservicefeesandcommission
				.setServicefeecondition(serviceFeeCondition);
		setupservicefeesandcommission.setServicefeedetails(serviceFeesArray);
		setupservicefeesandcommission.setServicefeemodel(serviceFeeModel);
		setupservicefeesandcommission.setSpaccountholderid(mmoId);

		SetupservicefeesandcommissionResponseE feesAndCommissionResponseE = new SetupservicefeesandcommissionResponseE();
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
