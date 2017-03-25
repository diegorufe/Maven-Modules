package com.rfBaseCore.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.QueryException;
import org.primefaces.model.SortOrder;
import org.springframework.transaction.annotation.Transactional;

import com.rfBaseCore.constants.EnumTipoFiltro;
import com.rfBaseCore.dao.BaseDao;
import com.rfBaseCore.filtro.BaseFiltro;

public class BaseDaoImpl<T> implements BaseDao<T> {

	@PersistenceContext(name = "GYM")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		// this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(T entidad) {
		entityManager.persist(entidad);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveAndMerge(T entidad, List<Object> atributos) {
		if (atributos != null) {
			for (Object atr : atributos) {
				entityManager.merge(atr);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(T entidad) {
		entityManager.merge(entidad);
	}

	@Override
	public T getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getAll() {
		return entityManager
				.createQuery("select u from " + getClasegenerica().getSimpleName() + " u", getClasegenerica())
				.getResultList();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(T entidad) {
		entityManager.remove(entityManager.contains(entidad) ? entidad : entityManager.merge(entidad));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getClasegenerica() {
		ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) thisType.getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getByCodigo(Integer codigo) {
		T entidad = null;
		Query query = entityManager
				.createQuery("from " + getClasegenerica().getSimpleName() + " e where e.codigo=" + codigo + "");
		query.setMaxResults(1);
		if (codigo != null) {
			try {
				List<T> lista = query.getResultList();
				if (lista != null && lista.size() > 0) {
					entidad = (T) query.getResultList().get(0);
				}
			} catch (Exception e) {
				entidad = null;
			}
		}
		return entidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getByCodigo(Integer codigo, LinkedList<BaseFiltro> filtros) {
		T entidad = null;
		String hql = "from " + getClasegenerica().getSimpleName() + " e where e.codigo=" + codigo + "";
		String hqlFiltros;
		if (filtros != null && filtros.size() > 0) {
			hql = hql + " and ";
			hqlFiltros = getFiltrosSql(filtros, "e");
			hql = hql + hqlFiltros;
		}
		Query query = entityManager.createQuery(hql);
		if (filtros != null && filtros.size() > 0) {
			setFiltrosInQuery(filtros, query);
		}
		query.setMaxResults(1);
		if (codigo != null) {
			try {
				List<T> lista = query.getResultList();
				if (lista != null && lista.size() > 0) {
					entidad = (T) lista.get(0);
				}
			} catch (Exception e) {
				entidad = null;
			}
		}
		return entidad;
	}

	@Override
	public List<T> getBySql(String sql) {
		return entityManager.createQuery(sql, getClasegenerica()).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getListByModel(T model) {
		List<T> lista = null;
		if (model != null) {
			Query query = entityManager.createQuery(
					"SELECT e FROM " + getClasegenerica().getName() + " e where e = :model", getClasegenerica());
			query.setParameter("model", model);
			try {
				update(model);
				lista = query.getResultList();
			} catch (QueryException e) {
				lista = null;
			}
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getByModel(T model) {
		T modelReturn = null;
		if (model != null) {
			Query query = entityManager.createQuery(
					"SELECT e FROM " + getClasegenerica().getName() + " e where e = :model", getClasegenerica());
			query.setParameter("model", model);
			query.setMaxResults(1);
			try {
				update(model);
				modelReturn = (T) query.getSingleResult();
			} catch (QueryException e) {
				modelReturn = null;
			}
		}
		return modelReturn;
	}

	@Override
	public CriteriaQuery<T> createQuery(LinkedHashMap<String, SortOrder> orders) {
		return null;
	}

	@Override
	public CriteriaQuery<T> createQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<BaseFiltro> filtros) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getClasegenerica());
		Root<T> root = criteriaQuery.from(getClasegenerica());
		if (filtros != null && filtros.size() > 0) {
			setFiltrosInCriteria(criteriaQuery, criteriaBuilder, root, filtros);
		}
		LinkedList<Order> orderList = new LinkedList<Order>();
		if (orders != null && orders.size() > 0) {
			LinkedHashSet<String> keysOrder = new LinkedHashSet<String>(orders.keySet());
			for (String key : keysOrder) {
				SortOrder order = orders.get(key);
				if (order != null && !order.equals(SortOrder.UNSORTED)) {
					if (order.equals(SortOrder.ASCENDING)) {
						orderList.add(criteriaBuilder.asc(root.get(key)));
					} else if (order.equals(SortOrder.DESCENDING)) {
						orderList.add(criteriaBuilder.desc(root.get(key)));
					}
				}
			}
		}
		if (orderList != null && orderList.size() > 0) {
			criteriaQuery.orderBy(orderList);
		}
		return criteriaQuery;
	}

	@Override
	public int countByQuery(LinkedHashMap<String, SortOrder> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from(getClasegenerica());
		LinkedList<Order> orderList = new LinkedList<Order>();
		if (orders != null && orders.size() > 0) {
			LinkedHashSet<String> keysOrder = new LinkedHashSet<String>(orders.keySet());
			for (String key : keysOrder) {
				SortOrder order = orders.get(key);
				if (order != null && !order.equals(SortOrder.UNSORTED)) {
					if (order.equals(SortOrder.ASCENDING)) {
						orderList.add(criteriaBuilder.asc(root.get(key)));
					} else if (order.equals(SortOrder.DESCENDING)) {
						orderList.add(criteriaBuilder.desc(root.get(key)));
					}
				}
			}
		}
		if (orderList != null && orderList.size() > 0) {
			criteriaQuery.orderBy(orderList);
		}
		criteriaQuery.select(criteriaBuilder.count(root));
		Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result.intValue() : 0;
	}

	@Override
	public int countByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<BaseFiltro> filtros) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from(getClasegenerica());
		criteriaQuery.select(criteriaBuilder.count(root));

		if (filtros != null && filtros.size() > 0) {
			setFiltrosInCriteria(criteriaQuery, criteriaBuilder, root, filtros);
		}

		LinkedList<Order> orderList = new LinkedList<Order>();
		if (orders != null && orders.size() > 0) {
			LinkedHashSet<String> keysOrder = new LinkedHashSet<String>(orders.keySet());
			for (String key : keysOrder) {
				SortOrder order = orders.get(key);
				if (order != null && !order.equals(SortOrder.UNSORTED)) {
					if (order.equals(SortOrder.ASCENDING)) {
						orderList.add(criteriaBuilder.asc(root.get(key)));
					} else if (order.equals(SortOrder.DESCENDING)) {
						orderList.add(criteriaBuilder.desc(root.get(key)));
					}
				}
			}
		}
		if (orderList != null && orderList.size() > 0) {
			criteriaQuery.orderBy(orderList);
		}
		Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result.intValue() : 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, int... iniFin) {
		List<T> data = null;
		Query query = entityManager.createQuery(createQuery(orders));
		if (iniFin != null && iniFin.length > 0 && iniFin.length <= 2) {
			query.setFirstResult(iniFin[0]);
			query.setMaxResults(iniFin[1]);
		}
		try {
			data = query.getResultList();
		} catch (Exception e) {
			data = null;
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<BaseFiltro> filtros, int... iniFin) {
		List<T> data = null;
		Query query = entityManager.createQuery(createQuery(orders, filtros));
		if (iniFin != null && iniFin.length > 0 && iniFin.length <= 2) {
			query.setFirstResult(iniFin[0]);
			query.setMaxResults(iniFin[1]);
		}
		try {
			data = query.getResultList();
		} catch (Exception e) {
			data = null;
		}
		return data;
	}

	private String getFiltrosSql(LinkedList<BaseFiltro> filtros, String aliasDefect) {
		boolean first = false;
		StringBuilder builder = new StringBuilder();
		String cond;
		String campo;
		String alias;
		String value;
		for (BaseFiltro filtro : filtros) {
			if (filtro.getTipoFiltro() != null) {
				if (filtro.getFiltros() != null && filtro.getFiltros().size() > 0) {
					builder.append("(");
					for (BaseFiltro filtroFiltro : filtro.getFiltros()) {
						if (filtroFiltro.getTipoFiltro2() != null
								&& filtroFiltro.getTipoFiltro2() != EnumTipoFiltro.UNDEFINED) {
							switch (filtroFiltro.getTipoFiltro2()) {
							case O:
								builder.append(" or ");
								break;
							case Y:
								builder.append(" and ");
							default:
								break;
							}
						}
						switch (filtroFiltro.getTipoFiltro()) {
						case IGUAL:
							cond = first ? " and " : " ";
							alias = " " + (filtroFiltro.getAlias() == null ? aliasDefect : filtroFiltro.getAlias())
									+ ".";
							campo = filtroFiltro.getCampo() + " ";
							value = ":" + filtroFiltro.getCampo() + "pr";
							builder.append(cond + alias + campo + " = " + value + " ");
							break;

						default:
							break;
						}
					}
					builder.append(")");
				} else {
					if (!first) {
						first = true;
					}
					switch (filtro.getTipoFiltro()) {
					case IGUAL:
						cond = first ? " and " : " ";
						alias = " " + (filtro.getAlias() == null ? aliasDefect : filtro.getAlias()) + ".";
						campo = filtro.getCampo() + " ";
						value = ":" + filtro.getCampo() + "pr";
						builder.append(cond + alias + campo + " = " + value + " ");
						break;

					default:
						break;
					}
				}
			}
		}
		return builder.toString();
	}

	private void setFiltrosInQuery(LinkedList<BaseFiltro> filtros, Query query) {
		for (BaseFiltro filtro : filtros) {
			if (filtro.getFiltros() != null && filtro.getFiltros().size() > 0) {
				for (BaseFiltro filtroFiltro : filtro.getFiltros()) {
					query.setParameter(filtroFiltro.getCampo() + "pr", filtroFiltro.getValue());
				}
			} else {
				query.setParameter(filtro.getCampo() + "pr", filtro.getValue());
			}

		}
	}

	@SuppressWarnings("rawtypes")
	private void setFiltrosInCriteria(CriteriaQuery criteria, CriteriaBuilder builder, Root<T> root,
			LinkedList<BaseFiltro> filtros) {
		if (filtros != null && filtros.size() > 0) {
			LinkedList<Predicate> restrinctions = new LinkedList<Predicate>();
			Expression<String> expresion = null;
			Expression<Date> campoFecha = null;
			Date fecha1 = null;
			Date fecha2 = null;
			// Recorremos los filtros
			for (BaseFiltro filtro : filtros) {
				if (filtro.getTipoFiltro() != null) {
					// Si no tiene filtros entre parentesis
					if (filtro.getFiltros() == null || filtro.getFiltros().size() <= 0) {
						expresion = root.get(filtro.getCampo());
						switch (filtro.getTipoFiltro()) {
						case LIKE_TODO:
							if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {

							} else {
								restrinctions.add(builder.like(expresion, "%" + filtro.getValue() + "%"));
							}
							break;
						case ENTRE:
							if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {

							} else {
								if (filtro.getValue() instanceof Date) {
									campoFecha = root.get(filtro.getCampo());
									fecha1 = (Date) filtro.getValue();
									fecha2 = (Date) filtro.getValue2();
									restrinctions.add(builder.between(campoFecha, builder.literal(fecha1),
											builder.literal(fecha2)));
								}
							}
							break;
						case IGUAL:
							if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {
								expresion = root.join(filtro.getCampo(), filtro.getJoin()).get(filtro.getCampoJoin());
								restrinctions.add(builder.equal(expresion, filtro.getValue()));
							} else {
								restrinctions.add(builder.equal(expresion, filtro.getValue()));
							}
							break;
						case DISTINTO:
							if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {
								expresion = root.join(filtro.getCampo(), filtro.getJoin()).get(filtro.getCampoJoin());
								restrinctions.add(builder.notEqual(expresion, filtro.getValue()));
							} else {
								restrinctions.add(builder.notEqual(expresion, filtro.getValue()));
							}
							break;
						default:
							break;
						}
						// Si tiene filtros entre parentesis
					} else if (filtro.getFiltros() != null && filtro.getFiltros().size() > 0) {
						setFiltroInCriteriaEntreParentesis(criteria, builder, root, filtro.getFiltros(), restrinctions);
					}
				}
			}
			criteria.where(restrinctions.toArray(new Predicate[restrinctions.size()]));
		}
	}

	@SuppressWarnings({ "incomplete-switch", "rawtypes" })
	private void setFiltroInCriteriaEntreParentesis(CriteriaQuery criteria, CriteriaBuilder builder, Root<T> root,
			List<BaseFiltro> filtros, LinkedList<Predicate> restrinctions) {
		Expression<String> expresion = null;
		Expression<Date> campoFecha = null;
		Date fecha1 = null;
		Date fecha2 = null;
		Predicate predicate1 = null;
		Predicate predicate2 = null;
		for (BaseFiltro filtro : filtros) {
			if (filtro.getFiltros() != null && filtro.getFiltros().size() > 0) {
				setFiltroInCriteriaEntreParentesis(criteria, builder, root, filtro.getFiltros(), restrinctions);
			} else {
				predicate2 = null;
				if (filtro.getTipoFiltro() != null) {
					expresion = root.get(filtro.getCampo());

					switch (filtro.getTipoFiltro()) {
					case IGUAL:
						if (filtro.getTipoFiltro2() == null) {
							if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {
								expresion = root.join(filtro.getCampo(), filtro.getJoin()).get(filtro.getCampoJoin());
								predicate1 = builder.equal(expresion, filtro.getValue());
							} else {
								predicate1 = builder.equal(expresion, filtro.getValue());
							}
						}
						if (filtro.getTipoFiltro2() != null) {

							switch (filtro.getTipoFiltro2()) {
							case Y:
								if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {
									expresion = root.join(filtro.getCampo(), filtro.getJoin())
											.get(filtro.getCampoJoin());
									predicate2 = builder.and(predicate1, builder.equal(expresion, filtro.getValue()));
								} else {
									predicate2 = builder.and(predicate1, builder.equal(expresion, filtro.getValue()));
								}
								break;
							case O:
								if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {
									expresion = root.join(filtro.getCampo(), filtro.getJoin())
											.get(filtro.getCampoJoin());
									predicate2 = builder.or(predicate1, builder.equal(expresion, filtro.getValue()));
								} else {
									predicate2 = builder.or(predicate1, builder.equal(expresion, filtro.getValue()));
								}
								break;
							}
						}
						break;
					case ENTRE:
						if (filtro.getValue() instanceof Date) {
							campoFecha = root.get(filtro.getCampo());
							fecha1 = (Date) filtro.getValue();
							fecha2 = (Date) filtro.getValue2();
							if (filtro.getTipoFiltro2() == null) {
								if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {

								} else {
									predicate1 = builder.between(campoFecha, builder.literal(fecha1),
											builder.literal(fecha2));
								}
							}
							if (filtro.getTipoFiltro2() != null) {

								switch (filtro.getTipoFiltro2()) {
								case Y:
									if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {

									} else {
										predicate2 = builder.and(predicate1, builder.between(campoFecha,
												builder.literal(fecha1), builder.literal(fecha2)));
									}
									break;
								case O:
									if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {

									} else {
										predicate2 = builder.or(predicate1, builder.between(campoFecha,
												builder.literal(fecha1), builder.literal(fecha2)));
									}
									break;
								}
							}
						}
						break;
					}
				}
				if (predicate2 == null && predicate1 != null && !filtro.isTienenSegundaCondicion()) {
					restrinctions.add(predicate1);
					predicate1 = null;
				} else if (filtro.isTienenSegundaCondicion() && predicate2 != null) {
					predicate1 = null;
					predicate1 = predicate2;
				}
				if (predicate2 != null) {
					restrinctions.add(predicate2);
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer getLastCodigo(LinkedList<BaseFiltro> filtros) {
		Integer lastCodigo = 0;
		String hql = "Select e.codigo from " + getClasegenerica().getSimpleName() + " e ";
		String hqlFiltros;
		if (filtros != null && filtros.size() > 0) {
			hql = hql + " Where ";
			hqlFiltros = getFiltrosSql(filtros, "e");
			hql = hql + hqlFiltros;
		}
		hql = hql + " order by e.codigo desc ";
		Query query = entityManager.createQuery(hql);
		if (filtros != null && filtros.size() > 0) {
			setFiltrosInQuery(filtros, query);
		}
		query.setMaxResults(1);
		try {
			List lista = query.getResultList();
			if (lista != null && lista.size() > 0) {
				lastCodigo = (Integer) lista.get(0);
				lastCodigo++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastCodigo;
	}

}
