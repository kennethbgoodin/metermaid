package com.gmail.kennethbgoodin.metrics.spring.controllers;

import com.gmail.kennethbgoodin.metrics.MetricKeys;
import com.gmail.kennethbgoodin.metrics.Metrics;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;

@RestController
public class MetricController {

    private final Metrics<HttpServletRequest, HttpServletResponse> metrics;

    public MetricController(Metrics<HttpServletRequest, HttpServletResponse> metrics) {
        this.metrics = metrics;
    }

    @RequestMapping("/metrics")
    public String index() {
        StringBuilder index = new StringBuilder("Metrics")
                .append("<br>")
                .append("------------------------")
                .append("<br><br>");
        Arrays.stream(MetricKeys.values())
                .forEach(key -> {
                    Map.Entry<String, Long> max = metrics.max(key)
                            .orElse(new AbstractMap.SimpleEntry<>("N/A", 0L));
                    Map.Entry<String, Long> min = metrics.min(key)
                            .orElse(new AbstractMap.SimpleEntry<>("N/A", 0L));
                    double avg = metrics.average(key);

                    index.append(key).append("<br>")
                            .append("Request id: ").append(min.getKey()).append(" min: ").append(min.getValue())
                                    .append(" ").append(key.getDisplaySuffix()).append("<br>")
                            .append("Request id: ").append(max.getKey()).append(" max: ").append(max.getValue())
                                    .append(" ").append(key.getDisplaySuffix()).append("<br>")
                            .append("Average: ").append(avg).append(key.getDisplaySuffix()).append("<br><br>");
                });

        return index.toString();
    }
}
