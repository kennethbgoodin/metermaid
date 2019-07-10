package com.gmail.kennethbgoodin.metrics.spring;

import com.gmail.kennethbgoodin.metrics.Metrics;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class MetricSupplier {

    @Bean
    public Metrics<HttpServletRequest, HttpServletResponse> getMetrics() {
        return Metrics.<HttpServletRequest, HttpServletResponse>builder()
                .idRequestsWith(req -> UUID.randomUUID().toString())
                .addHeaderToResponseWith(HttpServletResponse::addHeader)
                .withRepository(Metrics.inMemoryRepository())
                .build();
    }

}
