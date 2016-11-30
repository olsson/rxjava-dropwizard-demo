package com.assaabloy.reactive.service.two;

import rx.Observable;

import java.util.concurrent.Callable;

public interface TwoService {
    Observable<Two> observeTwo();

    Two getTwo();

    Callable<Two> getTwoCallable();

    static TwoService newInstance() {
        return new TwoServiceImpl();
    }
}
