package com.rfBaseCore.controller;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.rfBaseCore.components.BaseBrowser;
import com.rfBaseCore.filtro.BaseFiltro;
import com.rfBaseCore.service.BaseService;
import com.rfBaseCore.session.controller.SessionController;

public interface BaseController<T> {
	public BaseService<T> getService();
	public void setService(BaseService<T> baseService);
	public T getEntity();
	public void setEntity(T entity);
	public SessionController getSessionController();
	public Class<T> getClasegenerica();
	public BaseBrowser<T> getBrowser();
	public void setBrowser(BaseBrowser<T> browser);
	public T newInstance();
	public void newInstanceEntity();
	public void newInstanceEntityCodigo();
	public void setDefaultFiltros();
	public abstract void save();
	public abstract void update();
	public void actionsJoinsTables();
	public LinkedList<BaseFiltro> aplicaFiltrosBusqueda(LinkedHashMap<String, Object> filtros);
	public void resetFiltrosBusqueda();
}
