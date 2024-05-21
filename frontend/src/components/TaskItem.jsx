import React from 'react';
import { useNavigate } from 'react-router-dom';
import starIcon from '../assets/images/Star-Png-164.png';
import TaskCheckbox from './TaskCheckbox.jsx';
import ModeEditIcon from '@mui/icons-material/ModeEdit';
import DeleteIcon from '@mui/icons-material/Delete';
import './TaskList.css';
import dayjs from 'dayjs';
import isSameOrAfter from 'dayjs/plugin/isSameOrAfter';
import {getUserRole} from "../../axiosConfig.js";

dayjs.extend(isSameOrAfter);

const TaskItem = ({ taskId, taskName, reward, isCompleted = false, toggleCompletion, onDelete, scheduledDate, childId }) => {
    const navigate = useNavigate();

    const handleToggle = () => {
        toggleCompletion();
    };
    const userRole = getUserRole();
    const today = dayjs();
    const isFutureTask = dayjs(scheduledDate).isSameOrAfter(today, 'day');
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
            {isFutureTask && !isCompleted && userRole === "Parent" && (
                <div className="task-actions">
                    <ModeEditIcon className="edit-icon" onClick={() => navigate(`/edit-task/${childId}/${taskId}`)} />
                    <DeleteIcon className="delete-icon" onClick={onDelete} />
                </div>
            )}
        </article>
    );
};

export default TaskItem;