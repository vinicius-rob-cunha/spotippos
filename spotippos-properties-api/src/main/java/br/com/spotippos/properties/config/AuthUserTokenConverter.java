package br.com.spotippos.properties.config;

import br.com.spotippos.properties.model.AuthUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe responsavel por extrair do token as informações do usuário
 * e transformar em objeto para uso na aplicação.
 *
 * <p>Implementação baseada na {@link org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter}</p>
 *
 * @author Vinicius Cunha
 */
@Component
public class AuthUserTokenConverter implements UserAuthenticationConverter {

    public static final String USER_DATA = "user_data";

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if(map.containsKey(USER_DATA)) {
            Map<String, Object> userData = (Map<String, Object>) map.get(USER_DATA);
            AuthUser principal = AuthUser.builder().id((Integer) userData.get("id"))
                                                   .name((String) userData.get("username"))
                                                   .email((String) userData.get("email"))
                                                   .build();
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        }
        throw new IllegalArgumentException("user_data_not_found");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}
