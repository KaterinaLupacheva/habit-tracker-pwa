package io.ramonak.habitTracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Data
@Entity
//@Table(name = "ICON", schema = "habit_tracker")
public class Icon implements Serializable {

    private static final long serialVersionUID = -7914103963582633923L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String link;
}
