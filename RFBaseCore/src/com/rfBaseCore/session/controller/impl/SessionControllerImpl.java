package com.rfBaseCore.session.controller.impl;

import java.io.IOException;
import java.security.Principal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

import com.rfBaseCore.controller.impl.BaseControllerImpl;
import com.rfBaseCore.i18n.controller.IdiomaController;
import com.rfBaseCore.i18n.controller.impl.IdiomaControllerImpl;
import com.rfBaseCore.security.maestros.entity.Usuario;
import com.rfBaseCore.security.maestros.service.UsuarioService;
import com.rfBaseCore.security.maestros.service.impl.UsuarioServiceImpl;
import com.rfBaseCore.session.controller.SessionController;
import com.rfBaseCore.utils.RFJSFUtils;

public class SessionControllerImpl extends BaseControllerImpl<Usuario> implements SessionController {

	private Usuario usuarioSession;

	@Override
	public Usuario getUsuarioSession() {
		return usuarioSession;
	}

	@Override
	public void setUsuarioSession(Usuario usuarioSession) {
		this.usuarioSession = usuarioSession;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8197775742317572658L;

	@Override
	public String login() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext context = facesContext.getExternalContext();
		try {
			context.dispatch("/j_spring_security_check");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Principal user = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (user != null) {
			UsuarioService usuarioService = (UsuarioService) getService();
			Usuario usuario = usuarioService.getByNick(user.getName());
			this.usuarioSession = usuario;
		} else {
			getIdiomaController().addMessageError("login.error");
			RequestContext.getCurrentInstance().update("messagesLoginG");
		}
		FacesContext.getCurrentInstance().responseComplete();
		return null;
	}

	@Override
	public String logout() {
		this.usuarioSession = null;
		Cookie[] cookies = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				cookie.setValue(null);
				cookie.setPath("/");
				((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse())
						.addCookie(cookie);
			}
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext context = facesContext.getExternalContext();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
		if (httpSession != null) {
			httpSession.invalidate();
		}
		try {
			context.dispatch("/j_spring_security_logout");
		} catch (IOException e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().responseComplete();
		return null;
	}

	@Override
	public void comprubaLoginListener() {
		if (FacesContext.getCurrentInstance().getExternalContext().getSession(false) == null
				|| FacesContext.getCurrentInstance().getExternalContext().getSessionId(false) == null
				|| FacesContext.getCurrentInstance().getExternalContext().getSessionId(false).trim().isEmpty()) {
			FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			RequestContext.getCurrentInstance().update("messagesLoginG");
		}
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (e instanceof BadCredentialsException) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);

			getIdiomaController().addMessageError("login.error");

			RequestContext.getCurrentInstance().update("messagesLoginG");
			e.printStackTrace();
		} else {
			Principal user = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
			if (user != null) {
				Usuario usuario = ((UsuarioServiceImpl) getService()).getByNick(user.getName());
				this.usuarioSession = usuario;
			}

		}
	}

	@Override
	public IdiomaController getIdiomaController() {
		return (IdiomaController) RFJSFUtils.getController("idiomaController", IdiomaControllerImpl.class);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
