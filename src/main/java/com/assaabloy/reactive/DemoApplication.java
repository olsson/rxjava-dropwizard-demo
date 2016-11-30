package com.assaabloy.reactive;

import com.assaabloy.reactive.resource.DemoResource;
import com.assaabloy.reactive.service.three.ThreeService;
import com.assaabloy.reactive.service.two.TwoService;
import com.assaabloy.reactive.service.one.OneService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class DemoApplication extends Application<DemoConfiguration> {

    public static void main(String[] args) throws Exception {
        new DemoApplication().run(args);
    }

    @Override
    public void run(DemoConfiguration configuration, Environment environment) throws Exception {
        environment.healthChecks().register("health", new DemoHealthCheck());
        environment.jersey().register(new DemoResource(
            OneService.newInstance(),
            TwoService.newInstance(),
            ThreeService.newInstance()));
    }
}
