package com.lab4.Models;

public enum RoleEnum {
    USER("user_role");

    private final String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
