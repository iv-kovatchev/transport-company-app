package com.transport.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CargoType {
    GOODS,
    PASSENGERS;

    @JsonValue
    public String toValue() {
        return name();
    }

    @JsonCreator
    public static CargoType fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Cargo type cannot be null");
        }

        try {
            return CargoType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid cargo type '" + value + "'. Allowed values: GOODS, PASSENGERS"
            );
        }
    }
}
