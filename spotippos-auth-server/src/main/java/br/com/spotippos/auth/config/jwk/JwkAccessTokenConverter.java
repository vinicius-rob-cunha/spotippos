package br.com.spotippos.auth.config.jwk;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.client.HttpServerErrorException;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import static org.springframework.security.core.authority.AuthorityUtils.authorityListToSet;

public class JwkAccessTokenConverter extends JwtAccessTokenConverter {

    public static final String SUBJECT = "sub";

    private final JWKManager jwk;

    public JwkAccessTokenConverter(File jwkFile) throws Exception {
        jwk = new JWKManager(jwkFile);
    }

    @Override
    protected String encode(OAuth2AccessToken token, OAuth2Authentication authentication) {
        try {
            JWTClaimsSet.Builder claims = new JWTClaimsSet.Builder()
                    .issuer("spotippos");

            OAuth2Request clientToken = authentication.getOAuth2Request();

            if (!authentication.isClientOnly()) {
                if(token.getAdditionalInformation().containsKey(SUBJECT)) {
                    claims.subject(token.getAdditionalInformation().get(SUBJECT).toString());
                }
                if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
                    claims.claim(AUTHORITIES, authorityListToSet(authentication.getAuthorities()));
                }
            } else {
                if (clientToken.getAuthorities() != null && !clientToken.getAuthorities().isEmpty()) {
                    claims.claim(AUTHORITIES, authorityListToSet(clientToken.getAuthorities()));
                }
            }

            if (token.getScope()!=null) {
                claims.claim(SCOPE, token.getScope());
            }

            if (token.getAdditionalInformation().containsKey(JTI)) {
                claims.jwtID(token.getAdditionalInformation().get(JTI).toString());
            }

            if (token.getExpiration() != null) {
                claims.expirationTime(token.getExpiration());
            }

            token.getAdditionalInformation().entrySet().forEach(entry -> claims.claim(entry.getKey(), entry.getValue()));

            claims.claim(CLIENT_ID, clientToken.getClientId());
            if (clientToken.getResourceIds() != null && !clientToken.getResourceIds().isEmpty()) {
                claims.audience(new ArrayList<>(clientToken.getResourceIds()));
            }

            return jwk.encode(claims.build());

        } catch (JOSEException e) {
            //cai aqui se der erro ao assinar ou criptografar o token
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível realizar o encode do token! Causa: " + e.getMessage());
        }
    }

    @Override
    protected Map<String, Object> decode(String token) {
        JWTClaimsSet jwt = jwk.decode(token);
        return jwt.getClaims();
    }
}
