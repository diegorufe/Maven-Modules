package com.rfBaseCore.i18n.controller;

import com.rfBaseCore.controller.BaseController;

public interface IdiomaController extends BaseController<Object> {
	public void addMessageError(String error);
	public void addMessageInfo(String info);
	public String getTraducion(String msg);
}
