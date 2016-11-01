package com.assaabloy.reactive.service.slow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

class SlowServiceImpl implements SlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlowServiceImpl.class);
    private static final AtomicInteger COUNT = new AtomicInteger();

    @Override
    public Observable<Slow> observeSlow() {
        return Observable.create(subscriber -> {
            subscriber.onNext(getSlow());
            subscriber.onCompleted();
        });
    }

    @Override
    public Slow getSlow() {
        try {
            LOGGER.info("Slow START");
            Thread.sleep(1000);
            LOGGER.info("Slow DONE");
            return new Slow("I took 1000 ms to fetch. I am number " + COUNT.incrementAndGet());
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Callable<Slow> getSlowCallable() {
        return this::getSlow;
    }
}
