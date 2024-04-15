import React from 'react';
import starIcon from '../assets/images/Star-Png-164.png';
import check from '../assets/images/task-check-button.png';

import './TaskList.css';

const TaskItem = ({ taskName, reward }) => {
  return (
    <article className="task-item">
      <div className="task-details">
        <h4 className="task-name">{taskName}</h4>
        <div className="task-reward">
          <div className="reward-label">Reward:</div>
          <div className="reward-amount">{reward}</div>
          <img src={starIcon} className="reward-icon" alt="Reward icon" />
        </div>
      </div>
      <button className="task-check-button">
        <img src={check} className="task-icon"/>
      </button>
    </article>
  );
};

const TaskList = ({ tasks }) => {
  return (
    <div className="tasks-section">
      {tasks.map((task, index) => (
        <TaskItem key={index} taskName={task.name} reward={task.reward} />
      ))}
    </div>
  );
};

export default TaskList;
