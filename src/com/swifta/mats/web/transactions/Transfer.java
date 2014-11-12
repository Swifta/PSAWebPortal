package com.swifta.mats.web.transactions;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.utils.TransactionsService;
import com.swifta.mats.web.utils.UserManagementService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class Transfer {

	Window subwindow;

	public FormLayout AddTransferPanel() {
		final FormLayout adjustPan = new FormLayout();
		Label lab = new Label("Float Transfer from Dealer to Bank Account");
		Button adjust = new Button("Transfer Float to Bank Account");
		TextField text1 = new TextField("Originating Resource ID");
		TextArea mess = new TextArea("Message");
		TextField text2 = new TextField("Resource Account");
		TextField text3 = new TextField("Amount");
		TextField text4 = new TextField("Bank Code");
		TextField text5 = new TextField("Bank Account Number");
		// ComboBox type = new ComboBox("Type");

		adjustPan.setMargin(true);
		adjustPan.addComponent(lab);
		adjustPan.addComponent(text1);
		adjustPan.addComponent(text2);
		// adjustPan.addComponent(type);
		adjustPan.addComponent(text3);
		adjustPan.addComponent(text4);
		adjustPan.addComponent(text5);
		adjustPan.addComponent(mess);
		adjustPan.addComponent(adjust);

		adjust.addClickListener(new Button.ClickListener() {

			/**
			 * This cancel button is bulk upload
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				TransactionsService flow = new TransactionsService();

				try {
					String resp = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getOrginatingpartnerbalanceafter();
					String resp2 = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getOrginatingpartnerfee();
					String resp3 = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getTransactionid();

					Label rep = new Label(resp + resp2 + resp3);
					adjustPan.addComponent(rep);
				} catch (AxisFault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Can't connect to Server ");
					adjustPan.addComponent(
							new Label("Can't connect to Server"), 0);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Server is currently not available");
					adjustPan.addComponent(new Label(
							"Server is currently not available"), 0);
				}

				// TODO Auto-generated method stub

			}

		});

		return adjustPan;

	}

	public FormLayout AddTransferPanel2() {
		final FormLayout adjustPan = new FormLayout();
		Label lab = new Label("Float Transfer from Agent to Dealer");
		Button adjust = new Button("Transfer Float");
		TextField text1 = new TextField("Resource UserID");
		TextArea mess = new TextArea("Message");
		TextField text2 = new TextField("Resource AccountID");
		TextField text3 = new TextField("Amount");
		TextField text4 = new TextField("Destination AccountID");
		Button activate = new Button("Activate");
		// TextField text5 = new TextField("Bank Account Number");
		// ComboBox type = new ComboBox("Type");

		adjustPan.setMargin(true);
		adjustPan.addComponent(lab);
		adjustPan.addComponent(text1);
		adjustPan.addComponent(text2);
		// adjustPan.addComponent(type);
		adjustPan.addComponent(text3);
		adjustPan.addComponent(text4);
		// adjustPan.addComponent(text5);
		adjustPan.addComponent(mess);
		adjustPan.addComponent(adjust);
		adjustPan.addComponent(activate);

		activate.addClickListener(new Button.ClickListener() {

			/*
			 * This cancel button is bulk upload
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserManagementService register = new UserManagementService();
				try {
					String bankdomainid = "145";
					String currency = "6357";
					String resourceid = "Kachiolay";
					String SecurityAns = "How r u";
					String firstPin = "57899";
					String confirmPin = "57899";
					String IDnumber = "6589856456";
					String ret = register.activateUser(bankdomainid, currency,
							IDnumber, resourceid, SecurityAns, firstPin,
							confirmPin);

					Label rep;
					rep = new Label(ret);
					adjustPan.addComponent(rep, 0);
					Notification.show(ret);

				} catch (RemoteException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// TODO Auto-generated method stub

		});

		adjust.addClickListener(new Button.ClickListener() {

			/**
			 * This cancel button is bulk upload
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				TransactionsService flow = new TransactionsService();

				try {
					String resp = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getOrginatingpartnerbalanceafter();
					String resp2 = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getOrginatingpartnerfee();
					String resp3 = flow.Floattransfer("1", "12", "tegy", "355",
							"ghdgyu").getTransactionid();

					Label rep = new Label(resp + resp2 + resp3);
					adjustPan.addComponent(rep);
				} catch (AxisFault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Can't connect to Server1 ");
					adjustPan.addComponent(
							new Label("Can't connect to Server"), 0);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show("Server is currently not available");
					adjustPan.addComponent(new Label(
							"Server is currently not available"), 0);
				}

				// TODO Auto-generated method stub

			}

		});

		return adjustPan;

	}

}
