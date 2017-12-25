package br.com.spotippos.auth;

import br.com.spotippos.auth.model.User;
import br.com.spotippos.auth.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpotipposAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotipposAuthServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserAccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		return (evt) -> Arrays.asList("vinicius,erica,canela,suzy".split(","))
				.forEach(a -> accountRepository.save(new User(a,bCryptPasswordEncoder.encode(a), a+"@teste.com")));
	}

}
