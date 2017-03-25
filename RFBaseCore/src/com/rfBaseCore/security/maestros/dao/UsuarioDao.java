package com.rfBaseCore.security.maestros.dao;
import com.rfBaseCore.dao.BaseDao;
import com.rfBaseCore.security.maestros.entity.Usuario;

public interface UsuarioDao extends BaseDao<Usuario> {
	public Usuario getUser(String nick, String password);
	public Usuario getUserByNick(String nick);
	public String getPasswordUser(String nick);
	public void save(Usuario usuario);
}
