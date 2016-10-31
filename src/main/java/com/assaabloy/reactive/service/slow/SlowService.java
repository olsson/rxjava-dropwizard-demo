package com.assaabloy.reactive.service.slow;

import rx.Observable;

import java.util.concurrent.Callable;

public interface SlowService {

    Observable<Slow> observeSlow();

    Slow getSlow();

    Callable<Slow> getSlowCallable();

    static SlowService newInstance() {
        return new SlowServiceImpl();
    }
}
