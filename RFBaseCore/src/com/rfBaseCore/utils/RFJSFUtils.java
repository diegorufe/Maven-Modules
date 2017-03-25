package com.rfBaseCore.utils;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.rfBaseCore.controller.BaseController;

public class RFJSFUtils {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BaseController getController(String controller,Class classController){
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		BaseController baseController = (BaseController) apli.evaluateExpressionGet(context, "#{"+controller+"}",
				classController);
		if (controller == null) {
			try {
				baseController = (BaseController) classController.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return baseController;
	}
	
	
}
