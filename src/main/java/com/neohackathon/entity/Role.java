package com.neohackathon.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    public String getValue() {
        return value;
    }

}
