package com.assaabloy.reactive.service.one;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.Optional;

public class OneCommand extends HystrixObservableCommand<One> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OneCommand.class);

    private OneService oneService;

    public OneCommand(OneService oneService) {
        super(HystrixCommandGroupKey.Factory.asKey("OneCommand"));
        this.oneService = oneService;
    }

    @Override
    protected Observable<One> construct() {
        return oneService.observeOne();
    }

    @Override
    protected Observable<One> resumeWithFallback() {
        String stackTrace = Optional.ofNullable(getExecutionException())
                                    .map(ExceptionUtils::getStackTrace)
                                    .orElse("Unknown exception");
        LOGGER.error("Using fallback due to failure: {}", stackTrace);
        return Observable.just(new One("This is a fallback"));
    }
}
