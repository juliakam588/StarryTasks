package com.starrytasks.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.DayOfWeek;

@Data
@Entity
@Table(name = "task_schedules")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TaskSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "taskId", nullable = false)
    private Task task;

    @Column(nullable = false)
    private DayOfWeek day;

}