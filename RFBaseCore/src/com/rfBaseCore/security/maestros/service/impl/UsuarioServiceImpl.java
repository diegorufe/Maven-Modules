package com.rfBaseCore.security.maestros.service.impl;

import org.springframework.stereotype.Service;

import com.rfBaseCore.security.maestros.dao.UsuarioDao;
import com.rfBaseCore.security.maestros.entity.Usuario;
import com.rfBaseCore.security.maestros.service.UsuarioService;
import com.rfBaseCore.serviceImpl.BaseServiceImpl;
import com.rfBaseCore.utils.Cifrado;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario> implements UsuarioService {

	public UsuarioServiceImpl() {
	}

	@Override
	public Usuario validaLogin(String nick, String password) {
		return ((UsuarioDao) getDao()).getUser(nick, Cifrado.md5(password));
	}

	@Override
	public boolean isUsuario(Usuario usuario) {
		boolean existe = false;
		Usuario usuarioExiste = ((UsuarioDao) getDao()).getUser(usuario.getNick(), Cifrado.md5(usuario.getPassword()));
		if (usuarioExiste != null) {
			existe = true;
		}
		usuarioExiste = ((UsuarioDao) getDao()).getUserByNick(usuario.getNick());
		if (usuarioExiste != null) {
			existe = true;
		}
		return existe;
	}

	@Override
	public void save(Usuario usuario) {
		((UsuarioDao) getDao()).save(usuario);
	}

	@Override
	public Usuario getByNick(String nick) {
		return ((UsuarioDao) getDao()).getUserByNick(nick);
	}

	@Override
	public boolean isUsuarioSinMd5(Usuario usuario) {
		boolean existe = false;
		Usuario usuarioExiste = ((UsuarioDao) getDao()).getUser(usuario.getNick(), usuario.getPassword());
		if (usuarioExiste != null) {
			existe = true;
		}
		usuarioExiste = ((UsuarioDao) getDao()).getUserByNick(usuario.getNick());
		if (usuarioExiste != null) {
			existe = true;
		}
		return existe;
	}

}
