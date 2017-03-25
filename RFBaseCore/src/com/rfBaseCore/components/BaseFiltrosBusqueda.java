package com.rfBaseCore.components;

import java.util.LinkedHashMap;

public class BaseFiltrosBusqueda {

	private LinkedHashMap<String, Object> filtros;

	public BaseFiltrosBusqueda() {
		filtros = new LinkedHashMap<>();
	}

	public LinkedHashMap<String, Object> getFiltros() {
		return filtros;
	}
	
	public void setFiltros(LinkedHashMap<String, Object> filtros) {
		this.filtros = filtros;
	}

}
