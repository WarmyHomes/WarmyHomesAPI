package com.project.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

public enum Roles {
    ADMIN("admin"),
    MANAGER("Manager"),
    CUSTOMER("Customer");


    private final String role_name;


    Roles(String role_name) {
        this.role_name = role_name;
    }

    public String getName() {
        return role_name;
    }
}
