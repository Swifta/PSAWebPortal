package com.swifta.mats.web.transactions;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.swifta.mats.web.utils.TransactionsService;
import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.AdjustmentType;
import com.swifta.sub.mats.operation.financial.v1_0.FinancialsStub.StatusCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class Adjustment {

	public FormLayout AddAdjustmentPanel() {
		final FormLayout adjustPan = new FormLayout();
		Label lab = new Label("Adjustment");
		Button adjust = new Button("Adjust");
		TextField text1 = new TextField("Originating Resource ID");
		TextArea mess = new TextArea("Originating Resource Description");
		TextField text2 = new TextField("Destination Resource ID");
		TextField text3 = new TextField("Amount");
		TextField text4 = new TextField("Destination Resource ID");
		TextField text5 = new TextField("Account");
		ComboBox type = new ComboBox("Type");
		type.addItem("ADJUST");
		type.addItem("ADJUST_FEE");
		type.addItem("ADJUST_COMMISSION");
		type.setNullSelectionAllowed(false);
		type.setTextInputAllowed(false);
		type.setInputPrompt("Select");

		adjustPan.setMargin(true);
		adjustPan.addComponent(lab);
		adjustPan.addComponent(text1);
		adjustPan.addComponent(text2);
		adjustPan.addComponent(type);
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
					AdjustmentType adjustmentType = null;
					String DescResourcedesc = null;
					String DescResourceID = null;
					String OriginatingResourceID = null;
					String OriginatingResourceDesc = null;
					String Amount = null;
					StatusCode ret = flow.AdjustAccount(adjustmentType,
							DescResourcedesc, DescResourceID,
							OriginatingResourceID, Amount,
							OriginatingResourceDesc).getStatusCode();

					// Label rep = new Label((String)ret);
					// adjustPan.addComponent(rep, 0);
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

}
