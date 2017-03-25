package com.rfSeries.maestros.controller.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.rfBaseCore.constants.EnumTipoFiltro;
import com.rfBaseCore.controller.impl.BaseControllerImpl;
import com.rfBaseCore.dao.BaseDao;
import com.rfBaseCore.filtro.BaseFiltro;
import com.rfBaseCore.security.maestros.entity.Usuario;
import com.rfBaseCore.utils.RFUtils;
import com.rfSeries.maestros.controller.SerieController;
import com.rfSeries.maestros.dao.SerieDao;
import com.rfSeries.maestros.entity.Categoria;
import com.rfSeries.maestros.entity.Serie;

public class SerieControllerImpl extends BaseControllerImpl<Serie> implements SerieController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4911223090717245665L;

	public SerieControllerImpl() {
		super();
	}

	@PostConstruct
	public void initPostConstructor() {
		init();
		loadColumns();
		loadDefaultFiltrosBusqueda();
		getBrowser().resetFiltrosBusqueda();
	}

	/**
	 * Método para cargar columnas
	 */
	private void loadColumns() {
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

	private void loadDefaultFiltrosBusqueda() {
		getBrowser().getFiltrosBusqueda().getFiltros().put(SerieDao.ACTIVA, true);
		getBrowser().getFiltrosBusqueda().getFiltros().put(SerieDao.FINALIZADA, false);
		getBrowser().getFiltrosBusqueda().getFiltros().put(SerieDao.VISTA, true);
		getBrowser().getFiltrosBusqueda().getFiltros().put(SerieDao.DESCRI, "");
		getBrowser().getFiltrosBusqueda().getFiltros().put("sinEstado",false);
	}

	@Override
	public void save() {
		getEntity().setUsuarioCreacion(getSessionController().getUsuarioSession());
		getEntity().setUsuarioModificacion(getSessionController().getUsuarioSession());
		getEntity().setFechaCreacion(new Date());
		getEntity().setFechaModificacion(new Date());
		if (getEntity().getCategoria() == null || getEntity().getCategoria().getId() == null) {
			getEntity().setCategoria(null);
		}
		getService().save(getEntity());
	}

	@Override
	public void update() {
		getEntity().setUsuarioModificacion(getSessionController().getUsuarioSession());
		getEntity().setFechaModificacion(new Date());
		if (getEntity().getCategoria() == null || getEntity().getCategoria().getId() == null) {
			getEntity().setCategoria(null);
		}
		getService().update(getEntity());
	}

	@Override
	public void onSelecCategoriaListener(SelectEvent event) {
		if (event.getObject() != null) {
			Categoria categoria = (Categoria) event.getObject();
			if (categoria != null && categoria.getId() != null) {
				getEntity().setCategoria(categoria);
			}
		}
		RequestContext.getCurrentInstance().execute("PF('CategoriaSel').hide()");
	}

	@Override
	public String delCategoriaAbm() {
		getEntity().setCategoria(new Categoria());
		return null;
	}

	@Override
	public void resetFiltrosBusqueda() {
		getBrowser().getFiltrosBusqueda().getFiltros().clear();
		loadDefaultFiltrosBusqueda();
	}

	@Override
	public LinkedList<BaseFiltro> aplicaFiltrosBusqueda(LinkedHashMap<String, Object> filtros) {
		LinkedList<BaseFiltro> filtrosHql = new LinkedList<BaseFiltro>();
		boolean sinEstado = (boolean) filtros.get("sinEstado");
		if (filtros.containsKey(SerieDao.ACTIVA) && !sinEstado) {
			BaseFiltro filtroActiva = new BaseFiltro();
			filtroActiva.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroActiva.setCampo(SerieDao.ACTIVA);
			filtroActiva.setValue((boolean) filtros.get(SerieDao.ACTIVA));
			filtrosHql.add(filtroActiva);
		}
		if (filtros.containsKey(SerieDao.FINALIZADA) && !sinEstado) {
			BaseFiltro filtroActiva = new BaseFiltro();
			filtroActiva.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroActiva.setCampo(SerieDao.FINALIZADA);
			filtroActiva.setValue((boolean) filtros.get(SerieDao.FINALIZADA));
			filtrosHql.add(filtroActiva);
		}
		if (filtros.containsKey(SerieDao.VISTA) && !sinEstado) {
			BaseFiltro filtroActiva = new BaseFiltro();
			filtroActiva.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroActiva.setCampo(SerieDao.VISTA);
			filtroActiva.setValue((boolean) filtros.get(SerieDao.VISTA));
			filtrosHql.add(filtroActiva);
		}
		if (filtros.containsKey(SerieDao.DESCRI)) {
			if(filtros.get(SerieDao.DESCRI) != null && !((String) filtros.get(SerieDao.DESCRI)).trim().isEmpty()){
				BaseFiltro filtroDescri = new BaseFiltro();
				filtroDescri.setTipoFiltro(EnumTipoFiltro.LIKE_TODO);
				filtroDescri.setCampo(SerieDao.DESCRI);
				filtroDescri.setValue((String) filtros.get(SerieDao.DESCRI));
				filtrosHql.add(filtroDescri);
			}
		}

		return filtrosHql;
	}

}
