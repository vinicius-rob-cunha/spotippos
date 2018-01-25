package br.com.spotippos.auth.config;

import br.com.spotippos.auth.config.jwk.JwkAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import static java.util.Arrays.asList;

/**
 * Created by vinic on 04/06/2017.
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    public static final String RESOURCE_ID = "spotippos-api";

    /** Representa a chave pública para assinar o payload do jwt */
    public static final String SIGNING_PUBLIC_KEY = "mySuperSecretSigningKey";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AdditionalInformationJWTEnhancer tokenEnhancer;

    @Value("${security.jwk.file}")
    private Resource jwkResource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("spotippos-view")
                .resourceIds(RESOURCE_ID)
                .autoApprove(true) //evita que o usuário precisa conceder acesso, utilizar apenas para aplicações próprias
                .redirectUris("http://localhost:4200") //adicionar outras
                .secret("123456")
                .scopes("read","write")
                .authorities("read", "write")
                .authorizedGrantTypes("refresh_token", "authorization_code", "password");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        DefaultOAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
        requestFactory.setCheckUserScopes(true);

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(asList(tokenEnhancer, accessTokenConverter()));

        endpoints.authenticationManager(authenticationManager)
                 .requestFactory(requestFactory)
                 .tokenStore(jwtTokenStore())
                 .tokenEnhancer(tokenEnhancerChain)
                 .accessTokenConverter(accessTokenConverter())
                 .userDetailsService(userDetailsService);
        ;
    }

    /**
     * Responsável por permitir a leitura de tokens codificados com JWT
     */
    @Bean
    public TokenStore jwtTokenStore() throws Exception {
        JwtTokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
        return tokenStore;
    }

    /**
     * responsável por traduzir tokens codificados com JWT para informações de
     * autenticação OAuth, e vice-versa. Para realizar o trabalho de conversão,
     * essa classe conta com duas outras muito importantes: Signer e SignatureVerifier .
     * Ambas as classes permitem assinar o payload de um token codificado com JWT
     * e verificar a assinatura de um token, respectivamente.
     */
    @Bean
    public JwkAccessTokenConverter accessTokenConverter() throws Exception {
        return new JwkAccessTokenConverter(jwkResource.getFile());
    }

    /**
     * Permite ao fluxo do oAuth2 utilizar o {@link JwtTokenStore}.
     *
     * Anotação @{@link Primary} necessário pois o Spring Security OAuth2 já declara um.
     * Com a anotação o nosso tem prioridade
     */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() throws Exception {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(jwtTokenStore());
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }
}
