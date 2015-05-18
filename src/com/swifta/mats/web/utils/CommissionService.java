package com.swifta.mats.web.utils;

import java.math.BigDecimal;

import org.apache.axis2.addressing.EndpointReference;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Deletecommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeletecommissionE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeletecommissionResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeletecommissionResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Deletecommissionrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Editcommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditcommissionE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditcommissionResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditcommissionResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Editcommissionrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceCommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Servicecommissionrequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setupcommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupcommissionE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupcommissionResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupcommissionResponseE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub;
import com.vaadin.ui.UI;

public class CommissionService {

	final static String esbendpoint = MatsWebPortalUI.conf.ESB;
	final static String loggedInUser = UI.getCurrent().getSession()
			.getAttribute("user").toString();

	static ProvisioningStub matsStub;
	static MatsreportingserviceStub matsReportstub;

	org.apache.axis2.client.ServiceClient _serviceClient = null;

	private static EndpointReference targetEPR = new EndpointReference(
			esbendpoint);

	public static String setupCommission(String loggedInUser,
			BigDecimal commissionfeefortrnxfee, Integer commissionsetuptype,
			ServiceCommission[] servicecommission,
			Integer servicefeepropertiesid, Integer transactiontypeid,
			Integer accountholderid) throws Exception {

		matsStub = new ProvisioningStub(esbendpoint);

		String statusMessage = "";

		Setupcommission setupcommission = new Setupcommission();
		setupcommission.setCommissionfeefortrnxfee(commissionfeefortrnxfee);
		setupcommission.setCommissionsetuptype(commissionsetuptype);
		setupcommission.setLoggedinUser(loggedInUser);
		setupcommission.setServicecommissiondetails(servicecommission);
		setupcommission.setServicefeepropertiesid(servicefeepropertiesid);
		setupcommission.setTransactiontypeid(transactiontypeid);
		setupcommission.setUserresourceid(accountholderid);

		SetupcommissionE setupcommissionE = new SetupcommissionE();
		setupcommissionE.setSetupcommission(setupcommission);

		SetupcommissionResponseE response = matsStub
				.setupcommission(setupcommissionE);

		if (response != null) {
			SetupcommissionResponse response2 = response
					.getSetupcommissionResponse();
			if (response2 != null) {
				Servicecommissionrequestresponse response3 = response2
						.get_return();
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

	public static String editCommission(String loggedInUser,
			BigDecimal commissionfeefortrnxfee, Integer commissionsetuptype,
			ServiceCommission[] servicecommission,
			Integer servicefeepropertiesid, Integer transactiontypeid,
			Integer accountholderid) throws Exception {
		matsStub = new ProvisioningStub(esbendpoint);

		String statusMessage = "";

		Editcommission editcommission = new Editcommission();
		editcommission.setCommissionfeefortrnxfee(commissionfeefortrnxfee);
		editcommission.setCommissionsetuptype(commissionsetuptype);
		editcommission.setLoggedinUser(loggedInUser);
		editcommission.setServicecommissiondetails(servicecommission);
		editcommission.setServicefeepropertiesid(servicefeepropertiesid);
		editcommission.setTransactiontypeid(transactiontypeid);
		editcommission.setUserresourceid(accountholderid);

		EditcommissionE editcommissionE = new EditcommissionE();
		editcommissionE.setEditcommission(editcommission);

		EditcommissionResponseE response = matsStub
				.editcommission(editcommissionE);

		if (response != null) {
			EditcommissionResponse response2 = response
					.getEditcommissionResponse();
			if (response2 != null) {
				Editcommissionrequestresponse response3 = response2
						.get_return();
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

	public static String deleteCommission(String loggedInUser,
			Integer transactiontypeid, Integer accountholderid,
			Integer servicefeepropertiesid) throws Exception {
		matsStub = new ProvisioningStub(esbendpoint);

		String statusMessage = "";

		Deletecommission deletecommission = new Deletecommission();
		deletecommission.setLoggedinUser(loggedInUser);
		deletecommission.setTransactiontypeid(transactiontypeid);
		deletecommission.setUserresourceid(accountholderid);
		deletecommission.setServicefeepropertiesid(servicefeepropertiesid);

		DeletecommissionE deletecommissionE = new DeletecommissionE();
		deletecommissionE.setDeletecommission(deletecommission);

		DeletecommissionResponseE response = matsStub
				.deletecommission(deletecommissionE);

		if (response != null) {
			DeletecommissionResponse response2 = response
					.getDeletecommissionResponse();
			if (response2 != null) {
				Deletecommissionrequestresponse response3 = response2
						.get_return();
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

}
