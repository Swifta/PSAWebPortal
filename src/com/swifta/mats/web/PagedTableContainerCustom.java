package com.swifta.mats.web;

import com.jensjansson.pagedtable.PagedTableContainer;
import com.vaadin.data.Container;

public class PagedTableContainerCustom extends PagedTableContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7842521856250911581L;
	private int pageLength = 1;
	
	public PagedTableContainerCustom(Indexed container) {
		super(container);
	}
	
	@Override
	public int getPageLength() {
	        return pageLength;
	   }

}
