package io.ramonak.habitTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ramonak.habitTracker.UI.DTO.DayOfWeek;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"id"})
@Entity
//@Table(name = "HABIT", schema = "habit_tracker")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Habit implements Serializable {

    private static final long serialVersionUID = 6348845605893911313L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Size(max = 65536)
    @Column
    private String description;

    @OneToOne
    @JoinColumn(name = "ICON_ID")
    private Icon icon;

    @Transient
    private Set<DayOfWeek> daysOfWeek = new HashSet<>();

    public Habit(HabitForGrid habitForGrid) {
        this.id = habitForGrid.getHabitId();
        this.name = habitForGrid.getName();
        this.description = habitForGrid.getDescription();
        addPlannedDayOfWeek(DayOfWeek.MONDAY, habitForGrid.getAchievementMonday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.TUESDAY, habitForGrid.getAchievementTuesday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.WEDNESDAY, habitForGrid.getAchievementWednesday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.THURSDAY, habitForGrid.getAchievementThursday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.FRIDAY, habitForGrid.getAchievementFriday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.SATURDAY, habitForGrid.getAchievementSaturday().getStatus());
        addPlannedDayOfWeek(DayOfWeek.SUNDAY, habitForGrid.getAchievementSunday().getStatus());
    }

    private void addPlannedDayOfWeek(DayOfWeek dayOfWeek, int status) {
        if (status == 10 || status == 11 || status == 12) {
            daysOfWeek.add(dayOfWeek);
        }
    }
}
