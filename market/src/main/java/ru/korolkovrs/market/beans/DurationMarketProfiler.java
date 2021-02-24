package ru.korolkovrs.market.beans;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
@Order(1)
@Data
public class DurationMarketProfiler {
    private Map<String, Double> durationMap;

    @PostConstruct
    public void init() {
        durationMap = new HashMap<>();
    }

    @Around("execution(public * ru.korolkovrs.market.controllers..*(..))")
    public Object getControllerDuration(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.nanoTime();
        Object out = proceedingJoinPoint.proceed();
        long end = System.nanoTime();
        double duration = (end - start) / 1_000_000d;
        String controllerName = proceedingJoinPoint.getThis().getClass().getSuperclass().getCanonicalName();
        durationMap.computeIfPresent(controllerName, (key, value) -> value + duration);
        durationMap.putIfAbsent(controllerName, duration);
        return out;
    }

    public Map.Entry<String, Double> getControllerWithMaxDurationTime() {
        return durationMap.entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue)).get();
    }
}
