package ru.javawebinar.topjava.service;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MyStopWatch extends Stopwatch {

    private static final Logger logger = LoggerFactory.getLogger(MyStopWatch.class);

    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        logger.info(String.format("Test %s %s, spent %d microseconds",
                testName, status, TimeUnit.NANOSECONDS.toMicros(nanos)));
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }
}
