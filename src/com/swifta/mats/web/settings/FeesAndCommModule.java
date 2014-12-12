package com.swifta.mats.web.settings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.swifta.mats.web.usermanagement.UserDetailsModule;
import com.swifta.mats.web.utils.CommissionService;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceCommission;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceCommissionConditionTypes;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceCommissionModelTypes;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceFeematrix;
import com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ServiceFees;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.event.ListenerMethod.MethodException;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
//import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class FeesAndCommModule {
	UserDetailsModule udm;
	HorizontalLayout udc;
	private ArrayList<VerticalLayout> cArrLItemContent;
	private ArrayList<FieldGroup> arrLRangeFG;
	private ArrayList<FieldGroup> arrLMatFG;
	private boolean isTiered;
	private static String lookedTab = null;
	private ComboBox comboOp;
	private ComboBox comboTxType;
	private int confCount = 0;
	ArrayList<FieldGroup> arrLDFG;
	private ArrayList<ArrayList<FieldGroup>> arrLAllFG;
	private HashMap<String, ArrayList<ArrayList<FieldGroup>>> hmAllFG;
	public final static String COMMISSION = "Commission";
	public final static String FEES = "Fees";
	public final static String EXISTING = "existing";
	private String tabType = null;
	private String[] arrMatValues;
	private String[] arrModelValues;
	private String[] arrConValues;
	FieldGroup otherfg;
	ArrayList<FieldGroup> prfg;
	ArrayList<FieldGroup> pmfg;

	public FeesAndCommModule() {
		udm = new UserDetailsModule();
		comboOp = new ComboBox("Operator");
		comboOp.addItem(7);
		comboOp.setItemCaption(7, "TeasyMobile");
		comboOp.addItem(4);
		comboOp.setItemCaption(4, "PocketMoni");
		comboOp.select(7);

		comboTxType = new ComboBox("Transaction Type");
		comboTxType.addItem(4);
		comboTxType.setItemCaption(4, "CASHIN");
		comboTxType.addItem(5);
		comboTxType.setItemCaption(5, "CASHOUT");
		comboTxType.addItem(6);
		comboTxType.setItemCaption(6, "PAYMENT SEND");
		comboTxType.addItem(7);
		comboTxType.setItemCaption(7, "PAYMENT RECEIVE");
		comboTxType.addItem(17);
		comboTxType.setItemCaption(17, "3rd PARTY PAYMENT");
		comboTxType.select(4);
		hmAllFG = new HashMap<>();
		arrLDFG = new ArrayList<>();
		arrMatValues = new String[] { "PERCENT", "FIXED" };
		arrModelValues = new String[] { "TIERED", "NOTAPPLICABLE" };
		arrConValues = new String[] { "AMOUNT", "FEE" };

	}

	private void addMatrix() {
		VerticalLayout cMat = cArrLItemContent.get(2);
		VerticalLayout cAmt = cArrLItemContent.get(3);
		final ComboBox comboMat = new ComboBox();
		comboMat.addItem(arrMatValues[0]);
		comboMat.setItemCaption(arrMatValues[0], "%");
		comboMat.addItem(arrMatValues[1]);
		comboMat.setItemCaption(arrMatValues[1], "Fixed");
		comboMat.select(arrMatValues[0]);

		final TextField tfAmt = new TextField();
		cMat.addComponent(comboMat);
		cAmt.addComponent(tfAmt);

		Property<String> pMat = new ObjectProperty<>(arrMatValues[0]);
		Property<String> pAmt = new ObjectProperty<>("");

		Item row = new PropertysetItem();
		row.addItemProperty("Mat", pMat);
		row.addItemProperty("Amt", pAmt);
		FieldGroup fg = new FieldGroup(row);
		fg.bind(comboMat, "Mat");
		fg.bind(tfAmt, "Amt");
		arrLMatFG.add(fg);

		comboMat.addValidator(new MatrixHouseKeeper(comboMat, tfAmt));
		tfAmt.addValidator(new MatrixHouseKeeper(comboMat, tfAmt));
		comboMat.setValidationVisible(true);
		tfAmt.setValidationVisible(true);
		comboMat.setImmediate(true);
		tfAmt.setImmediate(true);

		fg.addCommitHandler(new CommitHandler() {
			private static final long serialVersionUID = -4960371331680442909L;

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {
				tfAmt.setRequired(false);
				comboMat.setRequired(false);
				if (comboMat.getValue() == null) {
					comboMat.setRequired(true);
					Notification.show("Fields marked with (*) are required.");
					throw new CommitException("Field required.");
				} else if (tfAmt.getValue().trim().isEmpty()) {
					tfAmt.setRequired(true);
					Notification.show("Fields marked with (*) are required.");
					throw new CommitException("Field required.");
				} else {
					tfAmt.setRequired(false);
					comboMat.setRequired(false);
					return;
				}

			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {

			}

		});

		comboMat.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -47504574989952155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					comboMat.setComponentError(null);
					tfAmt.setComponentError(null);
					comboMat.validate();
				} catch (MethodException e) {

				}
			}

		});

		tfAmt.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -4750457473552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					tfAmt.setComponentError(null);
					comboMat.setComponentError(null);
					tfAmt.validate();

				} catch (MethodException e) {

				}

			}

		});

	}

	private void addRange() {
		VerticalLayout cMin = cArrLItemContent.get(0);
		VerticalLayout cMax = cArrLItemContent.get(1);
		final TextField tfMin = new TextField();
		final TextField tfMax = new TextField();
		cMin.addComponent(tfMin);
		cMax.addComponent(tfMax);

		Property<String> pMin = new ObjectProperty<>("");
		Property<String> pMax = new ObjectProperty<>("");

		Item row = new PropertysetItem();
		row.addItemProperty("Min", pMin);
		row.addItemProperty("Max", pMax);
		FieldGroup fg = new FieldGroup(row);
		fg.bind(tfMin, "Min");
		fg.bind(tfMax, "Max");
		arrLRangeFG.add(fg);
		int index = arrLRangeFG.size() - 1;
		tfMin.addValidator(new RangeHouseKeeper(tfMin, tfMax, index));
		tfMax.addValidator(new RangeHouseKeeper(tfMin, tfMax, index));
		tfMin.setValidationVisible(true);
		tfMax.setValidationVisible(true);
		tfMin.setImmediate(true);
		tfMax.setImmediate(true);

		if (!isTiered) {
			tfMin.setValue("0.00");
			tfMin.setEnabled(false);
			tfMax.setValue("0.00");
			tfMax.setEnabled(false);
		}

		fg.addCommitHandler(new CommitHandler() {
			private static final long serialVersionUID = -4960371331680442909L;

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {
				tfMin.setRequired(false);
				tfMax.setRequired(false);
				if (tfMin.getValue().trim().isEmpty()) {
					tfMin.setRequired(true);
					Notification.show("Fields marked with (*) are required.");
					throw new CommitException("Field required.");
				}
				if (tfMax.getValue().trim().isEmpty()) {
					tfMax.setRequired(true);
					Notification.show("Fields marked with (*) are required.");
					throw new CommitException("Field required.");
				} else {
					tfMin.setRequired(false);
					tfMax.setRequired(false);
					return;
				}

			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {

			}

		});

		tfMin.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -47504574989952155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					tfMin.setComponentError(null);
					tfMax.setComponentError(null);
					tfMin.validate();
				} catch (MethodException e) {

				}
			}

		});

		tfMax.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -4750457473552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					tfMax.setComponentError(null);
					tfMin.setComponentError(null);
					tfMax.validate();
				} catch (MethodException e) {

				}

			}

		});

	}

	private class RangeHouseKeeper implements Validator {

		private static final long serialVersionUID = 8429528148551522733L;
		private TextField tfMin;
		private TextField tfMax;
		private Float vMin;
		private Float vMax;
		private int fgIndex;

		RangeHouseKeeper(TextField tfMin, TextField tfMax, int fgIndex) {
			this.tfMin = tfMin;
			this.tfMax = tfMax;
			this.fgIndex = fgIndex;

		}

		@Override
		public void validate(Object v) throws InvalidValueException,
				NumberFormatException {

			if (v.toString().trim().isEmpty())
				return;

			try {
				Float.valueOf(v.toString());
			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			String sMin = tfMin.getValue().trim();
			String sMax = tfMax.getValue().trim();

			if (sMin.isEmpty() || sMax.isEmpty())
				return;

			try {
				vMin = Float.parseFloat(sMin);

			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			try {
				vMax = Float.parseFloat(sMax);

			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			if (vMin > vMax) {
				throw new InvalidValueException("Invalid range.");

			}

			if (arrLRangeFG.size() > fgIndex + 1) {
				FieldGroup nxtFG = arrLRangeFG.get(fgIndex + 1);
				TextField nxtMin = (TextField) nxtFG.getField("Min");
				Double nxt = Double.valueOf(tfMax.getValue()) + 1;
				nxtMin.setValue(nxt.toString());
				nxtMin.setEnabled(false);

				return;

			}
		}
	}

	private class MatrixHouseKeeper implements Validator {

		private static final long serialVersionUID = 8429528148551522733L;
		private ComboBox comboMat;
		private TextField tfAmt;
		private String mat;
		private Float vAmt;

		MatrixHouseKeeper(ComboBox comboMat, TextField tfAmt) {
			this.comboMat = comboMat;
			this.tfAmt = tfAmt;
		}

		@Override
		public void validate(Object v) throws InvalidValueException,
				NumberFormatException {

			if (v.toString().trim().isEmpty())
				return;

			if (tfAmt.getValue().trim().isEmpty())
				return;

			try {
				Float.valueOf(tfAmt.getValue());
			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			mat = comboMat.getValue().toString();
			String sAmt = tfAmt.getValue().trim();

			try {
				vAmt = Float.parseFloat(sAmt);

			} catch (NumberFormatException e) {
				throw new InvalidValueException("Only Numbers.");
			}

			if (mat.equals(arrMatValues[0]) && vAmt > 100) {
				throw new InvalidValueException("Invalid Percentage.");
			}

		}
	}

	private void add() {
		addRange();
		addMatrix();
	}

	private void remove() {
		removeRange();
		removeMatrix();
	}

	private void removeAll() {
		removeAllRange();
		removeAllMatrix();
	}

	private void removeAllRange() {
		if (removeRange() == -1) {
			return;
		}
		removeAllRange();
	}

	private void removeAllMatrix() {
		if (removeMatrix() == -1) {
			return;
		}
		removeAllMatrix();
	}

	private int removeRange() {
		if (arrLRangeFG.size() < 2)
			return -1;

		arrLRangeFG.remove(arrLRangeFG.size() - 1);
		VerticalLayout cMin = cArrLItemContent.get(0);
		VerticalLayout cMax = cArrLItemContent.get(1);
		cMin.removeComponent(cMin.getComponent(cMin.getComponentCount() - 1));
		cMax.removeComponent(cMax.getComponent(cMax.getComponentCount() - 1));
		return 1;

	}

	private int removeMatrix() {
		if (arrLMatFG.size() < 2)
			return -1;

		arrLMatFG.remove(arrLMatFG.size() - 1);
		VerticalLayout cMat = cArrLItemContent.get(2);
		VerticalLayout cAmt = cArrLItemContent.get(3);
		cMat.removeComponent(cMat.getComponent(cMat.getComponentCount() - 1));
		cAmt.removeComponent(cAmt.getComponent(cAmt.getComponentCount() - 1));
		return 1;

	}

	public HorizontalLayout getFeesContainer(String type) {

		cArrLItemContent = new ArrayList<>(4);
		arrLRangeFG = new ArrayList<>();
		arrLMatFG = new ArrayList<>();
		arrLAllFG = new ArrayList<>();
		isTiered = true;
		tabType = type;

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

		Label lbAddUser = new Label("Manage " + tabType);
		lbAddUser.setStyleName("label_add_user");
		lbAddUser.setSizeUndefined();

		cChoose.addComponent(lbAddUser);
		Label lbChoose = new Label("Please choose...");

		final ComboBox comboConditionType = new ComboBox("Condition Type");
		comboConditionType.addItem(arrConValues[0]);
		comboConditionType.setItemCaption(arrConValues[0], "Amount");
		comboConditionType.addItem(arrConValues[1]);
		comboConditionType.setItemCaption(arrConValues[1], "Fee");
		comboConditionType.select(arrConValues[0]);

		final ComboBox comboModelType = new ComboBox("Model Type");
		comboModelType.addItem(arrModelValues[0]);
		comboModelType.setItemCaption(arrModelValues[0], "Tiered");
		comboModelType.addItem(arrModelValues[1]);
		comboModelType.setItemCaption(arrModelValues[1], "None");
		comboModelType.select(arrModelValues[0]);

		Item row = new PropertysetItem();

		Property<String> pconTypeID = new ObjectProperty<>(arrConValues[0]);
		Property<String> pmodelTypeID = new ObjectProperty<>(arrModelValues[0]);
		row.addItemProperty("CONID", pconTypeID);
		row.addItemProperty("MODID", pmodelTypeID);

		otherfg = new FieldGroup(row);
		otherfg.bind(comboConditionType, "CONID");
		otherfg.bind(comboModelType, "MODID");

		otherfg.addCommitHandler(new CommitHandler() {
			private static final long serialVersionUID = 6144936023943646696L;

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {
				comboOp.setRequired(false);
				comboConditionType.setRequired(false);
				comboModelType.setRequired(false);
				comboTxType.setRequired(false);
				if (comboOp.getValue() == null) {
					comboOp.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");
				} else if (comboConditionType.getValue() == null) {
					comboConditionType.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");

				} else if (comboModelType.getValue() == null) {
					comboModelType.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");

				} else if (comboTxType.getValue() == null) {
					comboTxType.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");
				} else {
					comboOp.setRequired(false);
					comboConditionType.setRequired(false);
					comboModelType.setRequired(false);
					comboTxType.setRequired(false);
					return;
				}
			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {

			}

		});

		cChoose.addComponent(lbChoose);
		cChoose.addComponent(comboOp);

		cChoose.addComponent(comboConditionType);
		cChoose.addComponent(comboModelType);
		cChoose.addComponent(comboTxType);
		cChoose.setStyleName("c_choose");

		VerticalLayout cAttr = new VerticalLayout();
		cAttr.setSpacing(true);
		cAttr.setMargin(new MarginInfo(false, false, true, false));
		cAttr.setStyleName("c_attr");

		final Label tFOp = new Label();
		tFOp.setCaption("Selected Operator: ");
		tFOp.setValue(comboOp.getItemCaption(comboOp.getValue()));
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

		// MIN

		VerticalLayout cItemContentMin = new VerticalLayout();
		cArrLItemContent.add(cItemContentMin);
		lbAttr = new Label("Min.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMin.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentMin);

		// MAX

		VerticalLayout cItemContentMax = new VerticalLayout();
		cArrLItemContent.add(cItemContentMax);
		lbAttr = new Label("Max.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMax.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentMax);

		// MATRIX
		VerticalLayout cItemContentMat = new VerticalLayout();
		cArrLItemContent.add(cItemContentMat);
		lbAttr = new Label("Matrix");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMat.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentMat);

		// AMOUNT

		VerticalLayout cItemContentAmt = new VerticalLayout();
		cArrLItemContent.add(cItemContentAmt);
		lbAttr = new Label("Amount (%/N)");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentAmt.addComponent(lbAttr);
		cAttrItem.addComponent(cItemContentAmt);
		add();
		// add();

		HorizontalLayout cControls = new HorizontalLayout();
		Button btnAdd = new Button("+");

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
				tFOp.setValue(comboOp.getItemCaption(comboOp.getValue()));
			}

		});

		comboConditionType.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -5878577330071011374L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue().toString().trim()
						.equals(arrConValues[0])) {
					comboModelType.select(arrModelValues[0]);
				} else {
					comboModelType.select(arrModelValues[1]);
				}
			}

		});

		comboModelType.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -6418325605387194047L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (comboModelType.getValue().toString()
						.equals(arrModelValues[0])) {

					if (arrLRangeFG.size() == 0)
						return;

					removeAll();
					FieldGroup fg = arrLRangeFG.get(0);
					TextField min = (TextField) fg.getField("Min");
					TextField max = (TextField) fg.getField("Max");
					min.setValue("");
					min.setEnabled(true);

					max.setValue("");
					max.setEnabled(true);
					isTiered = true;
					comboConditionType.select(arrConValues[0]);
					return;

				} else {
					if (arrLRangeFG.size() == 0)
						return;
					removeAll();
					FieldGroup fg = arrLRangeFG.get(0);
					TextField min = (TextField) fg.getField("Min");
					TextField max = (TextField) fg.getField("Max");
					min.setValue("0.00");
					min.setEnabled(false);

					max.setValue("0.00");
					max.setEnabled(false);
					isTiered = false;
					comboConditionType.select(arrConValues[1]);
					return;
				}

			}

		});

		btnAdd.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 8105347974986024315L;

			@Override
			public void buttonClick(ClickEvent event) {
				add();

			}
		});

		btnRemove.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3921995443687711235L;

			@Override
			public void buttonClick(ClickEvent event) {
				remove();

			}
		});

		btnSave.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -7900935606088856031L;

			@Override
			public void buttonClick(ClickEvent event) {
				/*
				 * Commit to server.
				 */

				if (isReadyToCommit(tabType, otherfg)) {
					commit();
					return;
				} else {
					return;
				}

			}
		});

		return cManage;
	}

	private void commit() {

		String opID = comboOp.getValue().toString().trim();
		Integer txID = Integer.valueOf(comboTxType.getValue().toString());
		ArrayList<ArrayList<FieldGroup>> allFeesFG = hmAllFG.get(FEES);
		ArrayList<FieldGroup> allRange = allFeesFG.get(0);
		ArrayList<FieldGroup> allMat = allFeesFG.get(1);

		ServiceFees[] sf = new ServiceFees[allRange.size()];
		for (int i = 0; i < allRange.size(); i++) {
			FieldGroup rfg = allRange.get(i);
			FieldGroup mfg = allMat.get(i);
			sf[i] = new ServiceFees();
			sf[i].setMinimumamount(BigDecimal.valueOf(Float.valueOf(rfg
					.getField("Min").getValue().toString().trim())));
			sf[i].setMaximumamount(BigDecimal.valueOf(Float.valueOf(rfg
					.getField("Max").getValue().toString().trim())));

			sf[i].setServicefeetype(ServiceFeematrix.Factory.fromValue(mfg
					.getField("Mat").getValue().toString().trim()));

			sf[i].setServicefee(BigDecimal.valueOf(Float.valueOf(mfg
					.getField("Amt").getValue().toString().trim())));
			sf[i].setTransactiontypeid(txID);

		}

		ArrayList<ArrayList<FieldGroup>> allCommFG = hmAllFG.get(COMMISSION);
		allRange = allCommFG.get(0);
		allMat = allCommFG.get(1);
		ArrayList<FieldGroup> arrLDFG = allCommFG.get(2);
		FieldGroup dfg = arrLDFG.get(0);
		String conType = dfg.getField("CONID").getValue().toString().trim();
		String modType = dfg.getField("MODID").getValue().toString().trim();

		ServiceCommission[] sc = new ServiceCommission[allRange.size()];
		for (int i = 0; i < allRange.size(); i++) {
			FieldGroup rfg = allRange.get(i);
			FieldGroup mfg = allMat.get(i);
			sc[i] = new ServiceCommission();
			sc[i].setMinimumamount(BigDecimal.valueOf(Float.valueOf(rfg
					.getField("Min").getValue().toString().trim())));
			sc[i].setMaximumamount(BigDecimal.valueOf(Float.valueOf(rfg
					.getField("Max").getValue().toString().trim())));
			sc[i].setServicecommissioncondition(ServiceCommissionConditionTypes.Factory
					.fromValue(conType));
			sc[i].setServicecommissionmodeltype(ServiceCommissionModelTypes.Factory
					.fromValue(modType));
			sc[i].setCommissionfeetype(mfg.getField("Mat").getValue()
					.toString().trim());
			sc[i].setCommissionfee(BigDecimal.valueOf(Float.valueOf(mfg
					.getField("Amt").getValue().toString().trim())));
			sc[i].setTransactiontypeid(txID);

		}

		CommissionService cs = new CommissionService();

		try {
			if (cs.setFeesAndCommission(opID, txID, sc, sf)) {
				if (cs.statusMessage == null) {
					Notification.show("Tariff information saved successfully!");
					confCount = 0;
					resetFields(lookedTab);
				} else {
					confCount = 0;
					comboOp.setEnabled(true);
					comboTxType.setEnabled(true);
					lookedTab = null;
					Notification.show("Tariff saving FAILED: "
							+ cs.statusMessage);
				}

			} else {
				Notification.show("Tariff saving FAILED: " + cs.statusMessage);
			}
		} catch (Exception ce) {
			ce.printStackTrace();
			return;
		}

	}

	private boolean isReadyToCommit(String type, FieldGroup dfg) {
		comboOp.setRequired(false);
		if (comboOp.getValue() == null) {
			comboOp.setRequired(true);
			Notification.show("Field marked with (*) is required.");
			return false;
		}

		comboTxType.setRequired(false);
		if (comboTxType.getValue() == null) {
			comboTxType.setRequired(true);
			Notification.show("Field marked with (*) is required.");
			return false;
		}

		try {
			dfg.commit();
		} catch (CommitException e1) {
			e1.printStackTrace();
			return false;
		}

		for (FieldGroup fg : arrLRangeFG) {
			try {
				fg.commit();
			} catch (CommitException e) {
				e.printStackTrace();
				return false;
			}
		}
		for (FieldGroup fg : arrLMatFG) {
			try {
				fg.commit();
			} catch (CommitException e) {
				e.printStackTrace();
				return false;
			}
		}

		/*
		 * Ready to commit valid values to the server.
		 */

		arrLDFG.clear();
		arrLDFG.add(dfg);
		arrLAllFG.add(arrLRangeFG);
		arrLAllFG.add(arrLMatFG);
		arrLAllFG.add(arrLDFG);
		hmAllFG.put(type, arrLAllFG);
		confCount++;

		if (type.equals(FEES) && confCount < 2) {
			lookedTab = type;
			UI.getCurrent()
					.getSession()
					.setAttribute(
							WorkSpaceManageFeesAndComm.SESSION_WSMP_CUR_ACTION,
							COMMISSION);
			Settings.wmfac.wsmpModifier();
			ManageFeesAndCommModule.btnFees.setStyleName("btn_tab_like");
			ManageFeesAndCommModule.btnFees.setEnabled(true);
			ManageFeesAndCommModule.btnComm
					.setStyleName("btn_tab_like btn_tab_like_active");
			ManageFeesAndCommModule.btnComm.setEnabled(false);

		} else if (type.equals(COMMISSION) && confCount < 2) {
			lookedTab = type;
			UI.getCurrent()
					.getSession()
					.setAttribute(
							WorkSpaceManageFeesAndComm.SESSION_WSMP_CUR_ACTION,
							FEES);
			Settings.wmfac.wsmpModifier();

			ManageFeesAndCommModule.btnFees
					.setStyleName("btn_tab_like btn_tab_like_active");
			ManageFeesAndCommModule.btnFees.setEnabled(false);
			ManageFeesAndCommModule.btnComm.setStyleName("btn_tab_like");
			ManageFeesAndCommModule.btnComm.setEnabled(true);

		} else {
			return true;
		}
		return false;
	}

	public HorizontalLayout getFeesForm(String strTbName, String strID,
			boolean a, boolean b) {
		return null;
	}

	private void resetFields(String type) {
		lookedTab = type;

		if (type.equals(COMMISSION)) {

			UI.getCurrent()
					.getSession()
					.setAttribute(
							WorkSpaceManageFeesAndComm.SESSION_WSMP_CUR_ACTION,
							type);
			Settings.wmfac.wsmpModifier();
			ManageFeesAndCommModule.btnFees.setStyleName("btn_tab_like");
			ManageFeesAndCommModule.btnFees.setEnabled(true);
			ManageFeesAndCommModule.btnComm
					.setStyleName("btn_tab_like btn_tab_like_active");
			ManageFeesAndCommModule.btnComm.setEnabled(false);

		} else if (type.equals(FEES)) {
			UI.getCurrent()
					.getSession()
					.setAttribute(
							WorkSpaceManageFeesAndComm.SESSION_WSMP_CUR_ACTION,
							type);
			Settings.wmfac.wsmpModifier();

			ManageFeesAndCommModule.btnFees
					.setStyleName("btn_tab_like btn_tab_like_active");
			ManageFeesAndCommModule.btnFees.setEnabled(false);
			ManageFeesAndCommModule.btnComm.setStyleName("btn_tab_like");
			ManageFeesAndCommModule.btnComm.setEnabled(true);

		}
	}

	public void apmModifier(String strTbName, HorizontalLayout cContent) {

		cContent.removeAllComponents();
		if (strTbName.equals(EXISTING)) {
			cContent.addComponent(getExistingFeesContainer(strTbName));
			return;
		}
		cContent.addComponent(getFeesContainer(strTbName));
		if (lookedTab != null) {
			if (lookedTab.equals(strTbName)) {

				comboOp.setEnabled(true);
				comboTxType.setEnabled(true);
				confCount = 0;

				ArrayList<ArrayList<FieldGroup>> allFeesFG = hmAllFG
						.get(strTbName);
				if (allFeesFG != null) {
					ArrayList<FieldGroup> allRange = allFeesFG.get(0);
					ArrayList<FieldGroup> allMat = allFeesFG.get(1);

					FieldGroup dfg = arrLDFG.get(0);
					String conType = dfg.getField("CONID").getValue()
							.toString().trim();
					String modType = dfg.getField("MODID").getValue()
							.toString().trim();
					ComboBox comboConT = (ComboBox) otherfg.getField("CONID");
					ComboBox comboModT = (ComboBox) otherfg.getField("MODID");

					comboConT.setValue(conType);
					comboModT.setValue(modType);

					comboOp.setEnabled(true);
					comboTxType.setEnabled(true);

					for (int i = 0; i < allRange.size(); i++) {

						FieldGroup rfg = allRange.get(i);
						FieldGroup mfg = allMat.get(i);

						FieldGroup nrfg = arrLRangeFG.get(i);
						FieldGroup nmfg = arrLMatFG.get(i);

						TextField tfRMin = (TextField) rfg.getField("Min");
						TextField tfnRMin = (TextField) nrfg.getField("Min");

						TextField tfRMax = (TextField) rfg.getField("Max");
						TextField tfnRMax = (TextField) nrfg.getField("Max");

						ComboBox comboMat = (ComboBox) mfg.getField("Mat");
						ComboBox combonMat = (ComboBox) nmfg.getField("Mat");

						TextField tfAmt = (TextField) mfg.getField("Amt");
						TextField tfnAmt = (TextField) nmfg.getField("Amt");

						tfnRMin.setValue(tfRMin.getValue());
						tfnRMax.setValue(tfRMax.getValue());
						combonMat.setValue(comboMat.getValue());
						tfnAmt.setValue(tfAmt.getValue());
						add();

					}

				}
				return;
			} else {
				comboOp.setEnabled(false);
				comboTxType.setEnabled(false);

			}

		}

	}

	public HorizontalLayout getExistingFeesContainer(String type) {

		cArrLItemContent = new ArrayList<>(4);
		arrLRangeFG = new ArrayList<>();
		arrLMatFG = new ArrayList<>();
		arrLAllFG = new ArrayList<>();
		isTiered = true;
		tabType = type;

		final HorizontalLayout cManage = new HorizontalLayout();
		cManage.setWidthUndefined();
		cManage.setHeightUndefined();

		VerticalLayout cChoose = new VerticalLayout();
		cChoose.setSizeUndefined();
		cChoose.setStyleName("frm_add_user frm_manage_commission");

		VerticalLayout addUserHeader = new VerticalLayout();
		addUserHeader.setWidthUndefined();
		addUserHeader.setHeightUndefined();
		addUserHeader.setSpacing(true);

		cChoose.addComponent(addUserHeader);

		Label lbAddUser = new Label("Manage " + tabType);
		lbAddUser.setStyleName("label_add_user");
		lbAddUser.setSizeUndefined();

		cChoose.addComponent(lbAddUser);
		Label lbChoose = new Label("Please choose...");

		final ComboBox comboConditionType = new ComboBox("Condition Type");
		comboConditionType.addItem(arrConValues[0]);
		comboConditionType.setItemCaption(arrConValues[0], "Amount");
		comboConditionType.addItem(arrConValues[1]);
		comboConditionType.setItemCaption(arrConValues[1], "Fee");
		comboConditionType.select(arrConValues[0]);

		final ComboBox comboModelType = new ComboBox("Model Type");
		comboModelType.addItem(arrModelValues[0]);
		comboModelType.setItemCaption(arrModelValues[0], "Tiered");
		comboModelType.addItem(arrModelValues[1]);
		comboModelType.setItemCaption(arrModelValues[1], "None");
		comboModelType.select(arrModelValues[0]);

		Item row = new PropertysetItem();

		Property<String> pconTypeID = new ObjectProperty<>(arrConValues[0]);
		Property<String> pmodelTypeID = new ObjectProperty<>(arrModelValues[0]);
		row.addItemProperty("CONID", pconTypeID);
		row.addItemProperty("MODID", pmodelTypeID);

		otherfg = new FieldGroup(row);
		otherfg.bind(comboConditionType, "CONID");
		otherfg.bind(comboModelType, "MODID");

		otherfg.addCommitHandler(new CommitHandler() {
			private static final long serialVersionUID = 6144936023943646696L;

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {
				comboOp.setRequired(false);
				comboConditionType.setRequired(false);
				comboModelType.setRequired(false);
				comboTxType.setRequired(false);
				if (comboOp.getValue() == null) {
					comboOp.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");
				} else if (comboConditionType.getValue() == null) {
					comboConditionType.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");

				} else if (comboModelType.getValue() == null) {
					comboModelType.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");

				} else if (comboTxType.getValue() == null) {
					comboTxType.setRequired(true);
					Notification.show("Field marked with (*) is required.");
					throw new CommitException("Field is required.");
				} else {
					comboOp.setRequired(false);
					comboConditionType.setRequired(false);
					comboModelType.setRequired(false);
					comboTxType.setRequired(false);
					return;
				}
			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {

			}

		});

		Button btnRetrieve = new Button("Retrive");
		btnRetrieve.setStyleName("btn_f_c_retrieve");

		cChoose.addComponent(lbChoose);
		cChoose.addComponent(comboOp);
		cChoose.addComponent(btnRetrieve);
		// cChoose.addComponent(comboConditionType);
		// cChoose.addComponent(comboModelType);
		// cChoose.addComponent(comboTxType);
		cChoose.setStyleName("c_choose");

		// cControls.addComponent(btn);

		cManage.addComponent(cChoose);

		comboTxType.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -4750457498573552155L;

			@Override
			public void valueChange(ValueChangeEvent event) {

			}

		});

		btnRetrieve.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5059266488185150011L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Retrive OP Tariffs.
				getSearchResults(cManage);

			}
		});

		comboConditionType.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -5878577330071011374L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue().toString().trim()
						.equals(arrConValues[0])) {
					comboModelType.select(arrModelValues[0]);
				} else {
					comboModelType.select(arrModelValues[1]);
				}
			}

		});

		comboModelType.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -6418325605387194047L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (comboModelType.getValue().toString()
						.equals(arrModelValues[0])) {

					if (arrLRangeFG.size() == 0)
						return;

					removeAll();
					FieldGroup fg = arrLRangeFG.get(0);
					TextField min = (TextField) fg.getField("Min");
					TextField max = (TextField) fg.getField("Max");
					min.setValue("");
					min.setEnabled(true);

					max.setValue("");
					max.setEnabled(true);
					isTiered = true;
					comboConditionType.select(arrConValues[0]);
					return;

				} else {
					if (arrLRangeFG.size() == 0)
						return;
					removeAll();
					FieldGroup fg = arrLRangeFG.get(0);
					TextField min = (TextField) fg.getField("Min");
					TextField max = (TextField) fg.getField("Max");
					min.setValue("0.00");
					min.setEnabled(false);

					max.setValue("0.00");
					max.setEnabled(false);
					isTiered = false;
					comboConditionType.select(arrConValues[1]);
					return;
				}

			}

		});

		return cManage;
	}

	private void getSearchResults(HorizontalLayout cManage) {
		VerticalLayout cAttr = new VerticalLayout();
		cAttr.setSpacing(true);
		cAttr.setMargin(new MarginInfo(false, false, true, false));
		cAttr.setStyleName("c_attr");

		final Label tFOp = new Label();
		tFOp.setCaption("Selected Operator: ");
		tFOp.setValue(comboOp.getItemCaption(comboOp.getValue()));
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

		// MIN

		VerticalLayout cItemContentMin = new VerticalLayout();
		cArrLItemContent.add(cItemContentMin);
		lbAttr = new Label("Tier");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMin.addComponent(lbAttr);
		cItemContentMin.addComponent(getPseudoTable());
		cAttrItem.addComponent(cItemContentMin);

		// MIN

		/*
		 * VerticalLayout cItemContentMin = new VerticalLayout();
		 * cArrLItemContent.add(cItemContentMin); lbAttr = new Label("Min.");
		 * lbAttr.setSizeFull(); lbAttr.setStyleName("label_add_user attr");
		 * cItemContentMin.addComponent(lbAttr); //
		 * cAttrItem.addComponent(cItemContentMin);
		 */

		// MAX

		VerticalLayout cItemContentMax = new VerticalLayout();
		cArrLItemContent.add(cItemContentMax);
		lbAttr = new Label("Max.");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMax.addComponent(lbAttr);
		// cAttrItem.addComponent(cItemContentMax);

		// MATRIX
		VerticalLayout cItemContentMat = new VerticalLayout();
		cArrLItemContent.add(cItemContentMat);
		lbAttr = new Label("Matrix");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentMat.addComponent(lbAttr);
		// cAttrItem.addComponent(cItemContentMat);

		// AMOUNT

		VerticalLayout cItemContentAmt = new VerticalLayout();
		cArrLItemContent.add(cItemContentAmt);
		lbAttr = new Label("Amount (%/N)");
		lbAttr.setSizeFull();
		lbAttr.setStyleName("label_add_user attr");
		cItemContentAmt.addComponent(lbAttr);
		// cAttrItem.addComponent(cItemContentAmt);
		add();
		// add();

		HorizontalLayout cControls = new HorizontalLayout();
		Button btnAdd = new Button("+");

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
		cAttr.setWidth("100%");
		cAttr.setHeightUndefined();

		// cAttr.setComponentAlignment(cControls, Alignment.BOTTOM_RIGHT);
		// VerticalLayout cT = new VerticalLayout();
		// cT.setHeightUndefined();
		// cT.setWidth("100%");
		// cT.addComponent(getPseudoTable());
		// cAttr.addComponent(cT);

		cManage.addComponent(cAttr);

		btnAdd.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 8105347974986024315L;

			@Override
			public void buttonClick(ClickEvent event) {
				add();

			}
		});

		btnRemove.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3921995443687711235L;

			@Override
			public void buttonClick(ClickEvent event) {
				remove();

			}
		});

		btnSave.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -7900935606088856031L;

			@Override
			public void buttonClick(ClickEvent event) {
				/*
				 * Commit to server.
				 */

				if (isReadyToCommit(tabType, otherfg)) {
					commit();
					return;
				} else {
					return;
				}

			}
		});

	}

	@SuppressWarnings("unchecked")
	private Table getPseudoTable() {

		IndexedContainer ct = new IndexedContainer();
		Table ttb = new Table();

		HorizontalLayout cttb = new HorizontalLayout();
		cttb.setWidth("100%");
		cttb.setHeightUndefined();
		cttb.addComponent(ttb);

		ct.addContainerProperty("Min.", String.class, "");
		ct.addContainerProperty("Max.", String.class, "");
		ct.addContainerProperty("Matrix", String.class, "");
		ct.addContainerProperty("Amount", String.class, "");

		Object ctobjr = ct.addItem();
		Item rct = ct.getItem(ctobjr);
		Property<String> pct = rct.getItemProperty("Min.");
		pct.setValue("100.00");

		pct = rct.getItemProperty("Max.");
		pct.setValue("1000.00");

		pct = rct.getItemProperty("Matrix");
		pct.setValue("%");

		pct = rct.getItemProperty("Amount");
		pct.setValue("2");
		ttb.setContainerDataSource(ct);
		ttb.setWidth("100%");
		ttb.setHeightUndefined();

		IndexedContainer container = new IndexedContainer();
		container
				.addContainerProperty("Transaction Type", String.class, "None");
		container.addContainerProperty("Tier", HorizontalLayout.class, null);

		Object objr = container.addItem();
		Item rowID = container.getItem(objr);
		Property<String> cID = rowID.getItemProperty("Transaction Type");
		cID.setValue("CASH_OUT");

		Table tb = new Table("Fees");
		Property<HorizontalLayout> pt = rowID.getItemProperty("Tier");
		pt.setValue(cttb);
		tb.setWidth("100%");
		tb.setHeightUndefined();
		// tb.setContainerDataSource(container);
		tb.setContainerDataSource(ct);
		tb.setPageLength(0);
		// tb.setPageLength(5);

		return tb;
	}
}
