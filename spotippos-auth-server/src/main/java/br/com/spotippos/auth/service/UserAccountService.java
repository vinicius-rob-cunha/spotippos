package br.com.spotippos.auth.service;

import br.com.spotippos.auth.model.UserAccount;
import br.com.spotippos.auth.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return userAccountRepository.findByUsername(username)
                .map(this::buildUserForAuthentication)
                .orElseThrow(() -> new UsernameNotFoundException("could not find the user '" + username + "'"));
    }

    private UserDetails buildUserForAuthentication(UserAccount user) {
        List<GrantedAuthority> authorities = getUserAuthority();
        return new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
    }

     private List<GrantedAuthority> getUserAuthority() {
//        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
//        for (Role role : userRoles) {
//            roles.add(new SimpleGrantedAuthority(role.getRole()));
//        }
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
//        return grantedAuthorities;
         return AuthorityUtils.createAuthorityList("USER", "write");
    }

    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username).orElse(null);
    }

    public void saveUser(UserAccount user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userAccountRepository.save(user);
    }
}
