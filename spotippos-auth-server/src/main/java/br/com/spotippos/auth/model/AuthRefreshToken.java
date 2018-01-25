package br.com.spotippos.auth.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by vinic on 25/06/2017.
 */
@Entity(name="oauth_refresh_token")
public class AuthRefreshToken {

    @Id
    private Integer id;

    public AuthRefreshToken() { /* jpa only */ }

    public AuthRefreshToken(Integer id) { /* jpa only */
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
