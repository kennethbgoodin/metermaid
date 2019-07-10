package com.gmail.kennethbgoodin.metrics;

import com.gmail.kennethbgoodin.metrics.MetricKey;

import java.util.Map;

public interface MetricRepository {

    void insert(MetricKey metric, String id, long value);

    Map<String, Long> getAll(MetricKey metricKey);

}
