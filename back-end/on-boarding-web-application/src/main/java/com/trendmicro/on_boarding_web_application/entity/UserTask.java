package com.trendmicro.on_boarding_web_application.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_tasks_tbl")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class UserTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userTasksId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

    private String status = "TODO";
    private LocalDate dateCreated = LocalDate.now();
    private LocalDate dateFinished;
}
