package com.assaabloy.reactive.service.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

class OneServiceImpl implements OneService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OneServiceImpl.class);
    private static final AtomicInteger COUNT = new AtomicInteger();

    @Override
    public Observable<One> observeOne() {
        return Observable.create(subscriber -> {
            subscriber.onNext(getOne());
            subscriber.onCompleted();
        });
    }

    @Override
    public One getOne() {
        try {
            LOGGER.info("One START");
            Thread.sleep(1000);
            LOGGER.info("One DONE");
            return new One("I took 1000 ms to fetch. I am number " + COUNT.incrementAndGet());
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Callable<One> getOneCallable() {
        return this::getOne;
    }
}
