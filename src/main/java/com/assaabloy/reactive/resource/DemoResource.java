package com.assaabloy.reactive.resource;

import com.assaabloy.reactive.service.one.OneCommand;
import com.assaabloy.reactive.service.three.Three;
import com.assaabloy.reactive.service.three.ThreeCommand;
import com.assaabloy.reactive.service.three.ThreeService;
import com.assaabloy.reactive.service.two.Two;
import com.assaabloy.reactive.service.two.TwoCommand;
import com.assaabloy.reactive.service.two.TwoService;
import com.assaabloy.reactive.service.one.One;
import com.assaabloy.reactive.service.one.OneService;
import rx.schedulers.Schedulers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Path("/demo")
@Produces(MediaType.APPLICATION_JSON)
public class DemoResource {

    private final OneService oneService;
    private final TwoService twoService;
    private final ThreeService threeService;

    public DemoResource(final OneService oneService, final TwoService twoService,
                        final ThreeService threeService) {
        this.oneService = oneService;
        this.twoService = twoService;
        this.threeService = threeService;
    }

    //
    // Simple naive blocking method
    //

    @GET
    @Path("/blocking")
    public Response getDemoBlocking() {
        One one = oneService.getOne();
        Two two = twoService.getTwo();

        return Response.ok(new Demo(one, two))
                       .build();
    }

    //
    // Using futures and an executor service works, when the usage is simple
    //

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @GET
    @Path("/futures")
    public Response getDemoFuture() throws Exception {
        Future<One> slow = executor.submit(oneService.getOneCallable());
        Future<Two> fast = executor.submit(twoService.getTwoCallable());

        return Response.ok(new Demo(slow.get(), fast.get()))
                       .build();
    }

    //
    // Using futures and an executor service starts blocking when more complicated call graphs are used
    //

    @GET
    @Path("/futures-complex")
    public Response getDemoFutureComplex() throws Exception {
        Future<One> slow = executor.submit(oneService.getOneCallable());
        Future<Three> complex = executor.submit(threeService.getThreeCallable(slow.get()));
        Future<Two> fast = executor.submit(twoService.getTwoCallable());

        return Response.ok(new Demo(complex.get(), fast.get()))
                       .build();
    }

    //
    // Using CompletableFutures can reduce blocking, but is lacking in operators and difficult to read
    //

    @GET
    @Path("/completable-futures")
    public void getDemoCompletableFutures(@Suspended AsyncResponse ar) throws Exception {
        final CompletableFuture<Two> threeFuture =
            CompletableFuture.supplyAsync(twoService::getTwo, executor);

        CompletableFuture.supplyAsync(oneService::getOne, executor)
                         .thenApplyAsync(threeService::getThree, executor)
                         .thenCombine(threeFuture, Demo::new)
                         .whenComplete((demo, throwable) -> {
                             if (throwable != null) {
                                 ar.resume(throwable);
                             }
                             ar.resume(demo);
                         })
                         .get();

    }


    //
    // Fully asynchronous RxJava method.
    //

    @GET
    @Path("/async")
    public void getDemoAsync(@Suspended final AsyncResponse asyncResponse) {
        oneService.observeOne()
                  .subscribeOn(Schedulers.io())
                  .flatMap(threeService::observeThree)
                  .subscribeOn(Schedulers.io())
                  .zipWith(twoService.observeTwo(), Demo::new)
                  .subscribe(asyncResponse::resume, asyncResponse::resume);
    }

    // Asynchronous RxJava method, wrapped with Hystrix

    @GET
    @Path("/async-hystrix")
    public void getDemoAsyncHystrix(@Suspended final AsyncResponse asyncResponse) {
        new OneCommand(oneService).toObservable()
                  .subscribeOn(Schedulers.io())
                  .flatMap(one -> new ThreeCommand(threeService, one).toObservable())
                  .subscribeOn(Schedulers.io())
                  .zipWith(new TwoCommand(twoService).toObservable(), Demo::new)
                  .subscribe(asyncResponse::resume, asyncResponse::resume);
    }




}
