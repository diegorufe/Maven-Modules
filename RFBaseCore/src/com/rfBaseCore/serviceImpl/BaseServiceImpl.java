package com.rfBaseCore.serviceImpl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.primefaces.model.SortOrder;

import com.rfBaseCore.dao.BaseDao;
import com.rfBaseCore.filtro.BaseFiltro;
import com.rfBaseCore.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> dao;
	private LinkedList<BaseFiltro> defaultFiltros;

	@Override
	public BaseDao<T> getDao() {
		return dao;
	}

	@Override
	public void setDao(BaseDao<T> genericoDao) {
		this.dao = genericoDao;
	}

	@Override
	public void save(T entidad) {
		dao.save(entidad);
	}

	@Override
	public void update(T entidad) {
		this.dao.update(entidad);

	}

	@Override
	public void delete(T entidad) {
		dao.delete(entidad);
	}

	@Override
	public T getEntidad(T entidad) {
		return dao.getByModel(entidad);
	}

	@Override
	public List<T> getAll() {
		return dao.getAll();
	}

	@Override
	public List<T> getBySql(String sql) {
		return dao.getBySql(sql);
	}

	@Override
	public T getByModel(T model) {
		return (T) dao.getByModel(model);
	}

	@Override
	public void saveAndMergue(T entidad, List<Object> atributos) {
		dao.saveAndMerge(entidad, atributos);
	}

	@Override
	public int countByQuery(LinkedHashMap<String, SortOrder> orders) {
		return dao.countByQuery(orders);
	}

	@Override
	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, int... iniFin) {
		return dao.findByQuery(orders, iniFin);
	}

	@Override
	public T getByCodigo(Integer codigo) {
		return dao.getByCodigo(codigo);
	}

	@Override
	public LinkedList<BaseFiltro> getDefaultFiltros() {
		return defaultFiltros;
	}

	@Override
	public void setDefaultFiltros(LinkedList<BaseFiltro> defaultFiltros) {
		this.defaultFiltros = defaultFiltros;

	}

	@Override
	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<BaseFiltro> filtros, int... iniFin) {
		LinkedList<BaseFiltro> filtrosEnviar = new LinkedList<>();
		if (defaultFiltros != null) {
			filtrosEnviar.addAll(defaultFiltros);
		}
		if (filtros != null) {
			filtrosEnviar.addAll(filtros);
		}
		return dao.findByQuery(orders, filtrosEnviar, iniFin);
	}

	@Override
	public int countByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<BaseFiltro> filtros) {
		LinkedList<BaseFiltro> filtrosEnviar = new LinkedList<>();
		if (defaultFiltros != null) {
			filtrosEnviar.addAll(defaultFiltros);
		}
		if (filtros != null) {
			filtrosEnviar.addAll(filtros);
		}
		return dao.countByQuery(orders, filtrosEnviar);
	}

	@Override
	public T getByCodigo(Integer codigo, LinkedList<BaseFiltro> filtros) {
		LinkedList<BaseFiltro> filtrosEnviar = new LinkedList<>();
		if (defaultFiltros != null && defaultFiltros.size() > 0) {
			filtrosEnviar.addAll(defaultFiltros);
		}
		if (filtros != null && filtros.size() > 0) {
			filtrosEnviar.addAll(filtros);
		}
		return dao.getByCodigo(codigo, filtrosEnviar);
	}

	@Override
	public Integer getLastCodigo(LinkedList<BaseFiltro> filtros) {
		LinkedList<BaseFiltro> filtrosEnviar = new LinkedList<>();
		if (defaultFiltros != null && defaultFiltros.size() > 0) {
			filtrosEnviar.addAll(defaultFiltros);
		}
		if (filtros != null && filtros.size() > 0) {
			filtrosEnviar.addAll(filtros);
		}
		return dao.getLastCodigo(filtrosEnviar);
	}

}
