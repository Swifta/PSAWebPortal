package com.swifta.mats.web.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVReader;

import com.swifta.mats.web.Initializer;
import com.swifta.mats.web.usermanagement.BtnActions;
import com.swifta.mats.web.usermanagement.PagedTableCustom;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
//import com.vaadin.ui.Notification;
//import com.vaadin.ui.Notification;
//import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class Settings extends VerticalLayout {
	private HashMap<String, String> hmProfPermPermissions;
	private HashMap<String, String> hmFeesCommPermissions;

	Settings() {
		setProfileAndPermissionsPermissions();
		setFeesAndCommPermissions();
	}

	private void setProfileAndPermissionsPermissions() {

		hmProfPermPermissions = new HashMap<>();
		hmProfPermPermissions.put("edit_profile", "/editprofile");
		hmProfPermPermissions.put("add_profile", "/addprofile");
		hmProfPermPermissions.put("add_threshold", "/addprofilethreshold");
		hmProfPermPermissions.put("remove_profile", "/removeprofile");
		hmProfPermPermissions.put("remove_permissions",
				"/removeprofilepermission");
		hmProfPermPermissions.put("set_permissions", "/setprofilepermission");

	}

	private void setFeesAndCommPermissions() {

		hmFeesCommPermissions = new HashMap<>();

		hmFeesCommPermissions.put("man_set_fees_commission",
				"/setupservicefeesandcommission");
		hmFeesCommPermissions.put("man_existing_fees_commission",
				"/viewsetfeesandcommission");

	}

	HorizontalLayout laying = new HorizontalLayout();
	int ret;
	Button adduser = new Button("+Add New");
	Button filter = new Button("Filter");
	Button BulkImport = new Button("Bulk Import");
	Button back = new Button();

	ComboBox selection = new ComboBox();
	Button createAccount = new Button("Create");
	Button cancelAccount = new Button("Cancel");
	Button cancelBulk = new Button("Cancel");
	Button applyBulk = new Button("Apply");
	Button applyBulk2 = new Button("Apply");

	FormLayout addAccount = new FormLayout();
	TextField nameOfAccount = new TextField("Name");
	TextField codeOfAccount = new TextField("Code");
	TextArea descOfAccount = new TextArea("Description");

	FormLayout addAccount2 = new FormLayout();
	TextField nameOfAccount2 = new TextField("Name");
	Label codeOfAccount2 = new Label();
	TextArea descOfAccount2 = new TextArea("Description");
	Label dateCreated = new Label("Date Created");
	Label index = new Label("Index");
	ComboBox type2 = new ComboBox("Type");
	Button editAccount = new Button("Save");
	Button cancelAccount2 = new Button("Cancel");

	ComboBox type = new ComboBox("Type");
	ComboBox bulkAction = new ComboBox();
	HorizontalLayout layout2 = new HorizontalLayout();
	DateField date = new DateField();
	BtnActions btnDetails;
	BtnActions btnEdit;
	BtnActions btnLink;
	BtnActions btnDelete;
	BtnActions btnMoreActions;
	HorizontalLayout actionsC;
	ThemeResource icDelete = new ThemeResource("img/ic_delete_small.png");
	CheckBox selectAll = new CheckBox();

	IndexedContainer container = new IndexedContainer();
	IndexedContainer filtercontainer2 = new IndexedContainer();
	IndexedContainer filtercontainer3 = new IndexedContainer();

	PagedTableCustom tb = new PagedTableCustom("Results for: Accounts");

	VerticalLayout searchResultsContainer = new VerticalLayout();
	VerticalLayout layoutFile = new VerticalLayout();
	Integer x = 1;
	File tempFile;
	Image image = new Image("", new ThemeResource("img/format.PNG"));
	Label labFile = new Label("File should be in the format below");

	HorizontalLayout pnUserSearchResults = tb.createControls();
	HorizontalLayout pnUserSearchResults2 = tb.createControls();
	HorizontalLayout laybut = new HorizontalLayout();
	HorizontalLayout bulkLayout = new HorizontalLayout();

	HorizontalLayout laybut1 = new HorizontalLayout();
	Label lbel1 = new Label("Account Management");
	int y = 0;

	private static final long serialVersionUID = -2467595854268829523L;

	private WorkSpaceManageFeesAndComm wmfac;
	private ProfilesAndPermissionsModule ppm;
	private FeesAndCommissionModuleClone fcm;

	public HorizontalLayout Addlabel() {
		setMargin(true);
		AddIcons icon1 = new AddIcons();

		back.setStyleName("btn_link btn_back");
		back.setCaption("Back");
		back.setDescription("Back to all settings");
		back.setIcon(FontAwesome.UNDO);

		final VerticalLayout het1 = icon1.ImagesClicking("Account Management",
				"img/use.png");
		het1.setStyleName(ValoTheme.LINK_SMALL + " ic_hand see_lay");
		final VerticalLayout het2 = icon1.ImagesClicking(
				"Profiles / Permissions", "img/permission.png");
		het2.setStyleName(ValoTheme.LINK_SMALL + " ic_hand see_lay");
		final VerticalLayout het3 = icon1.ImagesClicking("Authentication",
				"img/auth.png");
		het3.setStyleName(ValoTheme.LINK_SMALL + " ic_hand see_lay");
		final VerticalLayout het4 = icon1.ImagesClicking("Fees/ Commission",
				"img/fees.png");
		het4.setStyleName(ValoTheme.LINK_SMALL + " ic_hand see_lay");
		final VerticalLayout het5 = icon1.ImagesClicking("Transfer",
				"img/transfer.png");
		het5.setStyleName(ValoTheme.LINK_SMALL + " ic_hand see_lay");
		final VerticalLayout het6 = icon1.ImagesClicking("Threshold",
				"img/threshold.png");

		het6.setStyleName(ValoTheme.LINK_SMALL + " ic_hand see_lay");

		final HorizontalLayout cIcon = new HorizontalLayout();
		cIcon.addComponent(het1);
		if (Initializer.setUserPermissions.contains(hmProfPermPermissions
				.get("edit_profile"))
				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("add_profile"))
				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("remove_profile"))
				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("add_threshold"))
				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions.get("set_permissions"))
				|| Initializer.setUserPermissions
						.contains(hmProfPermPermissions
								.get("remove_permissions")))
			cIcon.addComponent(het2);
		cIcon.addComponent(het3);

		if (Initializer.setUserPermissions.contains(hmFeesCommPermissions
				.get("man_set_fees_commission"))
				|| Initializer.setUserPermissions
						.contains(hmFeesCommPermissions
								.get("man_existing_fees_commission")))
			cIcon.addComponent(het4);

		if (Initializer.setUserPermissions.contains(hmFeesCommPermissions
				.get("man_set_fees_commission"))
				|| Initializer.setUserPermissions
						.contains(hmFeesCommPermissions
								.get("man_existing_fees_commission")))
			cIcon.addComponent(het5);
		cIcon.addComponent(het6);

		het2.addLayoutClickListener(new LayoutClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -3403162468496827919L;

			@Override
			public void layoutClick(LayoutClickEvent event) {

				if (ppm == null)
					ppm = new ProfilesAndPermissionsModule(back,
							hmProfPermPermissions);
				VerticalLayout cppm = ppm.getMainContainer();

				laying.removeAllComponents();
				laying.addComponent(cppm);
				laying.setComponentAlignment(cppm, Alignment.TOP_CENTER);
				laying.setSizeFull();

			}

		});

		het4.addLayoutClickListener(new LayoutClickListener() {

			private static final long serialVersionUID = -3403162468496827919L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (wmfac == null)
					wmfac = new WorkSpaceManageFeesAndComm(
							hmFeesCommPermissions);

				VerticalLayout cmfac = wmfac.getWorkSpaceAccountProfile(back);
				laying.removeAllComponents();
				laying.addComponent(cmfac);
				laying.setComponentAlignment(cmfac, Alignment.TOP_LEFT);
				laying.setSizeFull();

			}

		});

		het5.addLayoutClickListener(new LayoutClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1706822656417754591L;

			@Override
			public void layoutClick(LayoutClickEvent event) {

				if (fcm == null)
					fcm = new FeesAndCommissionModuleClone(back,
							hmProfPermPermissions);
				VerticalLayout cfcm = fcm.getMainContainer();

				laying.removeAllComponents();
				laying.addComponent(cfcm);
				laying.setComponentAlignment(cfcm, Alignment.TOP_CENTER);
				laying.setSizeFull();

			}

		});

		laying.setSizeUndefined();
		// laying.setSizeFull();
		laying.addComponent(cIcon);

		type.addItem("Fees");
		type.addItem("Commission");
		type.addItem("Control");
		type.setNullSelectionAllowed(false);
		type.setTextInputAllowed(false);
		type.setValue(type.getItemIds().iterator().next());

		type2.addItem("Fees");
		type2.addItem("Commission");
		type2.addItem("Control");
		type2.setNullSelectionAllowed(false);
		type2.setTextInputAllowed(false);
		type2.setValue(type.getItemIds().iterator().next());

		// bulkAction.addItem("Delete");
		bulkAction.addItem("Disable");
		bulkAction.addItem("Enable");
		bulkAction.setNullSelectionAllowed(false);
		// bulkAction.setTextInputAllowed(false);
		// bulkAction.setStyleName("bulkActionButt");
		// bulkAction.setValue(bulkAction.getItemIds().iterator().next());
		bulkAction.setInputPrompt("Select an action");

		// bulkLayout.setHeight("20px");
		// bulkLayout.setWidth("50px");
		bulkLayout.setMargin(new MarginInfo(false, false, true, false));
		bulkLayout.addComponent(selectAll);
		bulkLayout.addComponent(bulkAction);
		bulkLayout.addComponent(applyBulk2);

		addProperties(container);
		addProperties(filtercontainer2);
		addProperties(filtercontainer3);

		het1.addLayoutClickListener(new LayoutClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void layoutClick(LayoutClickEvent event) {

				searchResultsContainer.setWidth("100%");
				searchResultsContainer.setHeightUndefined();

				searchResultsContainer.setSpacing(true);
				searchResultsContainer.setMargin(new MarginInfo(false, true,
						true, true));

				searchResultsContainer.addComponent(back);
				searchResultsContainer.setComponentAlignment(back,
						Alignment.BOTTOM_RIGHT);

				// tb.setWidth("990px");
				tb.setWidth("100%");
				tb.setHeightUndefined();

				tb.setContainerDataSource(container);
				tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE);

				// tb.setColumnIcon("Name", icDelete);
				// tb.setStyleName("tb_u_search_results");

				pnUserSearchResults.setMargin(false);
				pnUserSearchResults.setSpacing(false);
				// pnUserSearchResults.addComponent(bulkLayout, 1);
				// pnUserSearchResults.setComponentAlignment(bulkLayout,
				// Alignment.TOP_LEFT);

				pnUserSearchResults2.setMargin(false);
				pnUserSearchResults2.setSpacing(false);

				laybut.setMargin(false);
				// laybut.setSizeFull();
				laybut.addComponent(adduser);
				laybut.addComponent(BulkImport);

				laybut1.setMargin(new MarginInfo(false, false, true, false));
				selection.addItem("All");
				selection.addItem("Fees");
				selection.addItem("Commission");
				selection.addItem("Control");
				selection.setNullSelectionAllowed(false);

				selection.setTextInputAllowed(false);
				selection.setValue(selection.getItemIds().iterator().next());
				laybut1.addComponent(selection);
				laybut1.addComponent(filter);

				searchResultsContainer.addComponent(lbel1);
				searchResultsContainer.addComponent(laybut1);
				searchResultsContainer.setComponentAlignment(laybut1,
						Alignment.TOP_LEFT);

				searchResultsContainer.addComponent(laybut);
				searchResultsContainer.addComponent(pnUserSearchResults);
				searchResultsContainer.addComponent(bulkLayout);

				searchResultsContainer.addComponent(tb);
				searchResultsContainer.setExpandRatio(tb, 1);

				searchResultsContainer.addComponent(pnUserSearchResults2);

				laying.removeAllComponents();
				laying.addComponent(searchResultsContainer);
				laying.setExpandRatio(searchResultsContainer, 1);
				int x = container.size();
				if (x > 30)
					x = 29;
				tb.setPageLength(x + 1);

			}
		});

		selectAll.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public void valueChange(ValueChangeEvent event) {
				int t = tb.getContainerDataSource().size();

				if (selectAll.getValue()) {
					if (t != 0) {
						for (int i = 0; i < t; i++) {
							CheckBox checky = new CheckBox();
							checky.setValue(true);

							tb.getContainerDataSource()
									.getContainerProperty(
											tb.getContainerDataSource()
													.getIdByIndex(i), " ")
									.setValue(checky);

						}
					} else {
						Notification.show("Table is empty",
								Notification.Type.WARNING_MESSAGE);
					}
				} else {
					for (int i = 0; i < t; i++) {
						CheckBox checky = new CheckBox();
						checky.setValue(false);
						tb.getContainerDataSource()
								.getContainerProperty(
										tb.getContainerDataSource()
												.getIdByIndex(i), " ")
								.setValue(checky);

					}
				}

				int x = tb.getContainerDataSource().size();
				if (x > 30)
					t = 29;
				tb.setPageLength(t + 1);

			}

		});

		applyBulk2.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				// int t = tb.getContainerDataSource().size();

				findById(searchResultsContainer, bulkAction.getValue()
						.toString());

				// TODO Auto-generated method stub

			}

		});

		// ///////////////////////////////////////////////////////////////////////////////////////////////////

		filter.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				String selectedId = selection.getValue().toString();
				int r;
				container.removeAllContainerFilters();

				r = container.size();
				if (selectedId != "All") {
					if (r != 0) {

						Filter filter = new SimpleStringFilter("Type",
								selectedId, true, true);

						container.addContainerFilter(filter);
						tb.setContainerDataSource(container);
						int x = tb.getContainerDataSource().size();
						if (x > 30)
							x = 29;
						tb.setPageLength(x + 1);
					} else {
						Notification.show("No result found, table is empty",
								Notification.Type.WARNING_MESSAGE);
					}

				} else {
					if (container.size() == 0) {
						Notification.show("Table is Empty",
								Notification.Type.WARNING_MESSAGE);
					}
					tb.setContainerDataSource(container);
					int x = tb.getContainerDataSource().size();
					if (x > 30)
						x = 29;
					tb.setPageLength(x + 1);

				}

			}

		});

		/*
		 * for(i=0; i<r; i++){ date = container.getContainerProperty(i+1,
		 * "Date Created").getValue().toString(); name =
		 * container.getContainerProperty(i+1, "Name").getValue().toString();
		 * typer = container.getContainerProperty(i+1,
		 * "Type").getValue().toString(); code =
		 * container.getContainerProperty(i+1, "Code").getValue().toString();
		 * desc = container.getContainerProperty(i+1,
		 * "Description").getValue().toString(); //butt = (HorizontalLayout)
		 * container.getContainerProperty(i+1, "Action").getValue();
		 * //Notification.show(typer+ " Noted "+selection.getValue());
		 * 
		 * if(typer.equals(selection.getValue())){
		 * 
		 * 
		 * //het1click(date ,name,typer, code, desc,filtercontainer);
		 * //Notification.show(" Noted "+selectedId); }else{
		 * //Notification.show("Container"); continue; } }
		 * if(filtercontainer.size() ==0){ Notification.show("No result found");
		 * }
		 */

		// Notification.show(Integer.toString(filtercontainer.size()));

		// tb.setContainerDataSource(filtercontainer);
		// laying.removeAllComponents();
		// laying.addComponent(searchResultsContainer);

		back.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				laying.removeAllComponents();
				laying.addComponent(cIcon);
				// TODO Auto-generated method stub
				BulkImport.setEnabled(true);
				adduser.setEnabled(true);
			}

		});

		cancelBulk.addClickListener(new Button.ClickListener() {

			/**
			 * This cancel button is bulk upload
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				// laying.removeAllComponents();
				// laying.addComponent(searchResultsContainer);
				laying.removeComponent(layoutFile);
				BulkImport.setEnabled(true);

				// TODO Auto-generated method stub

			}

		});

		BulkImport.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (!adduser.isEnabled()) {

					adduser.setEnabled(true);
					laying.removeComponent(addAccount);
				}
				BulkImport.setEnabled(false);
				layoutFile.removeAllComponents();
				Upload upload = new Upload("Upload CSV File",
						new Upload.Receiver() {
							/**
								 * 
								 */
							private static final long serialVersionUID = 1L;

							@Override
							public OutputStream receiveUpload(String filename,
									String mimeType) {
								try {
									/*
									 * Here, we'll stored the uploaded file as a
									 * temporary file. No doubt there's a way to
									 * use a ByteArrayOutputStream, a reader
									 * around it, use ProgressListener (and a
									 * progress bar) and a separate reader
									 * thread to populate a container *during*
									 * the update.
									 * 
									 * This is quick and easy example, though.
									 */
									tempFile = File.createTempFile("temp",
											".csv");
									return new FileOutputStream(tempFile);
								} catch (IOException e) {
									e.printStackTrace();
									return null;
								}
							}
						});

				upload.addFinishedListener(new Upload.FinishedListener() {

					// upload.addListener(new Upload.FinishedListener() {
					/**
								 * 
								 */
					private static final long serialVersionUID = 1L;

					@Override
					public void uploadFinished(
							Upload.FinishedEvent finishedEvent) {
						try {
							FileReader reader = new FileReader(tempFile);

							@SuppressWarnings("resource")
							CSVReader csvReader = new CSVReader(reader);

							String[] record;
							while ((record = csvReader.readNext()) != null) {
								Date newFieldValue2 = new Date();
								filtercontainer2 = het1click(
										newFieldValue2.toString(), record[0],
										record[1], record[2], record[3],
										container, 0);
								// tb.setContainerDataSource(filtercontainer2);

								laying.removeAllComponents();
								laying.addComponent(searchResultsContainer);
								laying.setExpandRatio(searchResultsContainer, 1);
								BulkImport.setEnabled(true);
								Notification.show("Done",
										Notification.Type.WARNING_MESSAGE);
							}

							reader.close();
							tempFile.delete();

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});

				upload.setButtonCaption("Upload a CSV");
				upload.setImmediate(true);

				layoutFile.addComponent(upload);
				layoutFile.addComponent(labFile);
				layoutFile.addComponent(image);
				layoutFile.addComponent(cancelBulk);

				laying.addComponent(layoutFile, 0);
				laying.setExpandRatio(searchResultsContainer, 1);

				// TODO Auto-generated method stub

			}
		});

		adduser.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -6399762731213165020L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (!BulkImport.isEnabled()) {

					BulkImport.setEnabled(true);
					laying.removeComponent(layoutFile);
				}

				addAccount.removeAllComponents();
				layout2.removeAllComponents();

				addAccount.addComponent(nameOfAccount);
				addAccount.addComponent(type);
				addAccount.addComponent(codeOfAccount);
				addAccount.addComponent(descOfAccount);
				layout2.addComponent(createAccount);
				layout2.addComponent(cancelAccount);
				addAccount.addComponent(layout2);
				// laying.removeAllComponents();

				laying.addComponent(addAccount, 0);
				laying.setExpandRatio(searchResultsContainer, 1);
				// adduser.setDisableOnClick(true);
				adduser.setEnabled(false);
			}
		});

		createAccount.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				adduser.setEnabled(true);
				laying.removeAllComponents();
				Date newFieldValue = new Date();
				het1click(newFieldValue.toString(), nameOfAccount.getValue(),
						type.getValue().toString(), codeOfAccount.getValue(),
						descOfAccount.getValue(), container, 0);
				tb.setContainerDataSource(container);
				int x = container.size();
				if (x > 30)
					x = 29;
				tb.setPageLength(x + 1);
				// TODO Auto-generated method stub
				nameOfAccount.setValue("");
				codeOfAccount.setValue("");
				descOfAccount.setValue("");
				type.setValue("Fees");
				laying.addComponent(searchResultsContainer);
				laying.setExpandRatio(searchResultsContainer, 1);

			}

		});

		cancelAccount.addClickListener(new Button.ClickListener() {

			/**
			 * This button is for Add user
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				adduser.setEnabled(true);
				laying.removeComponent(addAccount);
				// TODO Auto-generated method stub

			}

		});

		editAccount.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				container.removeItem(ret);
				container.removeAllContainerFilters();
				het1click(dateCreated.getValue(), nameOfAccount2.getValue(),
						type2.getValue().toString(), codeOfAccount2.getValue(),
						descOfAccount2.getValue(), container, ret);

				if (selection.getValue().toString() != "All") {
					Filter filter = new SimpleStringFilter("Type", selection
							.getValue().toString(), true, true);
					container.addContainerFilter(filter);
				}

				tb.setContainerDataSource(container);
				int x = container.size();
				if (x > 30)
					x = 29;
				tb.setPageLength(x + 1);
				laying.removeComponent(addAccount);

				// btnEdit.setEnabled(true);
				// TODO Auto-generated method stub

			}
		});

		laying.setWidth("100%");
		laying.setHeightUndefined();

		return laying;

	}

	public void addProperties(IndexedContainer containa) {

		containa.addContainerProperty(" ", CheckBox.class, "");
		containa.addContainerProperty("Date Created", String.class, "");
		containa.addContainerProperty("Name", String.class, "");
		containa.addContainerProperty("Type", String.class, "");
		containa.addContainerProperty("Code", String.class, "");
		containa.addContainerProperty("Description", String.class, "");
		containa.addContainerProperty("Actions", HorizontalLayout.class, null);

	}

	@SuppressWarnings("unchecked")
	public IndexedContainer het1click(String a, String b, String e, String c,
			String d, IndexedContainer tabContainer, int id) {

		// Button actionsC = new Button("Details");

		btnDetails = new BtnActions("Details");
		btnDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		btnEdit = new BtnActions("Edit");
		btnEdit.setIcon(FontAwesome.EDIT);
		btnLink = new BtnActions("Disable Account");
		btnLink.setIcon(FontAwesome.CHECK);
		/*
		 * btnDelete = new BtnActions("Delete"); btnDelete.setIcon(icDelete);
		 * btnMoreActions = new BtnActions("More...");
		 * btnMoreActions.setIcon(FontAwesome.ANGLE_RIGHT);
		 */

		btnLink.setStyleName("butEnable");
		btnEdit.setStyleName("putspace");
		btnDetails.setStyleName("putspace");
		// btnDelete.setStyleName("putspace");
		// btnMoreActions.setStyleName("putspace");

		btnDetails.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("btnDetails Clicked",
						Notification.Type.WARNING_MESSAGE);

			}
		});

		btnEdit.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				ret = Integer.parseInt(event.getButton().getId()
						.replace("Edit", ""));

				tb.getContainerProperty(ret, "Name").getValue();

				nameOfAccount2.setValue(tb.getContainerProperty(ret, "Name")
						.getValue().toString());
				codeOfAccount2.setValue(tb.getContainerProperty(ret, "Code")
						.getValue().toString());
				descOfAccount2.setValue(tb
						.getContainerProperty(ret, "Description").getValue()
						.toString());
				type2.setValue(tb.getContainerProperty(ret, "Type").getValue()
						.toString());
				dateCreated.setValue(tb
						.getContainerProperty(ret, "Date Created").getValue()
						.toString());
				index.setValue(String.valueOf(ret));

				codeOfAccount2.setCaption("Account Code");

				addAccount.removeAllComponents();

				layout2.removeAllComponents();

				addAccount.addComponent(codeOfAccount2);
				addAccount.addComponent(nameOfAccount2);
				addAccount.addComponent(type2);

				addAccount.addComponent(descOfAccount2);

				layout2.addComponent(editAccount);
				layout2.addComponent(cancelAccount);
				addAccount.addComponent(layout2);
				// laying.removeAllComponents();

				laying.addComponent(addAccount, 0);
				laying.setExpandRatio(searchResultsContainer, 1);

				// btnEdit.setEnabled(false);
				event.getButton().setEnabled(false);

				// TODO Auto-generated method stub

			}

		});

		btnLink.addClickListener(new Button.ClickListener() {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				String Idc = event.getButton().getId();
				String Idd = event.getButton().getDescription();

				if (Idd == "Disable Account") {

					event.getButton().setDescription("Enable Account");
					event.getButton().setStyleName("butDisable");
					Resource icon = new ThemeResource("img/stop.png");
					event.getButton().setIcon(icon);
					int reti = Integer.parseInt(Idc.replace("Link", ""));
					String reti2 = tb.getContainerDataSource()
							.getContainerProperty(reti, "Name").getValue()
							.toString();
					Notification.show(reti2 + " Account Disabled",
							Notification.Type.WARNING_MESSAGE);
				} else if (Idd == "Enable Account") {

					event.getButton().setDescription("Disable Account");
					event.getButton().setStyleName("butEnable");
					event.getButton().setIcon(FontAwesome.CHECK);
					int reta = Integer.parseInt(Idc.replace("Link", ""));
					String reta2 = tb.getContainerDataSource()
							.getContainerProperty(reta, "Name").getValue()
							.toString();
					Notification.show(reta2 + " Account Enabled",
							Notification.Type.WARNING_MESSAGE);
				} else {

					Notification.show("No description on Button",
							Notification.Type.WARNING_MESSAGE);
				}

				int x = container.size();
				if (x > 30)
					x = 29;
				tb.setPageLength(x + 1);

			}
		});

		Object itemId;
		Item trItem;
		if (id == 0) {
			itemId = tabContainer.addItem();
		} else {
			itemId = id;
			// pitemId=id+1;
			tabContainer.addItemAt(id - 1, itemId);

		}
		CheckBox checky = new CheckBox();

		trItem = tabContainer.getItem(itemId);

		btnDetails.setId("Details" + itemId.toString());
		btnEdit.setId("Edit" + itemId.toString());
		btnLink.setId("Link" + itemId.toString());
		// btnDelete.setId("Delete"+itemId.toString());
		// btnMoreActions.setId("MoreActions"+itemId.toString());

		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		actionsC.setStyleName("c_actions");

		actionsC.addComponent(btnDetails);
		actionsC.addComponent(btnEdit);
		actionsC.addComponent(btnLink);

		Property<CheckBox> tdPropertyCheck = trItem.getItemProperty(" ");
		Property<String> tdPropertyUID = trItem.getItemProperty("Date Created");
		Property<String> tdPropertyUname = trItem.getItemProperty("Name");
		Property<String> tdPropertyUtype = trItem.getItemProperty("Type");
		Property<String> tdPropertyFname = trItem.getItemProperty("Code");
		Property<String> tdPropertyLname = trItem
				.getItemProperty("Description");
		Property<HorizontalLayout> tdPropertyActions = trItem
				.getItemProperty("Actions");

		tdPropertyCheck.setValue(checky);
		tdPropertyUID.setValue(a);
		tdPropertyUname.setValue(b);
		tdPropertyUtype.setValue(e);
		tdPropertyFname.setValue(c);
		tdPropertyLname.setValue(d);
		tdPropertyActions.setValue(actionsC);

		return tabContainer;
	}

	public void findById(HasComponents root, String id) {

		Iterator<Component> iterate = root.iterator();

		while (iterate.hasNext()) {
			String y;
			Component c = iterate.next();
			if (c.getId() != null) {
				y = c.getId().substring(0, 4);

			} else {
				y = "";
			}

			if (y.matches("Link")) {

				int de = Integer.parseInt(c.getId().substring(4));
				CheckBox checkwell = (CheckBox) tb.getContainerDataSource()
						.getContainerProperty(de, " ").getValue();

				Button b = (Button) c;
				if (id == "Disable") {
					if (checkwell.getValue()) {
						b.setDescription("Enable Account");
						b.setStyleName("butDisable");
						Resource icon = new ThemeResource("img/stop.png");
						b.setIcon(icon);
					}
				} else if (id == "Enable") {
					if (checkwell.getValue()) {
						b.setDescription("Disable Account");
						b.setStyleName("butEnable");
						b.setIcon(FontAwesome.CHECK);
					}
				} else {
					Notification.show("Unknown State",
							Notification.Type.WARNING_MESSAGE);
				}
			}
			if (c instanceof HasComponents) {
				findById((HasComponents) c, id);

			}
		}

	}

}
