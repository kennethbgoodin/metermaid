package com.gmail.kennethbgoodin.metrics.spring.filter;

import com.gmail.kennethbgoodin.metrics.MetricKeys;
import com.gmail.kennethbgoodin.metrics.Metrics;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * An example filter for spring that collects metrics data on request size and response time
 */
// first in the filter chain
@Order(1)
public class MetricFilter implements Filter {

    private final Metrics<HttpServletRequest, HttpServletResponse> metrics;

    MetricFilter(Metrics<HttpServletRequest, HttpServletResponse> metrics) {
        this.metrics = metrics;
    }

    public MetricFilter() {
        // example builder usage
        this(Metrics.<HttpServletRequest, HttpServletResponse>builder()
                .idRequestsWith(req -> UUID.randomUUID().toString())
                .addHeaderToResponseWith(HttpServletResponse::addHeader)
                .withRepository(Metrics.inMemoryRepository())
                .build());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        metrics.insert(MetricKeys.REQUEST_BODY_SIZE_BYTES, req, req.getContentLengthLong());

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // add header for later
        metrics.addResponseHeader(resp, Metrics.METRIC_ID_HEADER, metrics.getRequestId(req));

        // since we are the first in the filter chain we can measure the time it takes to execute the entire chain up
        // until just before the response is sent
        long now = System.nanoTime();
        filterChain.doFilter(servletRequest, servletResponse);
        long elapsed = System.nanoTime() - now;
        metrics.insert(MetricKeys.RESPONSE_TIME_NS, req, elapsed);
    }
}
