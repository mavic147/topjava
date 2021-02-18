package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;

public class FilterUtil {
    public static  <T extends Comparable<T>> boolean isBetweenHalfOpen(LocalDateTime lt, LocalDateTime start, LocalDateTime end) {
        return (start == null || lt.compareTo(start) >= 0) && (end == null || lt.compareTo(end) < 0);
    }
}
