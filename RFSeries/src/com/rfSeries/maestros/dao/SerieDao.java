package com.rfSeries.maestros.dao;

import com.rfBaseCore.dao.BaseDao;
import com.rfSeries.maestros.entity.Serie;

public interface SerieDao extends BaseDao<Serie> {
	public static final String ACTIVA = "activa";
	public static final String FINALIZADA = "finalizada";
	public static final String VISTA = "vista";
	public static final String CAPITULO = "capitulo";
	public static final String CAPTIULOS = "capitulos";
	public static final String TEMPORADA = "temporada";
	public static final String TEMPORADAS = "temporadas";
}
