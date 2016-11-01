package com.assaabloy.reactive.service.fast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

class FastServiceImpl implements FastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastServiceImpl.class);
    private static final AtomicInteger COUNT = new AtomicInteger();

    @Override
    public Observable<Fast> observeFast() {
        return Observable.create(subscriber -> {
            subscriber.onNext(getFast());
            subscriber.onCompleted();
        });
    }

    @Override
    public Fast getFast() {
        LOGGER.info("Fast START");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        LOGGER.info("Fast DONE");
        return new Fast("I was fetched quickly, number " + COUNT.incrementAndGet());
    }

    @Override
    public Callable<Fast> getFastCallable() {
        return this::getFast;
    }
}
