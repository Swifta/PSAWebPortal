package com.swifta.mats.web.utils;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis2.AxisFault;

import com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub;
import com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.Adjustaccount;
import com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.AdjustaccountE;
import com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.AdjustaccountresponseE;
import com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.Adjustaccounttransactionparameters;
import com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.AdjustmentType;
import com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.Floattransfer;
import com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.FloattransferE;
import com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.FloattransferresponseE;

public class TransactionsService {

	FinancialsStub financial;

	public FloattransferresponseE Floattransfer(String OriginatingResourceid,
			String DestinationResourceid, String ReceivingDesc, String Amount,
			String SendingDesc) throws RemoteException, AxisFault {

		financial = new FinancialsStub();

		Floattransfer floattransfer = new Floattransfer();

		floattransfer.setOrginatingresourceid(OriginatingResourceid);
		floattransfer.setDestinationresourceid(DestinationResourceid);
		floattransfer.setAmount(Amount);
		floattransfer.setReceivingdescription(ReceivingDesc);
		floattransfer.setSendingdescription(SendingDesc);

		FloattransferE floattransfer6 = new FloattransferE();
		floattransfer6.setFloattransfer(floattransfer);

		FloattransferresponseE resp = financial.floattransfer(floattransfer6)
				.getFloattransferResponse().get_return();
		return resp;

	}

	public void TransfertoBank() {

	}

	public AdjustaccountresponseE AdjustAccount(AdjustmentType adjustmentType,
			String DescResourcedesc, String DescResourceID,
			String OriginatingResourceID, String OriginatingResourceDesc)
			throws RemoteException, AxisFault {
		financial = new FinancialsStub();
		Date newFieldValue = new Date();
		Calendar cal = utils.DateToCalendar(newFieldValue);

		AdjustaccountE adjustaccount4 = new AdjustaccountE();
		Adjustaccount adjustParam = new Adjustaccount();
		Adjustaccounttransactionparameters param = new Adjustaccounttransactionparameters();

		param.setAdjusttype(adjustmentType);
		param.setDateoftransaction(cal);
		param.setDestinationresourcedescription(DescResourcedesc);
		param.setDestinationresourceid(DescResourceID);
		param.setOrginatingresourceid(OriginatingResourceID);
		param.setOriginatingresourcedescription(OriginatingResourceDesc);

		adjustParam.setAdjustaccountinstruction(param);
		adjustaccount4.setAdjustaccount(adjustParam);

		AdjustaccountresponseE response = financial
				.adjustaccount(adjustaccount4).getAdjustaccountResponse()
				.get_return();

		return response;
	}

}
