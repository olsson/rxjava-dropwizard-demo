package com.assaabloy.reactive.service.two;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

class TwoServiceImpl implements TwoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwoServiceImpl.class);
    private static final AtomicInteger COUNT = new AtomicInteger();

    @Override
    public Observable<Two> observeTwo() {
        return Observable.create(subscriber -> {
            subscriber.onNext(getTwo());
            subscriber.onCompleted();
        });
    }

    @Override
    public Two getTwo() {
        LOGGER.info("Two START");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        LOGGER.info("Two DONE");
        return new Two("I was fetched quickly, number " + COUNT.incrementAndGet());
    }

    @Override
    public Callable<Two> getTwoCallable() {
        return this::getTwo;
    }
}
