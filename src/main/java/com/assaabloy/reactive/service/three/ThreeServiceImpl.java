package com.assaabloy.reactive.service.three;

import com.assaabloy.reactive.service.one.One;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

class ThreeServiceImpl implements ThreeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreeServiceImpl.class);
    private static final AtomicInteger COUNT = new AtomicInteger();

    @Override
    public Three getThree(final One one) {
        try {
            LOGGER.info("Three START");
            Thread.sleep(500);
            LOGGER.info("Three DONE");
            return new Three("I took 500 ms to fetch. I am number " + COUNT.incrementAndGet(), one);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Callable<Three> getThreeCallable(final One one) {
        return () -> getThree(one);
    }

    @Override
    public Observable<Three> observeThree(final One one) {
        return Observable.create(subscriber -> {
            subscriber.onNext(getThree(one));
            subscriber.onCompleted();
        });
    }


}
