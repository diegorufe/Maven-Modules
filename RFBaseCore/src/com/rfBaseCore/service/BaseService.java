package com.rfBaseCore.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.primefaces.model.SortOrder;

import com.rfBaseCore.dao.BaseDao;
import com.rfBaseCore.filtro.BaseFiltro;

public interface BaseService<T> {

	public BaseDao<T> getDao();

	public void setDao(BaseDao<T> genericoDao);

	public void save(T entidad);

	public void update(T entidad);

	public void delete(T entidad);

	public T getEntidad(T entidad);

	public List<T> getAll();

	public List<T> getBySql(String sql);

	public T getByModel(T model);

	public void saveAndMergue(T entidad, List<Object> atributos);

	public int countByQuery(LinkedHashMap<String, SortOrder> orders);

	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, int... iniFin);

	public T getByCodigo(Integer codigo);

	public LinkedList<BaseFiltro> getDefaultFiltros();

	public void setDefaultFiltros(LinkedList<BaseFiltro> defaultFiltros);

	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<BaseFiltro> filtros, int... iniFin);

	public int countByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<BaseFiltro> filtros);

	public T getByCodigo(Integer codigo, LinkedList<BaseFiltro> filtros);

	public Integer getLastCodigo(LinkedList<BaseFiltro> filtros);
}
