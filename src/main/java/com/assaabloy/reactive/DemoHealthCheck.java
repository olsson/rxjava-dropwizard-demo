package com.assaabloy.reactive;

import com.codahale.metrics.health.HealthCheck;

class DemoHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
