package com.alexshay.buber.domain;

public enum OrderStatus {
    WAITING("waiting"),
    IN_PROGRESS("in-progress"),
    COMPLETE("complete");

    private final String value;


    OrderStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OrderStatus fromValue(String v) {
        for (OrderStatus c: OrderStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
