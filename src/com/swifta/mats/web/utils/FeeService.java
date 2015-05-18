package com.swifta.mats.web.utils;

import org.apache.axis2.addressing.EndpointReference;

import com.swifta.mats.web.MatsWebPortalUI;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Deletefeerequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Deleteservicefees;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeleteservicefeesE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeleteservicefeesResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeleteservicefeesResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Editfeerequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Editservicefees;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditservicefeesE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditservicefeesResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditservicefeesResponseE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceFees;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Servicefeerequestresponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.Setupservicefees;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupservicefeesE;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupservicefeesResponse;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SetupservicefeesResponseE;
import com.swifta.sub.mats.reporting.MatsreportingserviceStub;
import com.vaadin.ui.UI;

public class FeeService {

	final static String esbendpoint = MatsWebPortalUI.conf.ESB;
	final static String loggedInUser = UI.getCurrent().getSession()
			.getAttribute("user").toString();

	static ProvisioningStub matsStub;
	static MatsreportingserviceStub matsReportstub;

	org.apache.axis2.client.ServiceClient _serviceClient = null;

	private static EndpointReference targetEPR = new EndpointReference(
			esbendpoint);

	public static String setupServicefee(String loggedInUser,
			Integer Serviceconfigtype, ServiceFees[] servicefess,
			Integer transactiontypeid, Integer accountholderid)
			throws Exception {
		matsStub = new ProvisioningStub(esbendpoint);

		String statusMessage = "";

		Setupservicefees setupservicefees = new Setupservicefees();
		setupservicefees.setLoggedinUser(loggedInUser);
		setupservicefees.setServiceconfigtype(Serviceconfigtype);
		setupservicefees.setServicefeedetails(servicefess);
		setupservicefees.setTransactiontypeid(transactiontypeid);
		setupservicefees.setUserresourceid(accountholderid);

		SetupservicefeesE setupservicefeesE = new SetupservicefeesE();
		setupservicefeesE.setSetupservicefees(setupservicefees);

		SetupservicefeesResponseE response = matsStub
				.setupservicefees(setupservicefeesE);

		if (response != null) {
			SetupservicefeesResponse response2 = response
					.getSetupservicefeesResponse();
			if (response2 != null) {
				Servicefeerequestresponse response3 = response2.get_return();
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

	public static String editServicefee(String loggedInUser,
			Integer Serviceconfigtype, ServiceFees[] servicefess,
			Integer transactiontypeid, Integer accountholderid)
			throws Exception {
		matsStub = new ProvisioningStub(esbendpoint);

		String statusMessage = "";

		Editservicefees editservicefees = new Editservicefees();
		editservicefees.setLoggedinUser(loggedInUser);
		editservicefees.setServiceconfigtype(Serviceconfigtype);
		editservicefees.setServicefeedetails(servicefess);
		editservicefees.setTransactiontypeid(transactiontypeid);
		editservicefees.setUserresourceid(accountholderid);

		EditservicefeesE editservicefeesE = new EditservicefeesE();
		editservicefeesE.setEditservicefees(editservicefees);

		EditservicefeesResponseE response = matsStub
				.editservicefees(editservicefeesE);

		if (response != null) {
			EditservicefeesResponse response2 = response
					.getEditservicefeesResponse();
			if (response2 != null) {
				Editfeerequestresponse response3 = response2.get_return();
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

	public static String deleteServicefee(String loggedInUser,
			Integer transactiontypeid, Integer accountholderid)
			throws Exception {
		matsStub = new ProvisioningStub(esbendpoint);

		String statusMessage = "";

		Deleteservicefees deleteservicefees = new Deleteservicefees();
		deleteservicefees.setLoggedinUser(loggedInUser);
		deleteservicefees.setTransactiontypeid(transactiontypeid);
		deleteservicefees.setUserresourceid(accountholderid);

		DeleteservicefeesE deleteservicefeesE = new DeleteservicefeesE();
		deleteservicefeesE.setDeleteservicefees(deleteservicefees);

		DeleteservicefeesResponseE response = matsStub
				.deleteservicefees(deleteservicefeesE);

		if (response != null) {
			DeleteservicefeesResponse response2 = response
					.getDeleteservicefeesResponse();
			if (response2 != null) {
				Deletefeerequestresponse response3 = response2.get_return();
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
