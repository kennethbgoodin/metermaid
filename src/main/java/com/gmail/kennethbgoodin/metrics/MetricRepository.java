package com.gmail.kennethbgoodin.metrics;

import java.util.Map;

/**
 * A repository for storing metrics
 */
public interface MetricRepository {

    /**
     * Stores the data for the given metric and id
     *
     * @param metric The metric key
     * @param id The unique id for this request
     * @param value The value for this metric
     */
    void insert(MetricKey metric, String id, long value);

    /**
     * Retrieves all data for the given metric key
     *
     * @param metricKey The metric key
     * @return A Map of request ids and values for all metrics for the given key
     */
    Map<String, Long> getAll(MetricKey metricKey);

}
