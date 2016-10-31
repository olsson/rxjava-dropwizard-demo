package com.assaabloy.reactive.service.slow;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Slow {

    private final String message;

    Slow(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Slow{" +
                "message='" + message + '\'' +
                '}';
    }
}
