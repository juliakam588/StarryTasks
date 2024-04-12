package com.starrytasks.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "taskTemplateId")
    private TaskTemplate taskTemplate;

    @Column(nullable = true)
    private String customName;

    @Column(nullable = false)
    private Integer assignedStars;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = true)
    private Category category;


    @OneToMany(mappedBy = "task")
    private Set<TaskSchedule> schedules;
}
