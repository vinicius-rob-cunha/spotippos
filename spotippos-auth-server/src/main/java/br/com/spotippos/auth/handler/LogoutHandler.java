package br.com.spotippos.auth.handler;

import br.com.spotippos.auth.config.ResourceOwner;
import br.com.spotippos.auth.model.User;
import br.com.spotippos.auth.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler, LogoutSuccessHandler {

    @Autowired
    private UserAccountService userService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User usuario = ((ResourceOwner) authentication.getPrincipal()).getUser();
        usuario.setAuthenticated(false);
        userService.save(usuario);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectTo;
        if(request.getQueryString() != null && request.getQueryString().contains("client_id")) {
            redirectTo = "/oauth/authorize?" + request.getQueryString();
        } else {
            redirectTo = "/";
        }
        response.sendRedirect(redirectTo);
    }

}
