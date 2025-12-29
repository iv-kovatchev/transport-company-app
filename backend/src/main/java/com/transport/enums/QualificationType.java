package com.transport.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum QualificationType {
    HAZARDOUS_MATERIALS,
    PASSENGER_TRANSPORT_12_PLUS,
    LONG_DISTANCE,
    INTERNATIONAL,
    HEAVY_CARGO,
    TANKER_TRANSPORT;

    @JsonValue
    public String toValue() {
        return name();
    }

    @JsonCreator
    public static QualificationType fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Qualification type cannot be null");
        }

        try {
            return QualificationType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid qualification type '" + value + "'. Allowed values: " +
                            "HAZARDOUS_MATERIALS, PASSENGER_TRANSPORT_12_PLUS, LONG_DISTANCE, " +
                            "INTERNATIONAL, HEAVY_CARGO, TANKER_TRANSPORT"
            );
        }
    }
}
