package io.ramonak.habitTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"id", "roles"})
@Entity
@Table(name = "UserInfo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    private static final long serialVersionUID = -5946721903590710787L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(unique = true)
    @NotNull
    private String username;

    @Column
    @Email
    @NotNull
    private String email;

    @Column
    @NotNull
    private String password;

    @PrePersist
    @PreUpdate
    private void prepareData(){
        this.email = email == null ? null : email.toLowerCase();
    }

    @ManyToMany
    private List<Role> roles = new ArrayList<>();

    public User(String username, String email, String password, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    //    public void addRole(Role role) {
//        this.roles.add(role);
//        role.getUsers().add(this);
//    }
}
