import React from 'react';
import starIcon from '../assets/images/Star-Png-164.png';
import check from '../assets/images/task-check-button.png';

import './TaskList.css';

const TaskItem = ({ taskName, reward, isCompleted, toggleCompletion }) => {
    const handleToggle = () => {
        toggleCompletion(); // This will be passed down from TaskList to change state
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
            <button className="task-check-button" onClick={handleToggle}>
                <img src={check} className="task-icon" alt="Check icon"/>
            </button>
        </article>
    );
};

export default TaskItem;
