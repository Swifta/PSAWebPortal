package com.swifta.mats.web.settings;


import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;

import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.swifta.mats.web.usermanagement.BtnActions;
import com.swifta.mats.web.usermanagement.PagedTableCustom;

public class Settings extends VerticalLayout {
	
	
	HorizontalLayout laying = new HorizontalLayout();

	Button adduser = new Button("+Add New");
	Button filter = new Button("Filter");
	Button BulkImport = new Button("Bulk Import");
	Button back = new Button("Back");
	ComboBox selection = new ComboBox();
	Button createAccount = new Button("Create");
	Button cancelAccount = new Button("Cancel");
	Button cancelBulk = new Button("Cancel");
	FormLayout addAccount = new FormLayout();
	TextField nameOfAccount = new TextField("Name");
	TextField codeOfAccount = new TextField("Code");
	TextArea descOfAccount = new TextArea("Description");
	HorizontalLayout layout2 = new HorizontalLayout();
	DateField date = new DateField();
	BtnActions btnDetails;
	BtnActions btnEdit;
	BtnActions btnLink;
	BtnActions  btnDelete;
	BtnActions  btnMoreActions;
	HorizontalLayout actionsC;
	ThemeResource icDelete = new ThemeResource("img/ic_delete_small.png");
	
	
	IndexedContainer container = new IndexedContainer();
	Indexed filtercontainer = new IndexedContainer();
	Indexed filtercontainer2 = new IndexedContainer();
	PagedTableCustom tb = new PagedTableCustom("Results for: Accounts");;
	VerticalLayout searchResultsContainer = new VerticalLayout();
	VerticalLayout layoutFile = new VerticalLayout();
	Integer x = 1;
	File tempFile;
	Image image = new Image("", new ThemeResource("img/dig.PNG"));
	Label labFile = new Label("File should be in the format below");
	
	HorizontalLayout pnUserSearchResults = tb.createControls();;
	HorizontalLayout laybut = new HorizontalLayout();
	HorizontalLayout laybut1 = new HorizontalLayout();
	Label lbel1 = new Label("Account Management");
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2467595854268829523L;
	
	public HorizontalLayout Addlabel() {
		
		
		
		
		
		container.addContainerProperty(" ", CheckBox.class, null);
		container.addContainerProperty("Date Created", String.class, "");
		container.addContainerProperty("Name", String.class, "");
		container.addContainerProperty("Code", String.class, "");
		container.addContainerProperty("Description", String.class, "");
		container.addContainerProperty("Actions", HorizontalLayout.class, null);
		
		// TODO Auto-generated method stub
		//setSizeFull();
		setMargin(true);
		AddIcons icon1 = new AddIcons();
		
		final VerticalLayout het1 = icon1.ImagesClicking("Account Management","img/use.png");
		final VerticalLayout het2 = icon1.ImagesClicking("Permissions","img/permission.png");
		final VerticalLayout het3 = icon1.ImagesClicking("Authentication","img/auth.png");
		final VerticalLayout het4 = icon1.ImagesClicking("Fees/ Commission","img/fees.png");
		final VerticalLayout het5 = icon1.ImagesClicking("Transfer","img/transfer.png");
		final VerticalLayout het6 = icon1.ImagesClicking("Threshold","img/threshold.png");
		
		laying.setSizeUndefined();
		//laying.setSizeFull();
		laying.addComponent(het1);
		laying.addComponent(het2);
		laying.addComponent(het3);
		laying.addComponent(het4);
		laying.addComponent(het5);
		laying.addComponent(het6);
		
		
		
		
		
		het1.addLayoutClickListener(new LayoutClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void layoutClick(LayoutClickEvent event) {
				
				
				
				searchResultsContainer.setSizeUndefined();
				searchResultsContainer.setSpacing(true);
				searchResultsContainer.setMargin(new MarginInfo(false, true, true, true));
				
								
				tb.setWidth("990px");
				
				tb.setContainerDataSource(container);
				tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE_O);
				
				
				
				pnUserSearchResults.setMargin(false);
				pnUserSearchResults.setSpacing(false);
				
				
				laybut.setMargin(false);
				//laybut.setSizeFull();
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
				searchResultsContainer.setComponentAlignment(laybut1, Alignment.TOP_LEFT);
				
				searchResultsContainer.addComponent(laybut);
				searchResultsContainer.addComponent(pnUserSearchResults);
				searchResultsContainer.addComponent(tb);
				searchResultsContainer.addComponent(back);
				searchResultsContainer.setComponentAlignment(back, Alignment.BOTTOM_LEFT);
				
				laying.removeAllComponents();
				laying.addComponent(searchResultsContainer);
				
				
            }
        });
		
		
		filter.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				String selectedId = (String) selection.getValue();
				String hi;
				int i;
				//Indexed filtercontainer2 = new IndexedContainer();
				if (selectedId != null) {
					  
						 filtercontainer = tb.getContainerDataSource();
						 
						 for(i=0; i<2; i++){
						  hi = filtercontainer.getContainerProperty(i+1, "Name").getValue().toString();
						 
							 if(hi.equals(selection.getValue())){
								 filtercontainer.removeItem(i+1);
								 Notification.show(hi+" Noted "+selectedId);
							 }
						 }
						 
						 laying.removeAllComponents();
						 tb.setContainerDataSource(filtercontainer);
						 laying.addComponent(searchResultsContainer);
						 
						  
					 
				}
					
			}
		
		});
		
		back.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
			
				laying.removeAllComponents();
				laying.addComponent(het1);
				laying.addComponent(het2);
				laying.addComponent(het3);
				laying.addComponent(het4);
				laying.addComponent(het5);
				laying.addComponent(het6);
				// TODO Auto-generated method stub
				BulkImport.setEnabled(true);
				adduser.setEnabled(true);
			}
			
			
		});
		
		cancelBulk.addClickListener(new Button.ClickListener() {
			
			

			/** This cancel button is bulk upload
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				//laying.removeAllComponents();
	            //laying.addComponent(searchResultsContainer);
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

			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				
				if(!adduser.isEnabled()){
					
					adduser.setEnabled(true);
					laying.removeComponent(addAccount);
				}
				BulkImport.setEnabled(false);
				layoutFile.removeAllComponents();
				Upload upload = new Upload("Upload CSV File", new Upload.Receiver() {
							      /**
								 * 
								 */
								private static final long serialVersionUID = 1L;
			
								@Override
							      public OutputStream receiveUpload(String filename, String mimeType) {
							        try {
							          /* Here, we'll stored the uploaded file as a temporary file. No doubt there's
							            a way to use a ByteArrayOutputStream, a reader around it, use ProgressListener (and
							            a progress bar) and a separate reader thread to populate a container *during*
							            the update.
							 
							            This is quick and easy example, though.
							            */
							          tempFile = File.createTempFile("temp", ".csv");
							          return new FileOutputStream(tempFile);
							        } catch (IOException e) {
							          e.printStackTrace();
							          return null;
							        }
							      }
							    });
				
							upload.addListener(new Upload.FinishedListener() {
							      /**
								 * 
								 */
								private static final long serialVersionUID = 1L;
			
								@Override
							      public void uploadFinished(Upload.FinishedEvent finishedEvent) {
							        try {
							          FileReader reader = new FileReader(tempFile);
							       
							          @SuppressWarnings("resource")
									CSVReader csvReader = new CSVReader(reader);
							          
							          String[] record;
							          while ((record = csvReader.readNext()) != null) {
							        	  Date newFieldValue2 = new Date();
							        	  
							            tb.setContainerDataSource(het1click(newFieldValue2.toString() ,record[0], record[1], record[2]));
							            
							            //Notification.show("Bulk Creation in progress");
							            
							           
							            laying.removeAllComponents();
							            laying.addComponent(searchResultsContainer);
							            BulkImport.setEnabled(true);
							            Notification.show("Done");			            
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
				
				laying.addComponent(layoutFile,0);
				
				
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		
		adduser.addClickListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = -6399762731213165020L;

			
			@Override
			public void buttonClick(ClickEvent event) {
				
				if(!BulkImport.isEnabled()){
					
					BulkImport.setEnabled(true);
					laying.removeComponent(layoutFile);
				}
				
				
				addAccount.addComponent(nameOfAccount);
				addAccount.addComponent(codeOfAccount);
				addAccount.addComponent(descOfAccount);
				layout2.addComponent(createAccount);
				layout2.addComponent(cancelAccount);
				addAccount.addComponent(layout2);
				//laying.removeAllComponents();
				
				laying.addComponent(addAccount,0);
				//adduser.setDisableOnClick(true);
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
				x=container.size();
				x = x+1;
				adduser.setEnabled(true);
				laying.removeAllComponents();
				Date newFieldValue = new Date();
				tb.setContainerDataSource(het1click(newFieldValue.toString() ,nameOfAccount.getValue(), codeOfAccount.getValue(), descOfAccount.getValue()));
				// TODO Auto-generated method stub
				nameOfAccount.setValue("");codeOfAccount.setValue("");descOfAccount.setValue("");
				laying.addComponent(searchResultsContainer);
				
				filtercontainer2 = tb.getContainerDataSource();
				
			}
			
			
		});
		
		cancelAccount.addClickListener(new Button.ClickListener() {

			/**This button is for Add user
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
		
		return laying;
		
	}
	
	public IndexedContainer het1click(String a, String b, String c, String d) {
				
		//Button actionsC = new Button("Details");		
		
		btnDetails = new BtnActions("Details");
		btnDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		btnEdit = new BtnActions("Edit");
		btnEdit.setIcon(FontAwesome.EDIT);
		btnLink = new BtnActions("Link");
		btnLink.setIcon(FontAwesome.LINK);
		btnDelete = new BtnActions("Delete");
		btnDelete.setIcon(icDelete);
		btnMoreActions = new BtnActions("More...");
		btnMoreActions.setIcon(FontAwesome.ANGLE_RIGHT);
		
		actionsC = new HorizontalLayout();
		actionsC.setSizeUndefined();
		actionsC.setStyleName("c_actions");
		
		actionsC.addComponent(btnDetails);
		actionsC.addComponent(btnEdit);
		actionsC.addComponent(btnLink);
		actionsC.addComponent(btnDelete);
		actionsC.addComponent(btnMoreActions);
		
		Item trItem;
		Object itemId = container.addItem();
		
		
		//container.addItem(e);
		trItem = container.getItem(itemId);
		Property<CheckBox> tdPropertyCheck =trItem.getItemProperty(" ");
		Property<String> tdPropertyUID =trItem.getItemProperty("Date Created");
		Property<String> tdPropertyUname =trItem.getItemProperty("Name");
		Property<String> tdPropertyFname =trItem.getItemProperty("Code");
		Property<String> tdPropertyLname =trItem.getItemProperty("Description");
		Property<HorizontalLayout> tdPropertyActions =trItem.getItemProperty("Actions");
		
		tdPropertyCheck.setValue(new CheckBox());
		tdPropertyUID.setValue(a);
		tdPropertyUname.setValue(b);
		tdPropertyFname.setValue(c);
		tdPropertyLname.setValue(d);
		tdPropertyActions.setValue(actionsC);
		
		
		
	
		
		return container;
		}
	

}


