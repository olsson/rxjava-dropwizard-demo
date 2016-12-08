package com.assaabloy.reactive.service.three;

import com.assaabloy.reactive.service.one.One;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Random;

public class ThreeCommand extends HystrixCommand<Three> {

    private final ThreeService threeService;
    private final One one;

    private static final ThreeCache THREE_CACHE = new ThreeCache();
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreeCommand.class);

    public ThreeCommand(ThreeService threeService, One one) {
        super(HystrixCommandGroupKey.Factory.asKey("ThreeCommand"));
        this.threeService = threeService;
        this.one = one;
    }

    @Override
    protected Three run() {
        if (new Random().nextBoolean()) {
            final Three three = threeService.getThree(one);
            THREE_CACHE.setCachedValue(three);
            return three;
        } else {
            throw new RuntimeException("Something bad happened!");
        }
    }

    @Override
    protected Three getFallback() {
        String stackTrace = Optional.ofNullable(getExecutionException())
                                    .map(ExceptionUtils::getStackTrace)
                                    .orElse("Unknown exception");
        LOGGER.error("Using cached value due to failure: {}", stackTrace);
        return THREE_CACHE.getCachedValue();
    }

    private static class ThreeCache {
        private Three cachedValue;

        Three getCachedValue() {
            return cachedValue;
        }

        void setCachedValue(final Three cachedValue) {
            this.cachedValue = cachedValue;
        }
    }
}
