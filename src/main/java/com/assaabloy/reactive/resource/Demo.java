package com.assaabloy.reactive.resource;

import com.assaabloy.reactive.service.complex.Complex;
import com.assaabloy.reactive.service.fast.Fast;
import com.assaabloy.reactive.service.slow.Slow;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
class Demo {

    private final Slow slow;
    private final Fast fast;
    private final Complex complex;

    Demo(Slow slow, Fast fast) {
        this.slow = slow;
        this.fast = fast;
        this.complex = null;
    }

    Demo(Complex complex, Fast fast) {
        this.fast = fast;
        this.complex = complex;
        this.slow = null;
    }

    @JsonProperty
    public Slow getSlow() {
        return slow;
    }

    @JsonProperty
    public Fast getFast() {
        return fast;
    }

    @JsonProperty
    public Complex getComplex() {
        return complex;
    }

    @Override
    public String toString() {
        return "Demo{" +
            "slow=" + slow +
            ", fast=" + fast +
            ", complex=" + complex +
            '}';
    }
}
