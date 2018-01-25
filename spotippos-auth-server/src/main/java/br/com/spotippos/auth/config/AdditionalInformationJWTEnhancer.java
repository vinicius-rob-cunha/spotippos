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

    public static final String USER_DATA = "user_data";

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User usuario = ((ResourceOwner) authentication.getPrincipal()).getUser();

        Map<String, Object> additionalInformation = new HashMap<>();
        additionalInformation.put(USER_DATA, usuario);
        additionalInformation.put(SUBJECT, usuario.getId());

        DefaultOAuth2AccessToken defaultAccessToken = (DefaultOAuth2AccessToken) accessToken;
        defaultAccessToken.setAdditionalInformation(additionalInformation);

        return defaultAccessToken;
    }

}
