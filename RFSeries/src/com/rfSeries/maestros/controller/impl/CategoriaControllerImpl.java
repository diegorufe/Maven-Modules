package com.rfSeries.maestros.controller.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.rfBaseCore.constants.EnumTipoFiltro;
import com.rfBaseCore.controller.impl.BaseControllerImpl;
import com.rfBaseCore.dao.BaseDao;
import com.rfBaseCore.filtro.BaseFiltro;
import com.rfBaseCore.security.maestros.entity.Usuario;
import com.rfBaseCore.utils.RFUtils;
import com.rfSeries.maestros.controller.CategoriaController;
import com.rfSeries.maestros.entity.Categoria;

public class CategoriaControllerImpl extends BaseControllerImpl<Categoria> implements CategoriaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4911223090717245665L;

	public CategoriaControllerImpl() {
		super();
	}

	@PostConstruct
	public void initPostConstructor() {
		init();
		LinkedList<BaseFiltro> filtros = new LinkedList<BaseFiltro>();
		Usuario usuario = getSessionController().getUsuarioSession();
		if (usuario != null) {
			BaseFiltro filtroUsuario = new BaseFiltro();
			filtroUsuario.setTipoFiltro(EnumTipoFiltro.UNDEFINED);
			List<BaseFiltro> filtrosUsuario = new ArrayList<>();
			BaseFiltro filtroCrea = new BaseFiltro();
			filtroCrea.setCampo(BaseDao.USUARIO_CREACION);
			filtroCrea.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroCrea.setValue(usuario);
			filtroCrea.setTienenSegundaCondicion(true);
			BaseFiltro filtroMod = new BaseFiltro();
			filtroMod.setCampo(BaseDao.USUARIO_MODIFICACION);
			filtroMod.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroMod.setValue(usuario);
			filtroMod.setTipoFiltro2(EnumTipoFiltro.O);
			filtrosUsuario.add(filtroCrea);
			filtrosUsuario.add(filtroMod);
			filtroUsuario.setFiltros(filtrosUsuario);
			filtros.add(filtroUsuario);
			getService().setDefaultFiltros(filtros);
		}
		getBrowser().getLazyTable().setService(getService());
		String[] columns = { BaseDao.CODIGO, BaseDao.DESCRI };
		String[] columnsSorters = { BaseDao.CODIGO, BaseDao.DESCRI };
		String[] columnsText = { RFUtils.getSimpleNameLower(getClasegenerica()) + "." + BaseDao.CODIGO,
				RFUtils.getSimpleNameLower(getClasegenerica()) + "." + BaseDao.DESCRI };
		getBrowser().getLazyTable().setNumColums(columns.length);
		getBrowser().getLazyTable().generaColumsn(columns, columnsSorters, columnsText);
		getBrowser().getLazyTable().setHeader(RFUtils.getSimpleNameLower(getClasegenerica()) + "." + "header");
	}
	
	
	
	@Override
	public void save() {
		getEntity().setUsuarioCreacion(getSessionController().getUsuarioSession());
		getEntity().setUsuarioModificacion(getSessionController().getUsuarioSession());
		getEntity().setFechaCreacion(new Date());
		getEntity().setFechaModificacion(new Date());
		getService().save(getEntity());
	}

	@Override
	public void update() {
		getEntity().setUsuarioModificacion(getSessionController().getUsuarioSession());
		getEntity().setFechaModificacion(new Date());
		getService().update(getEntity());
	}

}
