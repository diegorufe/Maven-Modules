package com.rfBaseCore.dao;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaQuery;

import org.primefaces.model.SortOrder;

import com.rfBaseCore.filtro.BaseFiltro;

public interface BaseDao<T> {
	
	public static final String ID = "id";
	public static final String CODIGO = "codigo";
	public static final String USUARIO_CREACION = "usuarioCreacion";
	public static final String USUARIO_MODIFICACION = "usuarioModificacion";
	public static final String FECHA_CREACION = "fechaCreacion";
	public static final String FECHA_MODIFICACION = "fechaModificacion";
	public static final String DESCRI = "descri";

	public EntityManager getEntityManager();

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory);

	public void setEntityManager(EntityManager entityManager);

	public void save(T entidad);

	public void saveAndMerge(T entidad, List<Object> atributos);

	public void update(T entidad);

	public T getById(String id);

	public List<T> getAll();

	public void delete(T entidad);

	public Class<T> getClasegenerica();

	public T getByCodigo(Integer codigo);

	public T getByCodigo(Integer codigo, LinkedList<BaseFiltro> filtros);

	public List<T> getBySql(String sql);

	public List<T> getListByModel(T model);
	
	public T getByModel(T model);

	public CriteriaQuery<T> createQuery(LinkedHashMap<String, SortOrder> orders);

	public CriteriaQuery<T> createQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<BaseFiltro> filtros);

	public int countByQuery(LinkedHashMap<String, SortOrder> orders);

	public int countByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<BaseFiltro> filtros);

	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, int... iniFin);

	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<BaseFiltro> filtros, int... iniFin);
	
	public Integer getLastCodigo( LinkedList<BaseFiltro> filtros);
}
