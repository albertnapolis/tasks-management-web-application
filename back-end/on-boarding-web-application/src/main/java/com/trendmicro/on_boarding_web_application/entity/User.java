package com.trendmicro.on_boarding_web_application.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_tbl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<UserTask> userTasks;
}
