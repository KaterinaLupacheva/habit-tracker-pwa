package io.ramonak.habitTracker.UI.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class DateDTO {

    private LocalDate day;
    private DayOfWeek dow;
}
