package com.swifta.mats.web;

import com.jensjansson.pagedtable.PagedTable;
import com.jensjansson.pagedtable.PagedTableContainer;
import com.vaadin.data.Container;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;

public class PagedTableCustom extends PagedTable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 7906821925280944742L;
	 private PagedTableContainer container;

		public PagedTableCustom(){
			super(null);
		}
		
		public PagedTableCustom(String caption){
			super(caption);
			 setPageLength(2);
		     addStyleName("pagedtable");
		    
			
		}
		
		
	  @Override
	    public void setContainerDataSource(Container newDataSource) {
	        if (!(newDataSource instanceof Container.Indexed)) {
	            throw new IllegalArgumentException(
	                    "PagedTable can only use containers that implement Container.Indexed");
	        }
	        PagedTableContainerCustom pagedTableContainer = new PagedTableContainerCustom(
	                (Container.Indexed) newDataSource);
	        
	        pagedTableContainer.setPageLength(getPageLength());
	        super.setContainerDataSource(pagedTableContainer);
	        super.setContainerDataSource(newDataSource);
	        this.container = pagedTableContainer;
	        
	    }
	
	
		
		@Override
		 public HorizontalLayout createControls() {
			
				Label itemsPerPageLabel = new Label("Items per page:");
		        final ComboBox itemsPerPageSelect = new ComboBox();
		        
		        itemsPerPageSelect.addItem("1");
		        itemsPerPageSelect.addItem("5");
		        itemsPerPageSelect.addItem("10");
		        itemsPerPageSelect.addItem("25");
		        itemsPerPageSelect.addItem("50");
		        itemsPerPageSelect.addItem("100");
		        itemsPerPageSelect.addItem("600");
		        itemsPerPageSelect.setImmediate(true);
		        itemsPerPageSelect.setNullSelectionAllowed(false);
		        itemsPerPageSelect.setWidth("50px");
		        itemsPerPageSelect.addValueChangeListener(new ValueChangeListener() {
		            private static final long serialVersionUID = -2255853716069800092L;

		            public void valueChange(
		                    com.vaadin.data.Property.ValueChangeEvent event) {
		                setPageLength(Integer.valueOf(String.valueOf(event
		                        .getProperty().getValue())));
		            }
		        });
		        itemsPerPageSelect.select("1");
		        Label pageLabel = new Label("Page:&nbsp;", ContentMode.HTML);
		        final TextField currentPageTextField = new TextField();
		        currentPageTextField.setValue(String.valueOf(getCurrentPage()));
		        currentPageTextField.setConverter(Integer.class);
		        final IntegerRangeValidator validator = new IntegerRangeValidator("Wrong page number", 1, getTotalAmountOfPages());
		        currentPageTextField.addValidator(validator);
		        Label separatorLabel = new Label("&nbsp;/&nbsp;", ContentMode.HTML);
		        final Label totalPagesLabel = new Label(
		                String.valueOf(getTotalAmountOfPages()), ContentMode.HTML);
		        currentPageTextField.setStyleName(Reindeer.TEXTFIELD_SMALL);
		        currentPageTextField.setImmediate(true);
		        currentPageTextField.addValueChangeListener(new ValueChangeListener() {
		            private static final long serialVersionUID = -2255853716069800092L;

		            public void valueChange(
		                    com.vaadin.data.Property.ValueChangeEvent event) {
		                if (currentPageTextField.isValid()
		                        && currentPageTextField.getValue() != null) {
		                    int page = Integer.valueOf(String
		                            .valueOf(currentPageTextField.getValue()));
		                    setCurrentPage(page);
		                }
		            }
		        });
		        pageLabel.setWidth(null);
		        currentPageTextField.setWidth("20px");
		        separatorLabel.setWidth(null);
		        totalPagesLabel.setWidth(null);

		        HorizontalLayout controlBar = new HorizontalLayout();
		        HorizontalLayout pageSize = new HorizontalLayout();
		        HorizontalLayout pageManagement = new HorizontalLayout();
		        final Button first = new Button("<<", new ClickListener() {
		            private static final long serialVersionUID = -355520120491283992L;

		            public void buttonClick(ClickEvent event) {
		                setCurrentPage(0);
		            }
		        });
		        final Button previous = new Button("<", new ClickListener() {
		            private static final long serialVersionUID = -355520120491283992L;

		            public void buttonClick(ClickEvent event) {
		                previousPage();
		            }
		        });
		        final Button next = new Button(">", new ClickListener() {
		            private static final long serialVersionUID = -1927138212640638452L;

		            public void buttonClick(ClickEvent event) {
		                nextPage();
		            }
		        });
		        final Button last = new Button(">>", new ClickListener() {
		            private static final long serialVersionUID = -355520120491283992L;

		            public void buttonClick(ClickEvent event) {
		                setCurrentPage(getTotalAmountOfPages());
		            }
		        });
		        first.setStyleName(Reindeer.BUTTON_LINK);
		        previous.setStyleName(Reindeer.BUTTON_LINK);
		        next.setStyleName(Reindeer.BUTTON_LINK);
		        last.setStyleName(Reindeer.BUTTON_LINK);

		        itemsPerPageLabel.addStyleName("pagedtable-itemsperpagecaption");
		        itemsPerPageSelect.addStyleName("pagedtable-itemsperpagecombobox");
		        pageLabel.addStyleName("pagedtable-pagecaption");
		        currentPageTextField.addStyleName("pagedtable-pagefield");
		        separatorLabel.addStyleName("pagedtable-separator");
		        totalPagesLabel.addStyleName("pagedtable-total");
		        first.addStyleName("pagedtable-first");
		        previous.addStyleName("pagedtable-previous");
		        next.addStyleName("pagedtable-next");
		        last.addStyleName("pagedtable-last");

		        itemsPerPageLabel.addStyleName("pagedtable-label");
		        itemsPerPageSelect.addStyleName("pagedtable-combobox");
		        pageLabel.addStyleName("pagedtable-label");
		        currentPageTextField.addStyleName("pagedtable-label");
		        separatorLabel.addStyleName("pagedtable-label");
		        totalPagesLabel.addStyleName("pagedtable-label");
		        first.addStyleName("pagedtable-button");
		        previous.addStyleName("pagedtable-button");
		        next.addStyleName("pagedtable-button");
		        last.addStyleName("pagedtable-button");

		        pageSize.addComponent(itemsPerPageLabel);
		        pageSize.addComponent(itemsPerPageSelect);
		        pageSize.setComponentAlignment(itemsPerPageLabel, Alignment.MIDDLE_LEFT);
		        pageSize.setComponentAlignment(itemsPerPageSelect,
		                Alignment.MIDDLE_LEFT);
		        pageSize.setSpacing(true);
		        pageManagement.addComponent(first);
		        pageManagement.addComponent(previous);
		        pageManagement.addComponent(pageLabel);
		        pageManagement.addComponent(currentPageTextField);
		        pageManagement.addComponent(separatorLabel);
		        pageManagement.addComponent(totalPagesLabel);
		        pageManagement.addComponent(next);
		        pageManagement.addComponent(last);
		        pageManagement.setComponentAlignment(first, Alignment.MIDDLE_LEFT);
		        pageManagement.setComponentAlignment(previous, Alignment.MIDDLE_LEFT);
		        pageManagement.setComponentAlignment(pageLabel, Alignment.MIDDLE_LEFT);
		        pageManagement.setComponentAlignment(currentPageTextField,
		                Alignment.MIDDLE_LEFT);
		        pageManagement.setComponentAlignment(separatorLabel,
		                Alignment.MIDDLE_LEFT);
		        pageManagement.setComponentAlignment(totalPagesLabel,
		                Alignment.MIDDLE_LEFT);
		        pageManagement.setComponentAlignment(next, Alignment.MIDDLE_LEFT);
		        pageManagement.setComponentAlignment(last, Alignment.MIDDLE_LEFT);
		        pageManagement.setWidth(null);
		        pageManagement.setSpacing(true);
		        controlBar.addComponent(pageSize);
		        controlBar.addComponent(pageManagement);
		        controlBar.setComponentAlignment(pageManagement,
		                Alignment.MIDDLE_CENTER);
		        controlBar.setWidth("100%");
		        controlBar.setExpandRatio(pageSize, 1);
		        addListener(new PageChangeListener() {
		            public void pageChanged(PagedTableChangeEvent event) {
		                first.setEnabled(container.getStartIndex() > 0);
		                previous.setEnabled(container.getStartIndex() > 0);
		                next.setEnabled(container.getStartIndex() < container
		                        .getRealSize() - getPageLength());
		                last.setEnabled(container.getStartIndex() < container
		                        .getRealSize() - getPageLength());
		                currentPageTextField.setValue(String.valueOf(getCurrentPage()));
		                totalPagesLabel.setValue(String.valueOf(getTotalAmountOfPages()));
		                itemsPerPageSelect.setValue(String.valueOf(getPageLength()));
		                validator.setMaxValue(getTotalAmountOfPages());
		            }
		        });
		        return controlBar;
			
	
	    }
}
