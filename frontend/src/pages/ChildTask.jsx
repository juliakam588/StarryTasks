import React, { useState, useEffect } from 'react';
import axios from '../../axiosConfig';
import Header from '../components/Header/Header';
import TaskList from '../components/TaskList';
import AddButton from '../components/AddButton';
import { Link, useParams } from 'react-router-dom';
import plusSign from '../assets/images/plus-sign.png';
import dayjs from 'dayjs';
import DateCalendarServerRequest from '../components/DateCalendarServerRequest';
import Select from 'react-select';
import { getUserRole } from '../../axiosConfig'; // Import funkcji, która pobiera rolę użytkownika

const ChildTasksPage = () => {
    const [tasks, setTasks] = useState([]);
    const [filteredTasks, setFilteredTasks] = useState([]);
    const [childName, setChildName] = useState('');
    const [selectedDate, setSelectedDate] = useState(dayjs());
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [userRole, setUserRole] = useState('');
    const { childId } = useParams();

    useEffect(() => {
        async function fetchChildDetails() {
            try {
                const response = await axios.get(`/api/parent/children/${childId}`);
                setChildName(response.data.name);
            } catch (error) {
                console.error('Failed to fetch child details:', error);
            }
        }
        fetchChildDetails();
    }, [childId]);

    useEffect(() => {
        async function fetchCategories() {
            try {
                const response = await axios.get('/api/categories');
                setCategories(response.data.map(category => ({ value: category.name, label: category.name })));
            } catch (error) {
                console.error('Failed to fetch categories:', error);
            }
        }
        fetchCategories();
    }, []);

    useEffect(() => {
        const role = getUserRole(); // Pobieranie roli użytkownika
        setUserRole(role);
    }, []);

    const fetchTasks = async (date, category = null) => {
        if (!date) return;
        try {
            const response = await axios.get(`/api/tasks/child/${childId}/date`, {
                params: {
                    date: date.format('YYYY-MM-DD'),
                    categoryName: category ? category.value : undefined
                }
            });
            setTasks(response.data);
            setFilteredTasks(response.data);
        } catch (error) {
            if (error.response && error.response.status === 204) {
                setTasks([]);
                setFilteredTasks([]);
            } else {
                console.error('Failed to fetch tasks:', error);
            }
        }
    };

    useEffect(() => {
        fetchTasks(selectedDate, selectedCategory);
    }, [selectedDate, selectedCategory]);

    const handleDayChange = (tasks, date) => {
        setSelectedDate(date);
        fetchTasks(date, selectedCategory);
    };

    const handleUpdateTask = (taskId, updatedTask) => {
        setTasks(tasks.map(task => (task.taskId === taskId ? { ...task, ...updatedTask } : task)));
        setFilteredTasks(filteredTasks.map(task => (task.taskId === taskId ? { ...task, ...updatedTask } : task)));
    };

    const handleDeleteTask = (taskId) => {
        setTasks(tasks.filter(task => task.taskId !== taskId));
        setFilteredTasks(filteredTasks.filter(task => task.taskId !== taskId));
    };

    const handleCategoryChange = (selectedOption) => {
        setSelectedCategory(selectedOption);
        fetchTasks(selectedDate, selectedOption);
    };

    return (
        <div className="container">
            <Header />
            <main className='lesser-container'>
                <h1 className="greeting">{childName}'s Tasks</h1>
                <Select
                    options={categories}
                    value={selectedCategory}
                    onChange={handleCategoryChange}
                    placeholder="Filter by category"
                    isClearable
                />
                <DateCalendarServerRequest
                    childId={childId}
                    onDaySelect={(tasks, date) => handleDayChange(tasks, date)}
                />
                <TaskList
                    key={selectedDate ? selectedDate.format('YYYY-MM-DD') : 'default'}
                    tasks={filteredTasks || []}
                    onToggleCompletion={(taskId) => {
                        if (userRole !== 'Parent') {
                            axios.put(`/api/tasks/${taskId}/toggle-completion`)
                                .then(() => {
                                    setTasks(tasks.map(task => (task.taskId === taskId ? { ...task, completed: !task.completed } : task)));
                                    setFilteredTasks(filteredTasks.map(task => (task.taskId === taskId ? { ...task, completed: !task.completed } : task)));
                                })
                                .catch(error => {
                                    console.error('Failed to toggle task completion:', error);
                                });
                        }
                    }}
                    onUpdateTask={handleUpdateTask}
                    onDeleteTask={handleDeleteTask}
                    childId={childId}
                />
                <Link to={`/add-task/${childId}`}>
                    <AddButton iconSrc={plusSign} buttonText="Add Task" />
                </Link>
            </main>
        </div>
    );
};

export default ChildTasksPage;
