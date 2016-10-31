package com.assaabloy.reactive.resource;

import com.assaabloy.reactive.service.complex.Complex;
import com.assaabloy.reactive.service.complex.ComplexService;
import com.assaabloy.reactive.service.fast.Fast;
import com.assaabloy.reactive.service.slow.Slow;
import com.assaabloy.reactive.service.slow.SlowService;
import com.assaabloy.reactive.service.fast.FastService;
import rx.schedulers.Schedulers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.*;

@Path("/demo")
@Produces(MediaType.APPLICATION_JSON)
public class DemoResource {

    private final SlowService slowService;
    private final FastService fastService;
    private final ComplexService complexService;

    public DemoResource(final SlowService slowService, final FastService fastService,
                        final ComplexService complexService) {
        this.slowService = slowService;
        this.fastService = fastService;
        this.complexService = complexService;
    }

    //
    // Simple naive blocking method
    //

    @GET
    @Path("/blocking")
    public Response getDemoBlocking() {
        Slow slow = slowService.getSlow();
        Fast fast = fastService.getFast();

        return Response.ok(new Demo(slow, fast))
                       .build();
    }

    //
    // Using futures and an executor service works, when the usage is simple
    //

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @GET
    @Path("/futures")
    public Response getDemoFuture() throws Exception {
        Future<Slow> slow = executorService.submit(slowService.getSlowCallable());
        Future<Fast> fast = executorService.submit(fastService.getFastCallable());

        return Response.ok(new Demo(slow.get(), fast.get()))
                       .build();
    }

    //
    // Using futures and an executor service starts blocking when more complicated call graphs are used
    //

    @GET
    @Path("/futures-complex")
    public Response getDemoFutureComplex() throws Exception {
        Future<Slow> slow = executorService.submit(slowService.getSlowCallable());
        Future<Complex> complex = executorService.submit(complexService.getComplexCallable(slow.get()));
        Future<Fast> fast = executorService.submit(fastService.getFastCallable());

        return Response.ok(new Demo(complex.get(), fast.get()))
                       .build();
    }


    //
    // Fully asynchronous RxJava method.
    //

    @GET
    @Path("/async")
    public void getDemoAsync(@Suspended final AsyncResponse asyncResponse) {
        slowService.observeSlow()
                   .subscribeOn(Schedulers.io())
                   .zipWith(fastService.observeFast(), Demo::new)
                   .doOnNext(asyncResponse::resume)
                   .doOnError(asyncResponse::resume)
                   .subscribe();
    }


}
