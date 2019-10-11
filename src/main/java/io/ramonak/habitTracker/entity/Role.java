package io.ramonak.habitTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Data
@EqualsAndHashCode
//@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role implements Serializable {

    private static final long serialVersionUID = 3675585703169904531L;
    public static final String ADMIN ="admin";
    public static final String USER = "user";

    public static String[] getAllRoles() {
        return new String[] {
                ADMIN, USER
        };
    }

}
