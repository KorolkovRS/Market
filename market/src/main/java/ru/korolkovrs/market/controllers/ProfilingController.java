package ru.korolkovrs.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korolkovrs.market.beans.DurationMarketProfiler;
import ru.korolkovrs.market.beans.FrequencyMarketProfiler;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/profiling")
@RequiredArgsConstructor
public class ProfilingController {
    private final FrequencyMarketProfiler marketProfiler;
    private final DurationMarketProfiler durationMarketProfiler;

    @GetMapping("/freq")
    public Map.Entry<String, Integer> getMethodWithMaxFrequency() {
        return marketProfiler.getMethodWithMaxFrequency();
    }

    @GetMapping("/duration")
    public Map.Entry<String, Double> getControllerWithMaxDuration() {
        return durationMarketProfiler.getControllerWithMaxDurationTime();
    }

    @GetMapping("/map")
    public Map<String, Double> getDurationMap() {
        return durationMarketProfiler.getDurationMap();
    }
}
