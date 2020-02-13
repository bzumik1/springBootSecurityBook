package com.znamenacek.jakub.spring_boot_security_test.security.authentication;

import com.znamenacek.jakub.spring_boot_security_test.security.authentication.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass=Role.class, fetch = FetchType.EAGER) // WHY EAGER TYPE, MAYBE BECOUSE OF STREAM
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_role")
    @Column(name = "role")
    private Set<Role> roles;

    @Column(name = "account_non_expired", columnDefinition = "boolean default true")
    public boolean accountNonExpired;

    @Column(name = "account_non_locked", columnDefinition = "boolean default true")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired", columnDefinition = "boolean default true")
    public boolean credentialsNonExpired;

    @Column(columnDefinition = "boolean default true")
    private boolean enabled;
















    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.addAll(role.grantedAuthorities()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
