package br.com.spotippos.auth.repository;

import br.com.spotippos.auth.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by vinic on 25/06/2017.
 */
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    Optional<UserAccount> findByUsername(String username);

}
