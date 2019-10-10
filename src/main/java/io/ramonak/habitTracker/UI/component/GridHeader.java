package io.ramonak.habitTracker.UI.component;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import io.ramonak.habitTracker.UI.DTO.DateDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GridHeader extends HorizontalLayout {

    private List<DateDTO> header;

    public List<DateCell> setupHeader(LocalDate today) {
        header = createWeek(setMonday(today));
        List<DateCell> list = new ArrayList<>();
        for (DateDTO dateDTO : header) {
            list.add(new DateCell(dateDTO));
        }
        return list;
    }

    private LocalDate setMonday(LocalDate today) {
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int dayOfWeekCount = dayOfWeek.getValue();
        int startOfWeek = dayOfWeekCount - 1;
        return today.minusDays(startOfWeek);
    }

    private List<DateDTO> createWeek(LocalDate monday) {
        LocalDate startOfWeek = monday;
        List<DateDTO> list = new ArrayList<>();
        List<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i<7; i++) {
            dateList.add(startOfWeek);
            startOfWeek = startOfWeek.plusDays(1L);
        }
        for(LocalDate tempDate : dateList) {
            DateDTO dateDTO = new DateDTO();
            dateDTO.setDay(tempDate);
            dateDTO.setDow(tempDate.getDayOfWeek());
            list.add(dateDTO);
        }
        return list;
    }

    public List<DateDTO> getHeader() {
        return header;
    }
}
