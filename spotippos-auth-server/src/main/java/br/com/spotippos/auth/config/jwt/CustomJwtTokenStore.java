package br.com.spotippos.auth.config.jwt;

import br.com.spotippos.auth.config.jwk.JwkAccessTokenConverter;
import br.com.spotippos.auth.model.AuthRefreshToken;
import br.com.spotippos.auth.repository.RefreshTokenRepository;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Component
public class CustomJwtTokenStore extends JwtTokenStore {

    private RefreshTokenRepository refreshTokenRepository;

    public CustomJwtTokenStore(JwkAccessTokenConverter jwkAccessTokenConverter, RefreshTokenRepository refreshTokenRepository) {
        super(jwkAccessTokenConverter);
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        refreshTokenRepository.save(new AuthRefreshToken(refreshToken.getValue().hashCode()));
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        if(!refreshTokenRepository.exists(tokenValue.hashCode()))
            return null;

        return super.readRefreshToken(tokenValue);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        refreshTokenRepository.delete(token.getValue().hashCode());
        refreshTokenRepository.deleteByExpireAtLessThan(now());
    }

}
