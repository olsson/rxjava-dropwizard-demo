package com.assaabloy.reactive;

import com.assaabloy.reactive.resource.DemoResource;
import com.assaabloy.reactive.service.three.ThreeService;
import com.assaabloy.reactive.service.two.TwoService;
import com.assaabloy.reactive.service.one.OneService;
import com.netflix.config.ConfigurationManager;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.apache.commons.configuration.MapConfiguration;

public class DemoApplication extends Application<DemoConfiguration> {

    public static void main(String[] args) throws Exception {
        new DemoApplication().run(args);
    }

    @Override
    public void run(DemoConfiguration configuration, Environment environment) throws Exception {
        ConfigurationManager.install(new MapConfiguration(configuration.getDefaultHystrixConfig()));

        environment.healthChecks().register("health", new DemoHealthCheck());
        environment.jersey().register(new DemoResource(
            OneService.newInstance(),
            TwoService.newInstance(),
            ThreeService.newInstance()));
    }
}
