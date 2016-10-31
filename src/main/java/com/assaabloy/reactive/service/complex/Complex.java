package com.assaabloy.reactive.service.complex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Complex {
    private final String message;

    Complex(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Complex{" +
            "message='" + message + '\'' +
            '}';
    }
}

