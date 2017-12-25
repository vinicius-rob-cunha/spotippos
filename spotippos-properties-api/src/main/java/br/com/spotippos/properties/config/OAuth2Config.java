package br.com.spotippos.properties.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by vinic on 24/06/2017.
 */
@Configuration
@EnableResourceServer
public class OAuth2Config extends ResourceServerConfigurerAdapter {

    /** Representa a chave pública com que o JWT foi assinado pelo auth_server */
    public static final String SIGNING_PUBLIC_KEY = "mySuperSecretSigningKey";
    public static final String RESOURCE_ID = "spotippos-api";

    @Autowired
    private AuthUserTokenConverter userTokenConverter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/properties/**").authenticated()
            .antMatchers(HttpMethod.GET, "/foo").hasAuthority("FOO_READ");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID).tokenStore(tokenStore());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifier(verifier());
        converter.setAccessTokenConverter(defaultAccessTokenConverter());
        converter.setSigningKey(SIGNING_PUBLIC_KEY);
        return converter;
    }

    /**
     * Através do {@link MacSigner} que o converter poderá validar a assinatura.
     * Por padrão ele usa o algoritmo {@code HMACSHA256}
     */
    @Bean
    public SignatureVerifier verifier() {
        return new MacSigner(SIGNING_PUBLIC_KEY);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    public AccessTokenConverter defaultAccessTokenConverter() {
        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        tokenConverter.setUserTokenConverter(userTokenConverter);
        return tokenConverter;
    }

}
