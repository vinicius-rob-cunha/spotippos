package br.com.spotippos.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by vinic on 25/06/2017.
 */
@Entity
public class UserAccount {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    private String password;
    private String username;
    private String email;

    public UserAccount() { /* jpa only */ }

    public UserAccount(String name, String password, String email) {
        this.username = name;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
