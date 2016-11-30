package com.assaabloy.reactive.resource;

import com.assaabloy.reactive.service.three.Three;
import com.assaabloy.reactive.service.two.Two;
import com.assaabloy.reactive.service.one.One;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
class Demo {

    private final One one;
    private final Two two;
    private final Three three;

    Demo(One one, Two two) {
        this.one = one;
        this.two = two;
        this.three = null;
    }

    Demo(Three three, Two two) {
        this.two = two;
        this.three = three;
        this.one = null;
    }

    @JsonProperty
    public One getOne() {
        return one;
    }

    @JsonProperty
    public Two getTwo() {
        return two;
    }

    @JsonProperty
    public Three getThree() {
        return three;
    }

    @Override
    public String toString() {
        return "Demo{" +
            "one=" + one +
            ", two=" + two +
            ", three=" + three +
            '}';
    }
}
