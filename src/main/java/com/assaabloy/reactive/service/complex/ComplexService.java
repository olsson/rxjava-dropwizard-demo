package com.assaabloy.reactive.service.complex;

import com.assaabloy.reactive.service.slow.Slow;
import rx.Observable;

import java.util.concurrent.Callable;

public interface ComplexService {

    Complex getComplex(final Slow slow);

    Callable<Complex> getComplexCallable(final Slow slow);

    Observable<Complex> observeComplex(final Slow slow);

    static ComplexService newInstance() {
        return new ComplexServiceImpl();
    }

}
