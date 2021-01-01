package com.ryanair.flights.utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

    public static List<YearMonth> getDatesBetween(LocalDate start, LocalDate end) {
        YearMonth yearMonthStart = YearMonth.of(start.getYear(), start.getMonth());
        YearMonth yearMonthEnd = YearMonth.of(end.getYear(), end.getMonth());
        if (yearMonthStart.equals(yearMonthStart)) {
            return Collections.singletonList(yearMonthStart);
        }
        List<YearMonth> listYearMonth = new ArrayList<>();
        do {
            listYearMonth.add(yearMonthStart);
            yearMonthStart = yearMonthStart.plusMonths(1);
        } while (yearMonthStart.isBefore(yearMonthEnd));
        listYearMonth.add(yearMonthEnd);
        return listYearMonth;
    }
}
