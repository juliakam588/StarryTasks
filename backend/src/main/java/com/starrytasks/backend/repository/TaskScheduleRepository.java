package com.starrytasks.backend.repository;

import com.starrytasks.backend.api.external.TasksDTO;
import com.starrytasks.backend.api.internal.Task;
import com.starrytasks.backend.api.internal.TaskSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface TaskScheduleRepository extends JpaRepository<TaskSchedule, Long> {
    Collection<TaskSchedule> findByTaskUserIdAndScheduledDate(Long childId, LocalDate date);


    @Query("SELECT new com.starrytasks.backend.api.external.TasksDTO(" +
            "t.taskId, " +
            "t.customName, " +
            "t.assignedStars, " +
            "ts.scheduledDate, " +
            "(SELECT CASE WHEN ut.status.name = 'Completed' THEN true ELSE false END FROM UserTask ut WHERE ut.task.taskId = t.taskId AND ut.user.id = :childId), " +
            "t.category.name) " +
            "FROM TaskSchedule ts " +
            "JOIN ts.task t " +
            "WHERE ts.scheduledDate BETWEEN :startDate AND :endDate " +
            "AND t.user.id = :childId")
    List<TasksDTO> findAllByDateRangeAndChildId(@Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate,
                                                @Param("childId") Long childId);


    TaskSchedule findTaskScheduleByTask(Task task);
}

