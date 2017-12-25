package br.com.spotippos.properties.model;

import java.security.Principal;
import java.util.Objects;

import static br.com.spotippos.properties.model.AuthUser.AuthUserBuilder.anAuthUser;

/**
 * Usuário logado no sistema. recebido atravédo do Bearer Token
 *
 * @author Vinicius Cunha
 */
public class AuthUser implements Principal {

    private Integer id;
    private String name;
    private String email;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthUser authUser = (AuthUser) o;
        return Objects.equals(id, authUser.id) &&
                Objects.equals(name, authUser.name) &&
                Objects.equals(email, authUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    public static AuthUserBuilder builder(){
        return anAuthUser();
    }

    public static final class AuthUserBuilder {
        private Integer id;
        private String name;
        private String email;

        private AuthUserBuilder() {
        }

        public static AuthUserBuilder anAuthUser() {
            return new AuthUserBuilder();
        }

        public AuthUserBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public AuthUserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AuthUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AuthUser build() {
            AuthUser authUser = new AuthUser();
            authUser.email = this.email;
            authUser.name = this.name;
            authUser.id = this.id;
            return authUser;
        }
    }
}
