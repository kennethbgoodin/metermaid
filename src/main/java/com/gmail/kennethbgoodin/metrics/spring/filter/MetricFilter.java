package com.gmail.kennethbgoodin.metrics.spring.filter;

import com.gmail.kennethbgoodin.metrics.MetricKeys;
import com.gmail.kennethbgoodin.metrics.Metrics;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An example filter for spring that collects metrics data on request size and response time
 */
// first in the filter chain
@Order(1)
@Component
public class MetricFilter implements Filter {

    private final Metrics<HttpServletRequest, HttpServletResponse> metrics;

    public MetricFilter(Metrics<HttpServletRequest, HttpServletResponse> metrics) {
        this.metrics = metrics;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        long bodySize = req.getContentLengthLong();
        // -1 is returned when there is no body, we don't want negative values
        if (bodySize < 0) {
            bodySize = 0;
        }
        metrics.insert(MetricKeys.REQUEST_BODY_SIZE_BYTES, req, bodySize);

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // add header marking this as a metrics-tracked response
        metrics.addResponseHeader(resp, Metrics.METRIC_ID_HEADER, metrics.getRequestId(req));

        // since we are the first in the filter chain we can measure the time it takes to execute the entire chain up
        // until just before the response is sent
        long now = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        long elapsed = System.currentTimeMillis() - now;
        metrics.insert(MetricKeys.RESPONSE_TIME_MS, req, elapsed);
    }
}
