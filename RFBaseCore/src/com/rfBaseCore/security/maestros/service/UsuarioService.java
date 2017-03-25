package com.rfBaseCore.security.maestros.service;

import com.rfBaseCore.security.maestros.entity.Usuario;
import com.rfBaseCore.service.BaseService;

public interface UsuarioService extends BaseService<Usuario> {
	public Usuario validaLogin(String nick, String password);
	public boolean isUsuario(Usuario usario);
	public boolean isUsuarioSinMd5(Usuario usario);
	public void save(Usuario usuario);
	public Usuario getByNick(String nick);
}
