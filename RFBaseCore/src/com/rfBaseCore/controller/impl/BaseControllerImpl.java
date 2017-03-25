package com.rfBaseCore.controller.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import com.rfBaseCore.components.BaseBrowser;
import com.rfBaseCore.controller.BaseController;
import com.rfBaseCore.filtro.BaseFiltro;
import com.rfBaseCore.service.BaseService;
import com.rfBaseCore.session.controller.SessionController;
import com.rfBaseCore.session.controller.impl.SessionControllerImpl;
import com.rfBaseCore.utils.RFJSFUtils;

public abstract class BaseControllerImpl<T> implements BaseController<T>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1154340323760574869L;
	private BaseService<T> service;
	private T entity;
	private BaseBrowser<T> browser;
	
	public BaseControllerImpl() {
		try {
			entity = getClasegenerica().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	@PostConstruct
	public void init(){
		browser = new BaseBrowser<T>(this);
		browser.init();
		
	}
	
	@Override
	public BaseService<T> getService() {
		return this.service;
	}

	@Override
	public void setService(BaseService<T> baseService) {
		this.service = baseService;
	}

	@Override
	public T getEntity() {
		return this.entity;
	}

	@Override
	public void setEntity(T entity) {
		this.entity = entity;
	}

	@Override
	public SessionController getSessionController()  {
		return (SessionController) RFJSFUtils.getController("sessionController", SessionControllerImpl.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getClasegenerica() {
		ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) thisType.getActualTypeArguments()[0];
	}

	@Override
	public BaseBrowser<T> getBrowser() {
		return browser;
	}

	@Override
	public void setBrowser(BaseBrowser<T> browser) {
		this.browser = browser;
	}

	@Override
	public T newInstance() {
		T entity = null;
		try {
			entity = getClasegenerica().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public void newInstanceEntity() {
		entity = null;
		try {
			entity = getClasegenerica().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void newInstanceEntityCodigo() {
		entity = null;
		try {
			entity = getClasegenerica().getConstructor(Integer.class).newInstance(getService().getLastCodigo(null));
		} catch (InstantiationException e) {
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (NoSuchMethodException e) {
			entity = null;
			//e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	@Override
	public void setDefaultFiltros() {	
	}
	
	@Override
	public void actionsJoinsTables() {
	}

	@Override
	public abstract  void save();

	@Override
	public abstract void update();
	
	@Override
	public LinkedList<BaseFiltro> aplicaFiltrosBusqueda(LinkedHashMap<String, Object> filtros) {
		return null;
	}
	
	@Override
	public void resetFiltrosBusqueda() {
	}

}
