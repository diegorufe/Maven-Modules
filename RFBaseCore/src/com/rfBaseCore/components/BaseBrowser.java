package com.rfBaseCore.components;

import java.util.LinkedList;

import org.primefaces.context.RequestContext;

import com.rfBaseCore.controller.BaseController;
import com.rfBaseCore.filtro.BaseFiltro;

public class BaseBrowser<T> {

	private BaseLazyTable<T> lazyTable;
	private BaseController<T> controller;
	private BaseAbm<T> abm;
	private BaseFiltrosBusqueda filtrosBusqueda;
	private String idModalFiltros;

	public BaseBrowser() {
		lazyTable = new BaseLazyTable<T>();
		abm = new BaseAbm<T>(this);
		filtrosBusqueda = new BaseFiltrosBusqueda();
	}

	public BaseBrowser(BaseController<T> controller) {
		lazyTable = new BaseLazyTable<T>();
		this.controller = controller;
		filtrosBusqueda = new BaseFiltrosBusqueda();
	}

	public void init() {
		this.abm = new BaseAbm<T>(this);
	}

	public BaseLazyTable<T> getLazyTable() {
		return lazyTable;
	}

	public void setLazyTable(BaseLazyTable<T> lazyTable) {
		this.lazyTable = lazyTable;
	}

	public BaseController<T> getController() {
		return controller;
	}

	public void setController(BaseController<T> controller) {
		this.controller = controller;
	}

	public BaseAbm<T> getAbm() {
		return abm;
	}

	public void setAbm(BaseAbm<T> abm) {
		this.abm = abm;
	}

	public BaseFiltrosBusqueda getFiltrosBusqueda() {
		return filtrosBusqueda;
	}

	public void setFiltrosBusqueda(BaseFiltrosBusqueda filtrosBusqueda) {
		this.filtrosBusqueda = filtrosBusqueda;
	}
	
	public String getIdModalFiltros() {
		return idModalFiltros;
	}
	
	public void setIdModalFiltros(String idModalFiltros) {
		this.idModalFiltros = idModalFiltros;
	}
	
	public String goFiltrosBusqueda(String modalFiltros){
		idModalFiltros = modalFiltros;
		RequestContext.getCurrentInstance().execute("PF('"+idModalFiltros+"').show()");
		return null;
	}
	
	public String resetFiltrosBusqueda(){
		this.controller.resetFiltrosBusqueda();
		return this.aplicarFiltrosBusqueda();
	}

	public String aplicarFiltrosBusqueda() {
		if (filtrosBusqueda.getFiltros() != null && filtrosBusqueda.getFiltros().size() > 0) {
			LinkedList<BaseFiltro> filtrosHQL = this.controller.aplicaFiltrosBusqueda(filtrosBusqueda.getFiltros());
			if (filtrosHQL != null) {
				this.lazyTable.setFiltros(filtrosHQL);
			}
		}
		RequestContext.getCurrentInstance().execute("PF('"+idModalFiltros+"').hide()");
		return null;
	}
}
