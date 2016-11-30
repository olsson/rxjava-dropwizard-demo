package com.assaabloy.reactive.service.two;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Two {

    private final String message;

    Two(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Two{" +
                "message='" + message + '\'' +
                '}';
    }
}
