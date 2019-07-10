package com.gmail.kennethbgoodin.metrics;

/**
 * Describes some given/example metric keys
 */
public enum MetricKeys implements MetricKey {
    RESPONSE_TIME_NS("resp-time", "ns"),
    REQUEST_BODY_SIZE_BYTES("req-body-size", "bytes");

    private final String key;
    private final String displaySuffix;

    MetricKeys(String key, String displaySuffix) {
        this.key = key;
        this.displaySuffix = displaySuffix;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDisplaySuffix() {
        return displaySuffix;
    }
}