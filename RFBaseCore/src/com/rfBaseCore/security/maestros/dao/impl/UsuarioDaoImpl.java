package com.rfBaseCore.security.maestros.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.rfBaseCore.daoImpl.BaseDaoImpl;
import com.rfBaseCore.security.maestros.dao.UsuarioDao;
import com.rfBaseCore.security.maestros.entity.Usuario;

public class UsuarioDaoImpl extends BaseDaoImpl<Usuario> implements UsuarioDao {

	@Override
	public Usuario getUser(String nick, String password) {
		Query query;
		Usuario usuario = null;

		if (password != null && nick != null) {
			EntityManager entityManager = getEntityManager();
			query = entityManager.createQuery("from Usuario u where u.nick = :nick and u.password = :password",
					Usuario.class);
			query.setParameter("nick", nick);
			query.setParameter("password", password);
			query.setMaxResults(1);
			try {
				usuario = (Usuario) query.getSingleResult();
			} catch (NoResultException ex) {
				usuario = null;
			}
		}

		return usuario;
	}

	@Override
	public Usuario getUserByNick(String nick) {
		Usuario usuario = null;
		EntityManager entityManager = getEntityManager();
		Query query = entityManager.createQuery("from Usuario u where u.nick = :nick", Usuario.class);
		query.setParameter("nick", nick);
		query.setMaxResults(1);
		try {
			usuario = (Usuario) query.getSingleResult();
		} catch (NoResultException ex) {
			usuario = null;
		}
		return usuario;
	}

	@Override
	public String getPasswordUser(String nick) {
		String password = null;
		EntityManager entityManager = getEntityManager();
		Query query = entityManager.createQuery("Select u.password from Usuario u where u.nick = :nick", String.class);
		query.setParameter("nick", nick);
		query.setMaxResults(1);
		try {
			password = (String) query.getSingleResult();
		} catch (NoResultException ex) {
			password = null;
		}
		return password;
	}

}
