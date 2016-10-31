package com.assaabloy.reactive.service.slow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

class SlowServiceImpl implements SlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlowServiceImpl.class);
    private static final AtomicInteger COUNT = new AtomicInteger();

    @Override
    public Observable<Slow> observeSlow() {
        return Observable.create(new Observable.OnSubscribe<Slow>() {
            @Override
            public void call(Subscriber<? super Slow> subscriber) {
                try {
                    LOGGER.info("Starting to emit Slow");
                    Thread.sleep(1000);
                    LOGGER.info("Emitting Slow");
                    subscriber.onNext(new Slow("I took 1000 ms to fetch. I am number " + COUNT.incrementAndGet()));
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }

    @Override
    public Slow getSlow() {
        try {
            LOGGER.info("Starting to emit Slow");
            Thread.sleep(1000);
            LOGGER.info("Emitting Slow");
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
