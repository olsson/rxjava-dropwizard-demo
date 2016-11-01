package com.assaabloy.reactive.service.complex;

import com.assaabloy.reactive.service.slow.Slow;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Complex {
    private final String message;
    private final Slow slow;

    Complex(String message, Slow slow) {
        this.message = message;
        this.slow = slow;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public Slow getSlow() {
        return slow;
    }

    @Override
    public String toString() {
        return "Complex{" +
            "message='" + message + '\'' +
            ", slow=" + slow +
            '}';
    }
}

