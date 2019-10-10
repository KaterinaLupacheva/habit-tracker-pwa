package io.ramonak.habitTracker.UI.views.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {

    private static LocalDate setMonday(LocalDate today) {
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int dayOfWeekCount = dayOfWeek.getValue();
        int startOfWeek = dayOfWeekCount - 1;
        return today.minusDays(startOfWeek);
    }

    public static List<LocalDate> createWeek(LocalDate today) {
        LocalDate startOfWeek = setMonday(today);
        List<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i<7; i++) {
            dateList.add(startOfWeek);
            startOfWeek = startOfWeek.plusDays(1L);
        }
        return dateList;
    }


}
