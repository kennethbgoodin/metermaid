package com.gmail.kennethbgoodin.metrics.spring.repository;

import com.gmail.kennethbgoodin.metrics.MetricKey;
import com.gmail.kennethbgoodin.metrics.MetricRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A metric repository that stores metrics in memory
 */
@Repository
public class MemoryMetricRepository implements MetricRepository {

    private final Map<MetricKey, Map<String, Long>> data = new ConcurrentHashMap<>();

    @Override
    public void insert(MetricKey metric, String id, long value) {
        data.computeIfAbsent(metric, ignored -> new ConcurrentHashMap<>())
                .put(id, value);
    }

    @Override
    public Map<String, Long> getAll(MetricKey metricKey) {
        return data.getOrDefault(metricKey, Collections.emptyMap());
    }
}
