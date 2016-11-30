package com.assaabloy.reactive.service.one;

import com.fasterxml.jackson.annotation.JsonProperty;

public class One {

    private final String message;

    One(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "One{" +
                "message='" + message + '\'' +
                '}';
    }
}
