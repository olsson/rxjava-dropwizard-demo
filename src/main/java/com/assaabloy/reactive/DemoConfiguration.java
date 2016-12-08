package com.assaabloy.reactive;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;
import java.util.Map;

class DemoConfiguration extends Configuration {

    @NotNull
    @JsonProperty
    private Map<String, Object> defaultHystrixConfig;

    public Map<String, Object> getDefaultHystrixConfig() {
        return defaultHystrixConfig;
    }

}
