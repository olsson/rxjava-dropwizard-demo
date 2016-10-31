package com.assaabloy.reactive.service.fast;

import rx.Observable;

import java.util.concurrent.Callable;

public interface FastService {
    Observable<Fast> observeFast();

    Fast getFast();

    Callable<Fast> getFastCallable();

    static FastService newInstance() {
        return new FastServiceImpl();
    }
}
