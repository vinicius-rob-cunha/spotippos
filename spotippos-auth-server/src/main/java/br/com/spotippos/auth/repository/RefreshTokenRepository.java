package br.com.spotippos.auth.repository;

import br.com.spotippos.auth.model.AuthRefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

public interface RefreshTokenRepository extends CrudRepository<AuthRefreshToken, Integer> {

    @Async
    void deleteByExpireAtLessThan(LocalDateTime now);

}
