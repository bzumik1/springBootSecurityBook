package com.znamenacek.jakub.spring_boot_security_test.security.authentication.enums;



import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.znamenacek.jakub.spring_boot_security_test.security.authentication.enums.Permission.READ;
import static com.znamenacek.jakub.spring_boot_security_test.security.authentication.enums.Permission.WRITE;

@Getter
public enum Role {
    ADMIN(Sets.newHashSet(READ,WRITE)),
    NORMAL_USER(Sets.newHashSet(READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions = permissions;
    }

    public Set<GrantedAuthority> grantedAuthorities(){
        Set<GrantedAuthority> authorities = permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }

}
