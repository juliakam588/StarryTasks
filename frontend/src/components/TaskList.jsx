import React, { useEffect } from 'react';
import TaskItem from './TaskItem';
import axios from '../../axiosConfig';


const TaskList = ({ tasks, onToggleCompletion, onDeleteTask, childId }) => {


    const handleDelete = (taskId) => {
        axios.delete(`/api/tasks/${taskId}`)
            .then(response => {
                onDeleteTask(taskId);
            })
            .catch(error => {
                console.error('Failed to delete task:', error);
            });
    };

    return (
        <div className="tasks-section">
            {tasks.map((task, index) => (
                <TaskItem
                    key={task.taskId || index}
                    taskId={task.taskId}
                    taskName={task.taskName}
                    reward={task.assignedStars}
                    isCompleted={task.completed}
                    toggleCompletion={() => onToggleCompletion(task.taskId)}
                    onDelete={() => handleDelete(task.taskId)}
                    scheduledDate={task.scheduledDate}
                    childId={childId}
                />
            ))}
        </div>
    );
};

export default TaskList;
