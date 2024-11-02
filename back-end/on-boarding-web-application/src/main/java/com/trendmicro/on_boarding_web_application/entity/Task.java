package com.trendmicro.on_boarding_web_application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "task_tbl")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID taskId;
    private String title;
    private String description;
    private Boolean isMandatory = false;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private Set<UserTask> userTasks;
}
