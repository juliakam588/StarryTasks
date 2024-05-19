package com.starrytasks.backend.repository;

import com.starrytasks.backend.api.external.TasksDTO;
import com.starrytasks.backend.api.internal.Task;
import com.starrytasks.backend.api.internal.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
    List<UserTask> findByUserId(Long childId);

    void deleteUserTaskByTask(Task task);
    UserTask findByTask_TaskIdAndUserId(Long taskId, Long childId);
    @Query("SELECT new com.starrytasks.backend.api.external.TasksDTO(" +
            "ut.task.taskId, " +
            "ut.task.customName, " +
            "ut.task.assignedStars, " +
            "ts.scheduledDate, " +
            "(CASE WHEN ut.status.name = 'Completed' THEN true ELSE false END), " +
            "ut.task.category.name) " +
            "FROM UserTask ut " +
            "JOIN ut.task.schedules ts " +
            "WHERE ut.user.id = :childId AND ts.scheduledDate = :date")
    List<TasksDTO> findTasksForChildByScheduledDate(@Param("childId") Long childId, @Param("date") LocalDate date);

    UserTask findByTask_TaskId(Long taskId);

}
