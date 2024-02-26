package com.project.entity.enums;

public enum Role {
    ADMIN("admin"),
    MANAGER("Manager"),
    CUSTOMER("Customer");


    private final String role_name;


    Role(String role_name) {
        this.role_name = role_name;
    }

    public String getName() {
        return role_name;
    }
}
