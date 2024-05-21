package com.starrytasks.backend.repository;

import com.starrytasks.backend.api.internal.Task;
import com.starrytasks.backend.api.internal.TaskSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskScheduleRepository extends JpaRepository<TaskSchedule, Long> {

    TaskSchedule findTaskScheduleByTask(Task task);
}

