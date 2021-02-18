package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

public class FilterUtil {
    public static  <T extends Comparable<T>> boolean isBetweenHalfOpen(T localTime, @Nullable T start, @Nullable T end) {
        return (start == null || localTime.compareTo(start) >= 0) && (end == null || localTime.compareTo(end) < 0);
    }
}
