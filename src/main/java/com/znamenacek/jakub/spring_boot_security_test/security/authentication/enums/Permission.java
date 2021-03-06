package com.znamenacek.jakub.spring_boot_security_test.security.authentication.enums;

import lombok.Getter;

@Getter
public enum Permission {
    READ("data:read"),
    WRITE("data:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
