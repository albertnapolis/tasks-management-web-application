package com.trendmicro.on_boarding_web_application.repository;

import com.trendmicro.on_boarding_web_application.entity.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, UUID> {

    // GET ALL USER'S TASK BY USER ID
    @Query("SELECT ut FROM UserTask ut WHERE ut.user.id = :userId")
    List<UserTask> findAllTasksByUserId(@Param("userId") UUID userId);
    // GET ALL MANDATORY TASK PER USER
    @Query("SELECT ut FROM UserTask ut WHERE ut.user.userId = :userId AND ut.task.isMandatory = true")
    List<UserTask> findAllMandatoryTasksByUserId(@Param("userId") UUID userId);
    // GET ALL PERSONAL TASK PER USER
    @Query("SELECT ut FROM UserTask ut WHERE ut.user.userId = :userId AND ut.task.isMandatory = false")
    List<UserTask> findAllPersonalTasksByUserId(@Param("userId") UUID userId);
}
