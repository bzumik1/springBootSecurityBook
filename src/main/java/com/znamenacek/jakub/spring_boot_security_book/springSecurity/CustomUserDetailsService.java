package com.znamenacek.jakub.spring_boot_security_book.springSecurity;

import com.znamenacek.jakub.spring_boot_security_book.models.User;
import com.znamenacek.jakub.spring_boot_security_book.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s)
                .map(user -> new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),getAuthorities(user)))
                .orElseThrow(() -> new UsernameNotFoundException("Email "+s+" not found"));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user){
        String[] userRoles = user.getRoles()
                .stream()
                .map(role -> role.getName())
                .toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(userRoles);
    }
}
