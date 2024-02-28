package com.project.entity.enums;

public enum RoleType {
    ADMIN("admin"),
    MANAGER("Manager"),
    CUSTOMER("Customer"),
    ANONYMOUS("Anonymous");


    public final String role_name;


    RoleType(String role_name) {
        this.role_name = role_name;
    }

    public String getName() {
        return role_name;
    }
}
