package br.com.spotippos.properties.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável por recuperar as informações do token e devolver para o usuario
 *
 * APENAS PARA TESTES DO JWT
 *
 * @author Vinicius Cunha
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity<Object> getTokenUser(Authentication authentication){
        return ResponseEntity.ok(authentication.getPrincipal());
    }

}
