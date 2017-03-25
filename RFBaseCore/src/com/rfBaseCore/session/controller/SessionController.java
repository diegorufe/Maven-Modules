package com.rfBaseCore.session.controller;

import com.rfBaseCore.controller.BaseController;
import com.rfBaseCore.i18n.controller.IdiomaController;
import com.rfBaseCore.security.maestros.entity.Usuario;
public interface SessionController extends BaseController<Usuario>{
	public Usuario getUsuarioSession();
	public void setUsuarioSession(Usuario usuarioSession);
	public String login();
	public String logout();
	public IdiomaController getIdiomaController();
	public void comprubaLoginListener();
}
