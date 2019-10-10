package io.ramonak.habitTracker.UI.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HabitDTO {

    private Integer id;
    private String name;
    private String description;
    private Set<DayOfWeek> daysOfWeek = new HashSet<>();

    public HabitDTO(HabitForGridDTO habitForGridDTO) {
        this.id = habitForGridDTO.getHabitId();
        this.name = habitForGridDTO.getName();
        this.description = habitForGridDTO.getDescription();
        addPlannedDayOfWeek(DayOfWeek.MONDAY, habitForGridDTO.getAchievementMonday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.TUESDAY, habitForGridDTO.getAchievementTuesday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.WEDNESDAY, habitForGridDTO.getAchievementWednesday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.THURSDAY, habitForGridDTO.getAchievementThursday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.FRIDAY, habitForGridDTO.getAchievementFriday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.SATURDAY, habitForGridDTO.getAchievementSaturday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.SUNDAY, habitForGridDTO.getAchievementSunday().getStatus());
    }

    private void addPlannedDayOfWeek(DayOfWeek dayOfWeek, int status) {
        if (status == 10 || status == 11 || status == 12) {
            daysOfWeek.add(dayOfWeek);
        }
    }
}
