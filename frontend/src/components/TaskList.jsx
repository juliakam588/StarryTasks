import React, { useState } from 'react';
import TaskItem from './TaskItem';
import axios from '../../axiosConfig';
import ConfirmationDialog from './ConfirmationDialog';

const TaskList = ({ tasks, onToggleCompletion, onDeleteTask, childId }) => {
    const [openDialog, setOpenDialog] = useState(false);
    const [taskIdToDelete, setTaskIdToDelete] = useState(null);

    const handleDelete = (taskId) => {
        axios.delete(`/api/tasks/${taskId}`)
            .then(response => {
                onDeleteTask(taskId);
            })
            .catch(error => {
                console.error('Failed to delete task:', error);
            });
    };

    const confirmDelete = (taskId) => {
        setTaskIdToDelete(taskId);
        setOpenDialog(true);
    };

    const handleConfirmDelete = () => {
        if (taskIdToDelete) {
            handleDelete(taskIdToDelete);
            setTaskIdToDelete(null);
        }
        setOpenDialog(false);
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
        setTaskIdToDelete(null);
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
                    onDelete={() => confirmDelete(task.taskId)}
                    scheduledDate={task.scheduledDate}
                    childId={childId}
                />
            ))}
            <ConfirmationDialog
                open={openDialog}
                onClose={handleCloseDialog}
                onConfirm={handleConfirmDelete}
                title="Confirm Delete"
                message="Are you sure you want to delete this task?"
            />
        </div>
    );
};

export default TaskList;
