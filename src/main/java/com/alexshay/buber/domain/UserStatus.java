package com.alexshay.buber.domain;

public enum UserStatus {
    OFF_LINE("off-line"),
    ONLINE("online"),
    IN_PROGRESS("in-progress");
    private final String value;


    UserStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UserStatus fromValue(String v) {
        for (UserStatus c: UserStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
