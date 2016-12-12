package com.beeva.ryd.vision.poc.app.service.requestor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.Period;

public class Result<T> {

    private final Instant initTime;

    private final Instant endTime;

    private final T invocationResult;

    public Result(Instant initTime, Instant endTime, T invocationResult) {
        this.initTime = initTime;
        this.endTime = endTime;
        this.invocationResult = invocationResult;
    }

    public Instant getInitTime() {
        return initTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public T getInvocationResult() {
        return invocationResult;
    }

    public long getExecutionTime() {
        return Duration.between(initTime, endTime).toMillis();
    }

}
