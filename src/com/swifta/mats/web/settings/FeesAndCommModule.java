package com.swifta.mats.web.settings;

import java.util.ArrayList;

import com.swifta.mats.web.usermanagement.UserDetailsModule;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
//import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FeesAndCommModule {
	UserDetailsModule udm;
	HorizontalLayout udc;
	private ArrayList<VerticalLayout> cArrLItemContent;

	private Item row;
	private Property<String> min;
	private Property<String> max;
	private FieldGroup fg;
	private ArrayList<TextField> arrLMin;
	private ArrayList<TextField> arrLMax;
	private static int rowCount = -1;

	public FeesAndCommModule() {
		udm = new UserDetailsModule();
		cArrLItemContent = new ArrayList<>(4);
		arrLMin = new ArrayList<>();
		arrLMax = new ArrayList<>();

	}

	private void add(ArrayList<VerticalLayout> cArrLItemContent, String attrName) {
		rowCount++;
		// Notification.show(String.valueOf(rowCount));
		for (int i = 0; i < cArrLItemContent.size(); i++) {
			VerticalLayout cItemContent = cArrLItemContent.get(i);
			if (i == 2) {
				ComboBox comboAttrVal = new ComboBox();
				comboAttrVal.addItem(1);
				comboAttrVal.setItemCaption(1, "%ge");
				comboAttrVal.addItem(2);
				comboAttrVal.setItemCaption(2, "Fixed");
				comboAttrVal.select(2);
				cItemContent.addComponent(comboAttrVal);
				continue;
			}

			TextField tFAttrVal = new TextField();
			tFAttrVal.setId(String.valueOf(rowCount));
			cItemContent.addComponent(tFAttrVal);
			if (i == 0) {
				arrLMin.add(tFAttrVal);
			} else if (i == 1) {
				arrLMax.add(tFAttrVal);
			}
		}
	}

	private void remove(ArrayList<VerticalLayout> cArrLItemContent,
			String attrName) {
		for (int i = 0; i < cArrLItemContent.size(); i++) {
			VerticalLayout cItemContent = cArrLItemContent.get(i);
			cItemContent.removeComponent(cItemContent.getComponent(cItemContent
					.getComponentCount() - 1));
		}
	}

	public HorizontalLayout getFeesContainer(String type) {

		HorizontalLayout cManage = new HorizontalLayout();
		cManage.setWidthUndefined();
		cManage.setWidthUndefined();

		VerticalLayout cChoose = new VerticalLayout();
		cChoose.setSizeUndefined();
		cChoose.setStyleName("frm_add_user frm_manage_commission");

		VerticalLayout addUserHeader = new VerticalLayout();
		addUserHeader.setWidthUndefined();
		addUserHeader.setHeightUndefined();
		addUserHeader.setSpacing(true);

		cChoose.addComponent(addUserHeader);

		Label lbAddUser = new Label("Manage " + type);
		lbAddUser.setStyleName("label_add_user");
		lbAddUser.setSizeUndefined();

		cChoose.addComponent(lbAddUser);
		Label lbChoose = new Label("Please choose...");

		final ComboBox comboOp = new ComboBox("Operator");
		comboOp.addItem(1);
		comboOp.setItemCaption(1, "Teasy");
		comboOp.addItem(2);
		comboOp.setItemCaption(2, "Pocket Money");

		final ComboBox comboTxType = new ComboBox("Transaction Type");

		if (type.equals("fees")) {
			comboTxType.addItem(1);
			comboTxType.setItemCaption(1, "Send money to registered user");
			comboTxType.addItem(2);
			comboTxType.setItemCaption(2, "Send Modney to unregistered user");
			comboTxType.addItem(3);
			comboTxType.setItemCaption(3, "Withdraw Money from Agent");
			comboTxType.select(1);
		} else {
			comboTxType.addItem(1);
			comboTxType.setItemCaption(1, "Cash-in");
			comboTxType.addItem(2);
			comboTxType.setItemCaption(2, "Cash-out");
			comboTxType.addItem(3);
			comboTxType.setItemCaption(3, "Airtime topup");
			comboTxType.addItem(4);
			comboTxType.setItemCaption(4, "Money Transfer");
			comboTxType.addItem(5);
			comboTxType.setItemCaption(5, "Bill Payment");
			comboTxType.select(1);

		}

		cChoose.addComponent(lbChoose);
		cChoose.addComponent(comboOp);

		cChoose.addComponent(comboTxType);
		cChoose.setStyleName("c_choose");

		// Button btnSave = new Button("Save");
		// btnSave.setWidth("100%");
		// btnSave.setIcon(FontAwesome.SAVE);

		VerticalLayout cAttr = new VerticalLayout();
		cAttr.setSpacing(true);
		cAttr.setMargin(new MarginInfo(false, false, true, false));
		cAttr.setStyleName("c_attr");

		final Label tFOp = new Label();
		tFOp.setCaption("Selected Operator: ");
		tFOp.setValue("None");
		tFOp.setStyleName("label_add_user");

		FormLayout cOp = new FormLayout();
		cOp.addComponent(tFOp);
		cAttr.addComponent(cOp);

		HorizontalLayout cAttrItem = new HorizontalLayout();
		VerticalLayout cItemContent = new VerticalLayout();
		Label lbAttr = new Label(comboTxType.getCaption());
		lbAttr.setSizeUndefined();
		cItemContent.setSizeFull();

		lbAttr.setStyleName("label_add_user attr");
		final Label lbAttrVal = new Label(
				comboTxType.getItemCaption(comboTxType.getValue()));

		HorizontalLayout cAttrVal = new HorizontalLayout();
		cAttrVal.addComponent(lbAttrVal);
		lbAttrVal.setStyleName("attr_val");
		cAttrVal.setStyleName("c_attr_val");
		cItemContent.addComponent(lbAttr);
		cItemContent.addComponent(cAttrVal);
		cItemContent.setComponentAlignment(cAttrVal, Alignment.MIDDLE_CENTER);
		cItemContent.setComponentAlignment(lbAttr, Alignment.TOP_LEFT);
		cAttrItem.addComponent(cItemContent);
		cAttr.addComponent(cAttrItem);

		VerticalLayout cItemContentMin = new VerticalLayout();
		cArrLItemContent.add(cItemContentMin);
		lbAttr = new Label("Min.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		final TextField tFAttrValMin = new TextField();
		tFAttrValMin.setValue("0.0");

		cItemContentMin.addComponent(lbAttr);
		cItemContentMin.addComponent(tFAttrValMin);
		cAttrItem.addComponent(cItemContentMin);

		cItemContent = new VerticalLayout();
		cArrLItemContent.add(cItemContent);
		lbAttr = new Label("Max.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		TextField tFAttrVal = new TextField();

		cItemContent.addComponent(lbAttr);
		cItemContent.addComponent(tFAttrVal);
		cAttrItem.addComponent(cItemContent);

		cItemContent = new VerticalLayout();
		cArrLItemContent.add(cItemContent);
		lbAttr = new Label("Charge Mode.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		ComboBox comboAttrVal = new ComboBox();
		comboAttrVal.addItem(1);
		comboAttrVal.setItemCaption(1, "%ge");
		comboAttrVal.addItem(2);
		comboAttrVal.setItemCaption(2, "Fixed");
		comboAttrVal.select(2);

		cItemContent.addComponent(lbAttr);
		cItemContent.addComponent(comboAttrVal);
		cAttrItem.addComponent(cItemContent);

		cItemContent = new VerticalLayout();
		cArrLItemContent.add(cItemContent);
		lbAttr = new Label("Amount (%/N)");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		tFAttrVal = new TextField();

		cItemContent.addComponent(lbAttr);
		cItemContent.addComponent(tFAttrVal);
		cAttrItem.addComponent(cItemContent);

		row = new PropertysetItem();
		min = new ObjectProperty<String>("");
		max = new ObjectProperty<String>("");
		row.addItemProperty("Min", min);
		row.addItemProperty("Max", max);
		fg = new FieldGroup(row);
		fg.bind(tFAttrValMin, "Min");
		fg.bind(tFAttrValMin, "Max");

		HorizontalLayout cControls = new HorizontalLayout();
		Button btnAdd = new Button("+");
		// btnAdd.setIcon(FontAwesome.PLUS);
		btnAdd.setStyleName("btn_link");
		Button btnRemove = new Button("-");
		btnRemove.setStyleName("btn_link");

		Button btnSave = new Button(FontAwesome.SAVE);
		btnSave.setStyleName("btn_link");

		Button btnCancel = new Button(FontAwesome.UNDO);
		btnCancel.setStyleName("btn_link");

		cControls.addComponent(btnAdd);
		cControls.addComponent(btnRemove);
		cControls.addComponent(btnSave);
		// cControls.addComponent(btn);

		cAttr.addComponent(cAttrItem);
		cAttr.addComponent(cControls);
		cAttr.setComponentAlignment(cControls, Alignment.BOTTOM_RIGHT);
		cManage.addComponent(cChoose);
		cManage.addComponent(cAttr);

		comboTxType.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -4750457498573552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				lbAttrVal.setValue(comboTxType.getItemCaption(comboTxType
						.getValue()));
			}

		});

		comboOp.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -4750457498573552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// tFOp.setReadOnly(false);
				tFOp.setValue(comboOp.getItemCaption(comboOp.getValue()));
				// tFOp.setReadOnly(true);
			}

		});

		btnAdd.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 8105347974986024315L;

			@Override
			public void buttonClick(ClickEvent event) {
				add(cArrLItemContent, "");

			}
		});

		btnRemove.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3921995443687711235L;

			@Override
			public void buttonClick(ClickEvent event) {
				remove(cArrLItemContent, "");

			}
		});

		btnSave.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -7900935606088856031L;

			@Override
			public void buttonClick(ClickEvent event) {

			}
		});

		return cManage;
	}

	public HorizontalLayout getFeesForm(String strTbName, String strID,
			boolean a, boolean b) {
		return null;
	}

	public void apmModifier(String strTbName, HorizontalLayout cContent) {

		cContent.removeAllComponents();
		cContent.addComponent(getFeesContainer(strTbName));

		/*
		 * if (UI.getCurrent().getSession()
		 * .getAttribute(WorkSpaceManageFeesAndComm.SESSION_UDM_IS_LOG) != null)
		 * { strTbName = (String) UI .getCurrent() .getSession() .getAttribute(
		 * WorkSpaceManageFeesAndComm.SESSION_UDM_TABLE_LOG); }
		 * 
		 * if (udc != null) cContent.removeComponent(udc); udc =
		 * udm.getDetailsForm(strTbName, "001", false, false);
		 * cContent.addComponent(udc); return;
		 */

	}

	private void addValidator() {
		for (int i = 0; i < arrLMin.size(); i++) {
			TextField min = arrLMin.get(i);
			TextField max = arrLMax.get(i);
			min.addValidator(new RangeValidator(min));
			max.addValidator(new RangeValidator(max));
		}
	}

	private class RangeValidator implements Validator {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4460365182369687080L;
		private TextField tF;

		RangeValidator(TextField f) {
			this.tF = f;
		}

		@Override
		public void validate(Object obj) throws InvalidValueException {
			Integer id = Integer.valueOf(tF.getId());
			TextField min = arrLMin.get(id);
			TextField max = arrLMax.get(id);
			if (min.getValue().trim() == null)
				return;
			if (max.getValue().trim() == null)
				return;
			Float minv = Float.parseFloat(min.getValue());
			Float maxv = Float.parseFloat(max.getValue());
			if (minv > maxv) {
				throw new InvalidValueException("Invalid Range.");
			}

		}
	}

	private class RangeChangeListener implements TextField.ValueChangeListener {

		private static final long serialVersionUID = -6571345512003629796L;

		@Override
		public void valueChange(ValueChangeEvent event) {
			/*
			 * Float mn = Float.parseFloat(arrLMin.get()); Float mx =
			 * Float.parseFloat(max.getValue()); if (Float.compare(mn, mx) == 1
			 * || Float.compare(mn, mx) == 0) {
			 * Notification.show("Invalid range"); }
			 */
			// Notification.show("Hello.");

		}
	}

}
