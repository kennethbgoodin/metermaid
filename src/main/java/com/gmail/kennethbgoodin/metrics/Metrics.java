package com.gmail.kennethbgoodin.metrics;

import com.gmail.kennethbgoodin.metrics.spring.repository.MemoryMetricRepository;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class Metrics<REQ, RESP> {

    private final Function<REQ, String> keyFunction;
    private final TriConsumer<RESP, String, String> addHeaderFunction;
    private final MetricRepository repository;

    private Metrics(Function<REQ, String> keyFunction,
                    TriConsumer<RESP, String, String> addHeaderFunction,
                    MetricRepository repository) {
        Objects.requireNonNull(keyFunction);
        Objects.requireNonNull(addHeaderFunction);
        Objects.requireNonNull(repository);

        this.keyFunction = keyFunction;
        this.addHeaderFunction = addHeaderFunction;
        this.repository = repository;
    }

    public void insertWithId(MetricKey key, String reqId, long value) {
        repository.insert(key, reqId, value);
    }

    public void insert(MetricKey metricKey, REQ req, long value) {
        insertWithId(metricKey, getRequestId(req), value);
    }

    public Map<String, Long> getAll(MetricKey metricKey) {
        return repository.getAll(metricKey);
    }

    public void addResponseHeader(RESP resp, String header, String body) {
        addHeaderFunction.accept(resp, header, body);
    }

    public String getRequestId(REQ req) {
        return keyFunction.apply(req);
    }

    public double average(MetricKey key) {
        Map<String, Long> data = getAll(key);
        return average(data);
    }

    public Optional<Map.Entry<String, Long>> max(MetricKey key) {
        Map<String, Long> data = getAll(key);
        return max(data);
    }

    public Optional<Map.Entry<String, Long>> min(MetricKey key) {
        Map<String, Long> data = getAll(key);
        return min(data);
    }

    //

    public static <REQ, RESP> MetricBuilder<REQ, RESP> builder() {
        return new MetricBuilder<>();
    }

    public static double average(Map<String, Long> data) {
        double size = (double) data.size();
        double sum = (double) data.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();
        return sum / size;
    }

    public static Optional<Map.Entry<String, Long>> max(Map<String, Long> data) {
        return data.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue));
    }

    public static Optional<Map.Entry<String, Long>> min(Map<String, Long> data) {
        return data.entrySet()
                .stream()
                .min(Comparator.comparing(Map.Entry::getValue));
    }

    public static MetricRepository inMemoryRepository() {
        return new MemoryMetricRepository();
    }

    public static class MetricBuilder<REQ, RESP> {

        private Function<REQ, String> requestIdFunction;
        private TriConsumer<RESP, String, String> responseHeaderFunction;
        private MetricRepository repository;

        public MetricBuilder<REQ, RESP> idRequestsWith(Function<REQ, String> requestIdFunction) {
            Objects.requireNonNull(requestIdFunction);
            this.requestIdFunction = requestIdFunction;
            return this;
        }

        public MetricBuilder<REQ, RESP> addHeaderToResponseWith(TriConsumer<RESP, String, String> responseHeaderFunction) {
            Objects.requireNonNull(responseHeaderFunction);
            this.responseHeaderFunction = responseHeaderFunction;
            return this;
        }

        public MetricBuilder<REQ, RESP> withRepository(MetricRepository repository) {
            Objects.requireNonNull(repository);
            this.repository = repository;
            return this;
        }

        public Metrics<REQ, RESP> build() {
            return new Metrics<>(requestIdFunction, responseHeaderFunction, repository);
        }
    }
}