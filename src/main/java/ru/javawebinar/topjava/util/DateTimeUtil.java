package ru.javawebinar.topjava.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1,0,0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1,0,0);

    public static LocalDateTime atStartOfDayOrMin(LocalDate startDate) {
        return startDate == null ? MIN_DATE : startDate.atStartOfDay();
    }

    public static LocalDateTime atStartOfDayOrMax(LocalDate endDate) {
        return endDate == null ? MAX_DATE : endDate.plus(1, ChronoUnit.DAYS).atStartOfDay();
    }

//    public static boolean isTimeBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
//        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
//    }
//
//    public static boolean isDateBetweenHalfOpen(LocalDate ldt, LocalDate startDate, LocalDate endDate) {
//        return ldt.compareTo(startDate) >=0 && ldt.compareTo(endDate) <= 0;
//    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

