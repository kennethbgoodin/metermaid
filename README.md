# metermaid

![Screenshot](https://travis-ci.com/kennethbgoodin/metermaid.svg?branch=master)

metermaid is a metrics gathering extension for java web frameworks, such as [Spring](https://spring.io/). It provides an API for storing, reducing, and displaying various metric data, such as max/min/average request to response time and request body size.

I built this for an interview project, but it is a fully functional extension. 

## Building
metermaid can be built by cloning this repo and running `mvn package`.

## Deploying
metermaid can be included in any java application as a library, and requires no external dependencies. 

## Tests
Tests are run automatically when the project is built and can be manually triggered with `mvn test`. 

## Usage
You can obtain a `Metrics` instance via `Metrics.builder()`. The builder has several methods, all of which need to be called before calling `MetricBuilder.build()`. These builder methods are used to set: where metrics will be stored; how the unique id for each request will be generated; and how a header will be added to the request response. 

In addition to a `Metrics` instance, you will also need a `MetricKey`, which just supplies a unique name for each metric and some formatting information. There are some default keys defined in `MetricKeys`.

An in-memory storage mechanism for metrics can be obtained via `Metrics.inMemoryRepository`, or you can supply your own by implementing `MetricsRepository`. Note that calls to the supplied repository are called *synchronously*.

Example `Metrics` usage for Spring can be found [here](https://github.com/kennethbgoodin/metermaid/blob/master/src/main/java/com/gmail/kennethbgoodin/metrics/spring/MetricSupplier.java) and [here](https://github.com/kennethbgoodin/metermaid/blob/master/src/main/java/com/gmail/kennethbgoodin/metrics/spring/filter/MetricFilter.java).


## TODO/Possible improvements
- Use trove primitive collections to avoid allocating an object per data point
- Include a timestamp with each recorded metric value
- Improve display of metrics, at the moment it is a very bland text page
- Include more example implementations than just with SpringBoot
- Include more `MetricsRepository` implementations than just an in-memory version
