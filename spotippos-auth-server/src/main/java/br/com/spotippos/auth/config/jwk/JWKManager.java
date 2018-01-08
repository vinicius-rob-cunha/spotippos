package br.com.spotippos.auth.config.jwk;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;

import java.io.File;
import java.text.ParseException;
import java.util.regex.Pattern;

import static com.nimbusds.jose.EncryptionMethod.A128CBC_HS256;
import static com.nimbusds.jose.JOSEObjectType.JWT;
import static com.nimbusds.jose.JWEAlgorithm.*;
import static com.nimbusds.jose.jwk.KeyType.*;
import static com.nimbusds.jose.jwk.KeyUse.ENCRYPTION;
import static com.nimbusds.jose.jwk.KeyUse.SIGNATURE;

/**
 * Classe que representa o JWK dentro do sistema
 * <p>
 *     Essa classe processa o arquivo {@code .jwk} fazendo o parser e
 *     setando os objetos necessários para gerar e recuperar o token
 * </p>
 * <p>
 *     O JWK deve ter uma chave de assinatura identificada por {@code sig} e opcionalmente uma chave de criptografia
 *     identificada por enc <br />
 *     A identificação é feita pelo parâmetro {@code use}.
 * </p>
 *
 * @author Vinicius Cunha
 */
public class JWKManager {

    public Pattern JWS_PATTERN = Pattern.compile("^[a-zA-Z0-9\\-_]+?\\.[a-zA-Z0-9\\-_]+?\\.([a-zA-Z0-9\\-_]+)?$");
    public Pattern JWE_PATTERN = Pattern.compile("^[a-zA-Z0-9\\-_]+?\\.([a-zA-Z0-9\\-_]+)?\\.[a-zA-Z0-9\\-_]+?\\.[a-zA-Z0-9\\-_]+?\\.([a-zA-Z0-9\\-_]+)?$");
    public static final String NOT_AUTHORIZED = "Falha na autenticação, acesso negado.";

    private JWEHeader jweHeader;
    private JWEEncrypter encrypter;
    private JWEDecrypter decrypter;

    private JWSHeader jwsHeader;
    private JWSSigner signer;
    private JWSVerifier verifier;

    public JWKManager(File file) throws Exception {
        JWKSet localKeys = JWKSet.load(file);

        for(JWK jwk : localKeys.getKeys()) {
            if (jwk.getKeyUse() == ENCRYPTION) {
                setEncryptionKey(jwk);
            } else if(jwk.getKeyUse() == SIGNATURE) {
                setSigningKey(jwk);
            }
        }
    }

    /**
     * Seta o {@code signer}, o {@code verifier} e o {@code jwsHeader} de acordo com o tipo da chave.
     *
     * @param jwk chave
     * @throws JOSEException
     */
    private void setSigningKey(JWK jwk) throws JOSEException {
        KeyType kty = jwk.getKeyType();

        if (kty == EC) {
            signer = new ECDSASigner((ECKey) jwk);
            verifier = new ECDSAVerifier((ECKey) jwk);
        } else if (kty == RSA) {
            signer = new RSASSASigner((RSAKey) jwk);
            verifier = new RSASSAVerifier((RSAKey) jwk);
        } else if (kty == OCT) {
            signer = new MACSigner((OctetSequenceKey) jwk);
            verifier = new MACVerifier((OctetSequenceKey) jwk);
        }

        JWSAlgorithm alg = JWSAlgorithm.parse(jwk.getAlgorithm().getName());
        jwsHeader = new JWSHeader.Builder(alg).type(JWT).build();
    }

    /**
     * Seta o {@code encrypter}, o {@code decrypter} e o {@code jweHeader} de acordo com o tipo da chave
     *
     * @param jwk chave
     * @throws JOSEException
     */
    private void setEncryptionKey(JWK jwk) throws JOSEException {
        KeyType kty = jwk.getKeyType();

        JWEAlgorithm alg = null;

        if (kty == EC) {
            alg = ECDH_ES_A128KW;
            encrypter = new ECDHEncrypter((ECKey) jwk);
            decrypter = new ECDHDecrypter((ECKey) jwk);
        } else if (kty == RSA) {
            alg = RSA_OAEP_256;
            encrypter = new RSAEncrypter((RSAKey) jwk);
            decrypter = new RSADecrypter((RSAKey) jwk);
        } else if (kty == OCT) {
            alg = DIR;
            encrypter = new DirectEncrypter((OctetSequenceKey) jwk);
            decrypter = new DirectDecrypter((OctetSequenceKey) jwk);
        }

        jweHeader = new JWEHeader.Builder(alg, A128CBC_HS256)
                .contentType("JWT")
                .build();
    }

    /**
     * Transforma os Claims passado em token JWT.
     *
     * @param claims claims que irão ser transportados no token
     * @return Token  JWT
     * @throws JOSEException
     */
    public String encode(JWTClaimsSet claims) throws JOSEException {
        JWSObject jws = sign(claims);

        JOSEObject jose = jws;

        if(encrypter != null) {
            jose = encrypt(jws);
        }

        return jose.serialize();
    }

    private JWSObject sign(JWTClaimsSet claims) throws JOSEException {
        JWSObject jws = new SignedJWT(jwsHeader, claims);
        jws.sign(signer);

        return jws;
    }

    private JWEObject encrypt(JWSObject jws) throws JOSEException {
        JWEObject jweObject = new JWEObject(jweHeader, new Payload(jws));
        jweObject.encrypt(encrypter);
        return jweObject;
    }

    /**
     * Faz o parser to token e retorna os claims contidos
     * @param token String representando o token JWT
     * @return os claims contidos
     */
    public JWTClaimsSet decode(String token) {
        try {
            SignedJWT signedJWT;

            if(decrypter != null && JWE_PATTERN.matcher(token).matches()) {
                signedJWT = decrypt(token);
            } else if (verifier != null && JWS_PATTERN.matcher(token).matches()) {
                signedJWT = SignedJWT.parse(token);
            } else {
                throw new InvalidTokenException(NOT_AUTHORIZED);
            }

            signedJWT.verify(verifier);

            return signedJWT.getJWTClaimsSet();
        } catch (ParseException | JOSEException e) {
            throw new InvalidTokenException(NOT_AUTHORIZED);
        }
    }

    private SignedJWT decrypt(String token) throws ParseException, JOSEException {
        JWEObject jweObject = JWEObject.parse(token);
        jweObject.decrypt(decrypter);

        return jweObject.getPayload().toSignedJWT();
    }

}
