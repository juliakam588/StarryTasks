import React, { useState, useEffect } from 'react';
import {useParams} from 'react-router-dom';
import axios from '../../axiosConfig';
import Header from '../components/Header/Header';
import PlusSign from '../assets/images/plus-sign.png';
import StarIcon from '../assets/images/Star-Png-164.png';
import '../assets/styles/AddTask.css';
import { useNavigate } from 'react-router-dom';



const AddTaskForm = () => {
    const navigate = useNavigate();
    const [taskName, setTaskName] = useState('');
    const [categoryName, setCategoryName] = useState('');
    const [stars, setStars] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [days, setDays] = useState({
        Monday: false,
        Tuesday: false,
        Wednesday: false,
        Thursday: false,
        Friday: false,
        Saturday: false,
        Sunday: false
    });
    const [categories, setCategories] = useState([]);
    const { childId } = useParams();


    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get('/api/categories');
                setCategories(response.data);
            } catch (error) {
                console.error('Failed to fetch categories', error);
            }
        };
        fetchCategories();
    }, []);

    const handleDayChange = (day) => {
        setDays(prevDays => ({ ...prevDays, [day]: !prevDays[day] }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const scheduledDays = Object.keys(days).filter(day => days[day]).map(day => day.toUpperCase());
        const taskData = {
            name: taskName,
            categoryName,
            stars,
            scheduledDays,
            startDate,
            endDate,
            childId
        };
        try {
            await axios.post('/api/tasks', taskData);
            alert('Task added successfully');
            navigate(`/child-task/${childId}/tasks`);
        } catch (error) {
            console.error('Error adding task:', error);
            alert('Failed to add task');
        }
    };

    return (
        <div className="container1">
            <Header />
            <form className="addtask-main-content" onSubmit={handleSubmit}>
                <input type="text" id="task-name" placeholder="Task name" className="add-task-name" value={taskName}
                       onChange={e => setTaskName(e.target.value)}/>
                <select className="category-select" value={categoryName}
                        onChange={e => setCategoryName(e.target.value)}>
                    <option value="">Select category</option>
                    {categories.map((category, index) => (
                        <option key={category.id || index} value={category.id}>{category.name}</option>
                    ))}
                </select>
                <div className="date-inputs">
                    <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)}
                           placeholder="Start Date"/>
                    <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)}
                           placeholder="End Date"/>
                </div>

                <div className="checklist">
                    {Object.keys(days).map(day => (
                        <label key={day} className="day-container">{day}
                            <input type="checkbox" checked={days[day]} onChange={() => handleDayChange(day)}/>
                            <span className="checkmark"></span>
                        </label>
                    ))}
                </div>
                <div className="reward-container">
                    <img src={StarIcon} alt="Star icon" className="star-icon"/>
                    <input
                        type="number"
                        id="stars-number"
                        placeholder="Number of stars"
                        className="star-input"
                        value={stars}
                        onChange={e => {
                            const parsedStars = parseInt(e.target.value, 10);
                            setStars(Number.isNaN(parsedStars) ? 1 : parsedStars);
                        }}
                        min="1"
                    />
                </div>
                <button type="submit" className="add-task-button">
                    <img src={PlusSign} alt="Add task icon" className="add-task-icon"/>
                    <span className="add-task-text">Add Task</span>
                </button>
            </form>
        </div>
    );
};

export default AddTaskForm;