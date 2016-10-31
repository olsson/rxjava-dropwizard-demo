package com.assaabloy.reactive.service.slow;

import rx.Observable;

public interface SlowService {

    Observable<Slow> observeSlow();

    static SlowService newInstance() {
        return new SlowServiceImpl();
    }
}
