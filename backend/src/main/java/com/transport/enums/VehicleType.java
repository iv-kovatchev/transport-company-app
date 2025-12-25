package com.transport.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum VehicleType {
    BUS,
    TRUCK,
    TANKER,
    VAN,
    CAR;

    @JsonValue
    public String toValue() {
        return name();
    }

    @JsonCreator
    public static VehicleType fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }

        try {
            return VehicleType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid vehicle type '" + value + "'. Allowed values: BUS, TRUCK, TANKER, VAN, CAR"
            );
        }
    }
}
