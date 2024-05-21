import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../../axiosConfig';
import Header from '../components/Header/Header';
import PlusSign from '../assets/images/plus-sign.png';
import StarIcon from '../assets/images/Star-Png-164.png';
import '../assets/styles/AddTask.css';

const EditTaskForm = () => {
    const navigate = useNavigate();
    const { taskId, childId } = useParams();
    const [taskName, setTaskName] = useState('');
    const [categoryName, setCategoryName] = useState('');
    const [assignedStars, setAssignedStars] = useState('');
    const [scheduledDate, setScheduledDate] = useState('');
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        const fetchTaskDetails = async () => {
            try {
                const response = await axios.get(`/api/tasks/${taskId}/edit`);
                const task = response.data;
                setTaskName(task.taskName);
                setCategoryName(task.categoryName);
                setAssignedStars(task.assignedStars);
                setScheduledDate(task.scheduledDate);
            } catch (error) {
                console.error('Failed to fetch task details', error);
            }
        };

        const fetchCategories = async () => {
            try {
                const response = await axios.get('/api/categories');
                setCategories(response.data);
            } catch (error) {
                console.error('Failed to fetch categories', error);
            }
        };

        fetchTaskDetails();
        fetchCategories();
    }, [taskId]);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const taskData = {
            taskName,
            categoryName,
            assignedStars,
            scheduledDate,
            childId
        };
        try {
            await axios.put(`/api/tasks/${taskId}`, taskData);
            alert('Task updated successfully');
            navigate(`/child-task/${childId}/tasks`);
        } catch (error) {
            console.error('Error updating task:', error);
            alert('Failed to update task');
        }
    };

    return (
        <div className="container1">
            <Header />
            <form className="addtask-main-content" onSubmit={handleSubmit}>
                <input type="text" id="task-name" placeholder="Task name" className="add-task-name" value={taskName}
                       onChange={e => setTaskName(e.target.value)} />
                <select className="category-select" value={categoryName}
                        onChange={e => setCategoryName(e.target.value)}>
                    <option value="">Select category</option>
                    {categories.map((category, index) => (
                        <option key={category.id || index} value={category.name}>{category.name}</option>
                    ))}
                </select>
                <div className="date-inputs">
                    <input type="date" value={scheduledDate} onChange={e => setScheduledDate(e.target.value)}
                           placeholder="Scheduled Date" />
                </div>
                <div className="reward-container">
                    <img src={StarIcon} alt="Star icon" className="star-icon" />
                    <input
                        type="number"
                        id="stars-number"
                        placeholder="Number of stars"
                        className="star-input"
                        value={assignedStars}
                        onChange={e => {
                            const parsedStars = parseInt(e.target.value, 10);
                            setAssignedStars(Number.isNaN(parsedStars) ? 1 : parsedStars);
                        }}
                        min="1"
                    />
                </div>
                <button type="submit" className="add-task-button">
                    <img src={PlusSign} alt="Update task icon" className="add-task-icon" />
                    <span className="add-task-text">Update Task</span>
                </button>
            </form>
        </div>
    );
};

export default EditTaskForm;
