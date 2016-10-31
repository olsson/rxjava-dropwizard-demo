package com.assaabloy.reactive.service.fast;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fast {

    private final String message;

    Fast(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Fast{" +
                "message='" + message + '\'' +
                '}';
    }
}
