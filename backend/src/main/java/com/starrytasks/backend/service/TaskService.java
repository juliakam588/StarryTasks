package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.AddTaskDTO;
import com.starrytasks.backend.api.external.TaskDetailsDTO;
import com.starrytasks.backend.api.external.TasksDTO;


import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    void saveTask(AddTaskDTO task, Long parentId);
    List<TaskDetailsDTO> getTasksByChildId(Long childId);


    void updateTask(Long id, TaskDetailsDTO taskDTO);

    void deleteTask(Long id);

    List<TasksDTO> findTasksForChildByDate(Long childId, LocalDate date);

    List<TasksDTO> findTasksScheduledBetweenAndChildId(LocalDate startDate, LocalDate endDate, Long childId);
}
