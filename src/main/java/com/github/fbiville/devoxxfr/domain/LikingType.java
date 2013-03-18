package com.github.fbiville.devoxxfr.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

public enum LikingType {
    HATES, LOVES;

    @JsonCreator
    public static LikingType fromString(@JsonProperty("TYPE") String rawValue) {
        checkNotNull(rawValue);
        String val = rawValue.toLowerCase(Locale.ENGLISH);
        if (val.equals("hate")) {
            return HATES;
        }
        if (val.equals("like")) {
            return LOVES;
        }
        throw new IllegalArgumentException("Unknown liking type: " + rawValue);
    }
}
