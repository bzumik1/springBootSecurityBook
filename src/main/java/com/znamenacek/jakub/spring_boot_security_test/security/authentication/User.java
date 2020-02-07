package com.znamenacek.jakub.spring_boot_security_test.security.authentication;

import com.znamenacek.jakub.spring_boot_security_test.security.authentication.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Setter @Getter @NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name ="last_name")
    private String lastName;


    private String email;

    @ElementCollection(targetClass=UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable( name="USER_ROLES", joinColumns=@JoinColumn(name="USER_ID") )
    @Column(nullable=false )
    private Set<UserRole> roles;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(name = "account_non_expired",nullable = false, columnDefinition = "boolean default true")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked", nullable = false, columnDefinition = "boolean default true")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired", nullable = false, columnDefinition = "boolean default true")
    private boolean credentialsNonExpired;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean enabled;










    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(UserRole role: roles){
            for(GrantedAuthority authority:role.getGrantedAuthoritis()){
                authorities.add(authority);
            }
        }
        return  authorities;
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
