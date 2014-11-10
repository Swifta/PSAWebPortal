package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis2.AxisFault;

import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub;
import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.Adjustaccountrequest;
import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.AdjustaccountrequestE;
import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.Adjustaccountresponse;
import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.Adjustaccounttransactionparameters;
import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.AdjustmentType;
import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.Floattransferrequest;
import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.FloattransferrequestE;
import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.Floattransferresponse;

public class TransactionsService {

	FinancialsStub financial;

	public Floattransferresponse Floattransfer(String OriginatingResourceid,
			String DestinationResourceid, String ReceivingDesc, String Amount,
			String SendingDesc) throws RemoteException, AxisFault {

		financial = new FinancialsStub();

		Floattransferrequest floattransfer = new Floattransferrequest();

		floattransfer.setOrginatingresourceid(OriginatingResourceid);
		floattransfer.setDestinationresourceid(DestinationResourceid);
		floattransfer.setAmount(Amount);
		floattransfer.setReceivingdescription(ReceivingDesc);
		floattransfer.setSendingdescription(SendingDesc);

		FloattransferrequestE floattransfer6 = new FloattransferrequestE();
		floattransfer6.setFloattransferrequest(floattransfer);

		Floattransferresponse resp = financial
				.floattransferrequest(floattransfer6)
				.getFloattransferrequestResponse().get_return();
		return resp;

	}

	public void TransfertoBank() {

	}

	public Adjustaccountresponse AdjustAccount(AdjustmentType adjustmentType,
			String DescResourcedesc, String DescResourceID,
			String OriginatingResourceID, String OriginatingResourceDesc,
			String Amount) throws RemoteException, AxisFault {
		financial = new FinancialsStub();
		Date newFieldValue = new Date();
		Calendar cal = utils.DateToCalendar(newFieldValue);

		AdjustaccountrequestE adjustaccount4 = new AdjustaccountrequestE();
		Adjustaccountrequest adjustParam = new Adjustaccountrequest();
		Adjustaccounttransactionparameters param = new Adjustaccounttransactionparameters();

		param.setAdjusttype(adjustmentType);
		param.setDateoftransaction(cal);
		param.setDestinationresourcedescription(DescResourcedesc);
		param.setDestinationresourceid(DescResourceID);
		param.setOrginatingresourceid(OriginatingResourceID);
		param.setOriginatingresourcedescription(OriginatingResourceDesc);
		param.setAmount(Amount);

		adjustParam.setAdjustaccountinstruction(param);
		adjustaccount4.setAdjustaccountrequest(adjustParam);

		Adjustaccountresponse response = financial
				.adjustaccountrequest(adjustaccount4)
				.getAdjustaccountrequestResponse().get_return();

		return response;
	}

}
