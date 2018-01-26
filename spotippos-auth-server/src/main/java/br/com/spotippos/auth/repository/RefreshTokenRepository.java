package br.com.spotippos.auth.repository;

import br.com.spotippos.auth.model.AuthRefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface RefreshTokenRepository extends CrudRepository<AuthRefreshToken, Integer> {

    void deleteByExpireAtLessThan(LocalDateTime now);

}
