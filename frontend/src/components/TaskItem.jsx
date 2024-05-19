import React from 'react';
import starIcon from '../assets/images/Star-Png-164.png';
import TaskCheckbox from './TaskCheckbox.jsx';
import './TaskList.css';

const TaskItem = ({ taskName, reward, isCompleted = false, toggleCompletion }) => {
    const handleToggle = () => {
        toggleCompletion();
    };

    return (
        <article className={`task-item ${isCompleted ? 'completed' : ''}`}>
            <div className="task-details">
                <h4 className="task-name">{taskName}</h4>
                <div className="task-reward">
                    <div className="reward-label">Reward:</div>
                    <div className="reward-amount">{reward}</div>
                    <img src={starIcon} className="reward-icon" alt="Reward icon" />
                </div>
            </div>
            <TaskCheckbox checked={isCompleted} onChange={handleToggle} />

        </article>
    );
};

export default TaskItem;
