package br.com.spotippos.auth.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Lob;

import static javax.persistence.FetchType.LAZY;

/**
 * Tabela para armazenar os codigos gerados no fluxo de authorization_code
 */
public class AuthCode {

    private String code;

    @Lob @Basic(fetch= LAZY)
    @Column(name="authentication")
    private byte[] authentication;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
}
