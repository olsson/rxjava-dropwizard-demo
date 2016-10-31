package com.assaabloy.reactive.resource;

import com.assaabloy.reactive.service.fast.Fast;
import com.assaabloy.reactive.service.slow.Slow;
import com.fasterxml.jackson.annotation.JsonProperty;

class Demo {

    private final Slow slow;
    private final Fast fast;

    Demo(Slow slow, Fast fast) {
        this.slow = slow;
        this.fast = fast;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "slow=" + slow +
                ", fast=" + fast +
                '}';
    }

    @JsonProperty
    public Slow getSlow() {
        return slow;
    }

    @JsonProperty
    public Fast getFast() {
        return fast;
    }
}
