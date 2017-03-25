package com.rfSeries.maestros.controller;

import org.primefaces.event.SelectEvent;

import com.rfBaseCore.controller.BaseController;
import com.rfSeries.maestros.entity.Serie;

public interface SerieController extends BaseController<Serie> {
	public void onSelecCategoriaListener(final SelectEvent event);
	public String delCategoriaAbm();
}
