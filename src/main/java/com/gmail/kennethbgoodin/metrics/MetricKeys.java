package com.gmail.kennethbgoodin.metrics;

/**
 * Describes some given/example metric keys
 */
public enum MetricKeys implements MetricKey {
    RESPONSE_TIME_NS("resp-time"),
    REQUEST_BODY_SIZE_BYTES("req-body-size");

    private final String key;

    MetricKeys(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
