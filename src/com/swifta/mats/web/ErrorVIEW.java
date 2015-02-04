package com.swifta.mats.web;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.navigator.ViewProvider;

public class ErrorVIEW implements ViewProvider, View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ErrorVIEW() {
	}

	@Override
	public String getViewName(String viewAndParameters) {

		return "invalid URI";
	}

	@Override
	public View getView(String viewName) {

		return this;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
