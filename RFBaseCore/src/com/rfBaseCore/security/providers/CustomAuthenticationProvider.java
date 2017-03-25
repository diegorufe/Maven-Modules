package com.rfBaseCore.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rfBaseCore.security.maestros.service.UsuarioService;
import com.rfBaseCore.session.controller.SessionController;
import com.rfBaseCore.session.controller.impl.SessionControllerImpl;
import com.rfBaseCore.utils.Cifrado;
import com.rfBaseCore.utils.RFJSFUtils;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userAutenticaionService;

	@Override
	@Transactional
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserDetails user = userAutenticaionService.loadUserByUsername(authentication.getName());
		String password = null;

		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException("Username not found.");
		}

		password = Cifrado.md5((String) authentication.getCredentials());

		if (password == null || password.trim().isEmpty()) {
			throw new BadCredentialsException("Wrong password.");
		}

		if (user == null) {
			throw new BadCredentialsException("Username not found.");
		}
		if (user.getPassword() == null) {
			throw new BadCredentialsException("Wrong password.");
		}

		if (!user.getPassword().equals(password)) {
			throw new BadCredentialsException("Wrong password.");
		}

		SessionController sessionController = (SessionController) RFJSFUtils.getController("sessionController",
				SessionControllerImpl.class);
		sessionController.setUsuarioSession(
				((UsuarioService) sessionController.getService()).getByNick(user.getUsername().trim()));

		return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

	public UserDetailsService getUserAutenticaionService() {
		return userAutenticaionService;
	}

	public void setUserAutenticaionService(UserDetailsService userAutenticaionService) {
		this.userAutenticaionService = userAutenticaionService;
	}

}
