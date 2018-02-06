package br.com.spotippos.auth.service;

import br.com.spotippos.auth.config.ResourceOwner;
import br.com.spotippos.auth.model.User;
import br.com.spotippos.auth.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by vinic on 25/06/2017.
 */
@Service
public class UserAccountService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userAccountRepository.findByUsername(username);
        return user.map(ResourceOwner::new)
                   .orElseThrow(()
                       -> new UsernameNotFoundException("could not find the user '" + username + "'")
                   );
    }

    public User findByUsername(String username) {
        return userAccountRepository.findByUsername(username).orElse(null);
    }

    public void save(User user) {
        if(user.getId() == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userAccountRepository.save(user);
    }
}
