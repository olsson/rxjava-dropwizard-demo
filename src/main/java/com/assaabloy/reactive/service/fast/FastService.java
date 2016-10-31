package com.assaabloy.reactive.service.fast;

import rx.Observable;

public interface FastService {
    Observable<Fast> observeFast();

    static FastService newInstance() {
        return new FastServiceImpl();
    }
}
