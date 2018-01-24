package br.com.spotippos.auth.config;

import br.com.spotippos.auth.model.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static br.com.spotippos.auth.config.jwk.JwkAccessTokenConverter.SUBJECT;

@Component
public class AdditionalInformationJWTEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInformation = new HashMap<>();

        //TODO como recuperar o usuário a quem pertence o refresh_token para recupera-lo aqui
        User usuario = ((ResourceOwner) authentication.getPrincipal()).getUser();
        additionalInformation.put("user_data", usuario);
        additionalInformation.put(SUBJECT, usuario.getId());

        DefaultOAuth2AccessToken defaultAccessToken = (DefaultOAuth2AccessToken) accessToken;
        defaultAccessToken.setAdditionalInformation(additionalInformation);

        return defaultAccessToken;
    }

}
