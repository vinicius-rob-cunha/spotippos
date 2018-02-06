package br.com.spotippos.auth.handler;

import br.com.spotippos.auth.config.ResourceOwner;
import br.com.spotippos.auth.model.User;
import br.com.spotippos.auth.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserAccountService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User usuario = ((ResourceOwner) authentication.getPrincipal()).getUser();
        usuario.setAuthenticated(true);
        userService.save(usuario);
    }

}
