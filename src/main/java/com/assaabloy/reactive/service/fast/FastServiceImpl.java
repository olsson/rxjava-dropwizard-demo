package com.assaabloy.reactive.service.fast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.atomic.AtomicInteger;

class FastServiceImpl implements FastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastServiceImpl.class);
    private static final AtomicInteger COUNT = new AtomicInteger();

    @Override
    public Observable<Fast> observeFast() {
        return Observable.create(new Observable.OnSubscribe<Fast>() {
            @Override
            public void call(Subscriber<? super Fast> subscriber) {
                LOGGER.info("Emitting Fast");
                subscriber.onNext(new Fast("I was fetched quickly, number " + COUNT.incrementAndGet()));
                subscriber.onCompleted();
            }
        });
    }
}
