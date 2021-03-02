package ru.korolkovrs.market.beans;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
@Order(0)
public class FrequencyMarketProfiler {
    private Map<String, Integer> frequencyMap;

    @PostConstruct
    public void init() {
        frequencyMap = new HashMap<>();
    }

    @Before("execution(public * ru.korolkovrs.market..*(..))")
    public void methodFrequencyProfiling(JoinPoint joinPoint) {
        log.debug("before profiling...");
        String methodName = joinPoint.getSignature().toString();
        int value;
        if (frequencyMap.containsKey(methodName)) {
            value = frequencyMap.get(methodName);
            frequencyMap.put(methodName, ++value);
        } else {
            value = 1;
            frequencyMap.put(methodName, value);
        }
    }

    public Map<String, Integer> getMethodWithMaxFrequency() {
        Map<String, Integer> maxFreqMap = new HashMap<>();
        String maxFreqMethod = Collections.max(frequencyMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        maxFreqMap.put(maxFreqMethod, frequencyMap.get(maxFreqMethod));
        return maxFreqMap;
    }
}
