package com.ss.scrumptious_restaurant.entity;

public enum  UserRole {

    DEFAULT("ROLE_DEFAULT"),
    CUSTOMER("ROLE_CUSTOMER"),
    EMPLOYEE("ROLE_EMPLOYEE"),
    DRIVER("ROLE_DRIVER"),
    ADMIN("ROLE_ADMIN");


    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRole() {
        return roleName;
    }

    public String getRoleName() {
        return roleName.replace("ROLE_", "");
    }
}
