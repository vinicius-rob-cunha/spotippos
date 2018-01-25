package br.com.spotippos.auth.repository;

import br.com.spotippos.auth.model.AuthRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<AuthRefreshToken, Integer> {

}
