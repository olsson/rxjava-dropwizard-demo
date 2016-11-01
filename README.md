# RxJava Dropwizard Demo

Using RxJava with Dropwizard is no different than using it with any other framework that uses Jersey.

In this demo app, a fast and a slow backend service is simulated and combined into a single response. 
This is done asynchronously using Jersey's `AsyncResponse` and RxJava's `subscribeOn()`.

## Prerequisites

Java 8 JDK should be installed and `java` on your `$PATH`.

## Build and Run

To build, run:

```
$ ./gradlew oneJar
```

To run:

```
$ java -jar build/libs/rxdemo-1.0-SNAPSHOT-standalone.jar server src/dist/config/demo.yml
```

## What to look at

Check out the examples in [DemoResource.java](src/main/java/com/assaabloy/reactive/resource/DemoResource.java) for the different ways to do async and how they differ from each other.
