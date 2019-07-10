package com.gmail.kennethbgoodin.metrics;

/**
 * Supplies a unique String key for a given metric
 */
public interface MetricKey {

    /**
     * @return the key for this metric, should be unique
     */
    String getKey();

    /**
     * @return the String suffix to append to this metric when displaying
     */
    String getDisplaySuffix();

}
