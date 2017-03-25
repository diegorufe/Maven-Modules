package com.rfSeries.core.controller;

import com.rfBaseCore.controller.BaseController;

public interface NavigationController extends BaseController<Object> {
	public String goLoginUrl();
	public String goCategoriasUrl();
	public String goSeriesUrl();
	public String goHomeUrl();
}
