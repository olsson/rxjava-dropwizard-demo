package com.assaabloy.reactive.service.one;

import rx.Observable;

import java.util.concurrent.Callable;

public interface OneService {

    Observable<One> observeOne();

    One getOne();

    Callable<One> getOneCallable();

    static OneService newInstance() {
        return new OneServiceImpl();
    }
}
