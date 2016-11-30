package com.assaabloy.reactive.service.three;

import com.assaabloy.reactive.service.one.One;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Three {
    private final String message;
    private final One one;

    Three(String message, One one) {
        this.message = message;
        this.one = one;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public One getOne() {
        return one;
    }

    @Override
    public String toString() {
        return "Three{" +
            "message='" + message + '\'' +
            ", one=" + one +
            '}';
    }
}

