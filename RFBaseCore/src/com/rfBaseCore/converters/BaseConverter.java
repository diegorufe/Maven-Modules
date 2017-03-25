package com.rfBaseCore.converters;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.rfBaseCore.i18n.controller.IdiomaController;
import com.rfBaseCore.i18n.controller.impl.IdiomaControllerImpl;

public abstract class BaseConverter implements Converter {
	
	
	/**
	 * Método para cojer el controlador de idiomas
	 * @return
	 */
	public IdiomaController getIdiomaController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		IdiomaController idiomaController = apli.evaluateExpressionGet(context, "#{idiomaController}",
				IdiomaController.class);
		if (idiomaController == null) {
			idiomaController = new IdiomaControllerImpl();
		}
		return idiomaController;
	}
	
}
