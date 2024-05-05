import React, { useState, useEffect } from 'react';
import TaskItem from './TaskItem';

const TaskList = ({ tasks }) => {
    const [taskState, setTaskState] = useState(tasks.map(task => ({
        ...task,
        isCompleted: false
    })));

    const toggleCompletion = (index) => {
        const newTasks = [...taskState];
        newTasks[index].isCompleted = !newTasks[index].isCompleted;
        setTaskState(newTasks);
        // Optionally, update the backend here
    };

    return (
        <div className="tasks-section">
            {taskState.map((task, index) => (
                <TaskItem
                    key={index}
                    taskName={task.name}
                    reward={task.reward}
                    isCompleted={task.isCompleted}
                    toggleCompletion={() => toggleCompletion(index)}
                />
            ))}
        </div>
    );
};

export default TaskList;
