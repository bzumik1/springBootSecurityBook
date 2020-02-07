package com.znamenacek.jakub.spring_boot_security_test.security.authentication.enums;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.znamenacek.jakub.spring_boot_security_test.security.authentication.enums.UserPermissions.*;

@Getter
public enum UserRole {
    ADMIN(Sets.newHashSet(READ,WRITE)),
    NORMAL_USER(Sets.newHashSet(READ));

    private final Set<UserPermissions> permissions;

    UserRole(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<GrantedAuthority> getGrantedAuthoritis(){
        Set<GrantedAuthority> authority = permissions
                .stream()
                .map(userPermissions -> new SimpleGrantedAuthority(userPermissions.getPermission()))
                .collect(Collectors.toSet());
        authority.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return authority;
    }
}
