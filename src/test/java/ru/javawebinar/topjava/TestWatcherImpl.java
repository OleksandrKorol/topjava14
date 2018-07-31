package ru.javawebinar.topjava;

import org.junit.AfterClass;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class TestWatcherImpl extends TestWatcher {
    private static final Logger log = LoggerFactory.getLogger(TestWatcherImpl.class);

    private static LocalTime localTime;
    private static Map<String, Long> map = new HashMap<>();

    @Override
    protected void starting(Description description) {
        localTime = LocalTime.now();
        super.starting(description);
    }

    @Override
    protected void finished(Description description) {
        map.put(description.getMethodName(), localTime.until(LocalTime.now(), ChronoUnit.MILLIS));
        log.info("| {} - {}ms |", description.getMethodName(), map.get(description.getMethodName()));
        super.finished(description);
    }

    public static void printDurationTest() {
        map.forEach((key, val) -> {
            log.info("--------------------------------");
            log.info("| {} - {}ms |", key, val);
        });
    }

}
