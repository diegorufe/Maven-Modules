package com.rfBaseCore.i18n.controller.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.rfBaseCore.constants.ConstantesAplicacion;
import com.rfBaseCore.controller.impl.BaseControllerImpl;
import com.rfBaseCore.i18n.controller.IdiomaController;

public class IdiomaControllerImpl extends BaseControllerImpl<Object> implements IdiomaController {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6967280437102170965L;
	private List<Locale> supportedLocales;
	private Locale locale;

	public IdiomaControllerImpl() {
		supportedLocales = new ArrayList<Locale>();
		supportedLocales.add(new Locale("es", "ES"));
		locale = new Locale("es", "ES");
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		this.locale = locale;
	}

	public List<Locale> getSupportedLocales() {
		return supportedLocales;
	}

	@Override
	public void addMessageError(String error) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, ConstantesAplicacion.I18N);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString(error), null);
		context.addMessage(null, message);
	}

	@Override
	public void addMessageInfo(String info) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, ConstantesAplicacion.I18N);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString(info), null);
		context.addMessage(null, message);
	}

	@Override
	public String getTraducion(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, ConstantesAplicacion.I18N);
		return bundle.getString(msg);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}



}
