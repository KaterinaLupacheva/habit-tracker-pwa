package io.ramonak.habitTracker.entity;

import io.ramonak.habitTracker.UI.DTO.DateDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
//@Table(name = "ACHIEVEMENT", schema = "habit_tracker")
public class Achievement implements Serializable {

    private static final long serialVersionUID = 8635471915261491776L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private int status;

    @ManyToOne
    private Habit habit;

    @Column
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Achievement(AchievementForGrid achievementForGrid) {
        this.id = achievementForGrid.getId();
        this.setStatus(achievementForGrid.getStatus());
        this.date = achievementForGrid.getDate();
    }

    public Achievement(DateDTO dateDTO) {
        this.date = dateDTO.getDay();
    }
}
