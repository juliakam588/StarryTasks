import React, { useEffect } from 'react';
import TaskItem from './TaskItem';

const TaskList = ({ tasks, onToggleCompletion }) => {

    useEffect(() => {
        console.log('Tasks updated:', tasks);
    }, [tasks]);

    const toggleCompletion = (taskId) => {
        if (onToggleCompletion) {
            onToggleCompletion(taskId);
        }
    };

    return (
        <div className="tasks-section">
            {tasks.map((task, index) => (
                <TaskItem
                    key={task.taskId || index}
                    taskName={task.taskName}
                    reward={task.assignedStars}
                    isCompleted={task.isCompleted}
                    toggleCompletion={() => toggleCompletion(task.taskId)}
                />
            ))}
        </div>
    );
};

export default TaskList;
