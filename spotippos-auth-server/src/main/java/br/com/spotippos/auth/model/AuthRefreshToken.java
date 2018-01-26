package br.com.spotippos.auth.model;

import br.com.spotippos.auth.config.OAuth2Config;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static br.com.spotippos.auth.config.OAuth2Config.REFRESH_TOKEN_VALIDITY_SECONDS;

/**
 * Created by vinic on 25/06/2017.
 */
@Entity(name="oauth_refresh_token")
public class AuthRefreshToken {

    @Id
    private Integer id;

    private LocalDateTime expireAt;

    public AuthRefreshToken() { /* jpa only */ }

    public AuthRefreshToken(Integer id) { /* jpa only */
        this.id = id;
        expireAt = LocalDateTime.now().plusSeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }
}
