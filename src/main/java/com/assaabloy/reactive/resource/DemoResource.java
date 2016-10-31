package com.assaabloy.reactive.resource;

import com.assaabloy.reactive.service.slow.SlowService;
import com.assaabloy.reactive.service.fast.FastService;
import com.codahale.metrics.annotation.Timed;
import rx.schedulers.Schedulers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("demo")
@Produces(MediaType.APPLICATION_JSON)
public class DemoResource {

    private final SlowService slowService;
    private final FastService fastService;

    public DemoResource(final SlowService slowService, final FastService fastService) {
        this.slowService = slowService;
        this.fastService = fastService;
    }

    @GET
    @Timed
    public void getDemo(@Suspended final AsyncResponse asyncResponse) {
        slowService.observeSlow()
                   .subscribeOn(Schedulers.io())
                   .zipWith(fastService.observeFast(), Demo::new)
                   .doOnNext(asyncResponse::resume)
                   .doOnError(asyncResponse::resume)
                   .subscribe();
    }

}
