import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import axios, {getUserInfo} from '../../axiosConfig';
import Header from '../components/Header/Header';
import PlusSign from '../assets/images/plus-sign.png';
import StarIcon from '../assets/images/Star-Png-164.png';
import '../assets/styles/AddTask.css';
import { taskValidationSchema } from '../validation/taskValidationSchema';
const AddTaskForm = () => {
    const navigate = useNavigate();
    const [categories, setCategories] = useState([]);
    const { childId } = useParams();
    useEffect(() => {
        const userInfo = getUserInfo();
        if (!userInfo || userInfo.role === 'Child') {
            navigate('/login');
        }
    }, [navigate]);

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

    const handleSubmit = async (values) => {
        const taskData = {
            ...values,
            scheduledDays: Object.keys(values.days).filter(day => values.days[day]).map(day => day.toUpperCase()),
            childId,
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
            <Formik
                initialValues={{
                    name: '',
                    categoryName: '',
                    stars: 1,
                    startDate: '',
                    endDate: '',
                    days: {
                        Monday: false,
                        Tuesday: false,
                        Wednesday: false,
                        Thursday: false,
                        Friday: false,
                        Saturday: false,
                        Sunday: false
                    }
                }}
                validationSchema={taskValidationSchema}
                onSubmit={handleSubmit}
            >
                {({ values, setFieldValue }) => (
                    <Form className="addtask-main-content">
                        <Field
                            type="text"
                            id="name"
                            name="name"
                            placeholder="Task name"
                            className="add-task-name"
                        />
                        <ErrorMessage name="name" component="div" className="error-message" />

                        <Field
                            as="select"
                            name="categoryName"
                            className="category-select"
                        >
                            <option value="">Select category</option>
                            {categories.map((category, index) => (
                                <option key={category.id || index} value={category.id}>{category.name}</option>
                            ))}
                        </Field>
                        <ErrorMessage name="categoryName" component="div" className="error-message" />

                        <div className="date-inputs">
                            <Field
                                type="date"
                                name="startDate"
                                className="date-input"
                            />
                            <ErrorMessage name="startDate" component="div" className="error-message" />
                            <Field
                                type="date"
                                name="endDate"
                                className="date-input"
                            />
                            <ErrorMessage name="endDate" component="div" className="error-message" />
                        </div>

                        <div className="checklist">
                            {Object.keys(values.days).map(day => (
                                <label key={day} className="day-container">{day}
                                    <Field
                                        type="checkbox"
                                        name={`days.${day}`}
                                    />
                                    <span className="checkmark"></span>
                                </label>
                            ))}
                        </div>

                        <div className="reward-container">
                            <img src={StarIcon} alt="Star icon" className="star-icon" />
                            <Field
                                type="number"
                                id="stars-number"
                                name="stars"
                                placeholder="Number of stars"
                                className="star-input"
                                min="1"
                            />
                            <ErrorMessage name="stars" component="div" className="error-message" />
                        </div>

                        <button type="submit" className="add-task-button">
                            <img src={PlusSign} alt="Add task icon" className="add-task-icon" />
                            <span className="add-task-text">Add Task</span>
                        </button>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default AddTaskForm;
