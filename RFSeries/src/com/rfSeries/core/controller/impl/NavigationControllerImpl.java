package com.rfSeries.core.controller.impl;

import com.rfBaseCore.controller.impl.BaseControllerImpl;
import com.rfSeries.core.constants.NavigationConstants;
import com.rfSeries.core.controller.NavigationController;

public class NavigationControllerImpl extends BaseControllerImpl<Object> implements NavigationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5554113912751934339L;

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public String goLoginUrl() {
		return NavigationConstants.LOGIN_URL;
	}

	@Override
	public String goCategoriasUrl() {
		return NavigationConstants.CATEGORIAS_URL;
	}

	@Override
	public String goSeriesUrl() {
		return NavigationConstants.SERIES_URL;
	}

	@Override
	public String goHomeUrl() {
		return NavigationConstants.HOME_URL;
	}

}
