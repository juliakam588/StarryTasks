import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import axios, {getUserInfo} from '../../axiosConfig';
import Header from '../components/Header/Header';
import PlusSign from '../assets/images/plus-sign.png';
import StarIcon from '../assets/images/Star-Png-164.png';
import '../assets/styles/AddTask.css';
import { editTaskValidationSchema } from '../validation/taskValidationSchema';

const EditTaskForm = () => {
    const navigate = useNavigate();
    const { taskId, childId } = useParams();
    const [categories, setCategories] = useState([]);
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

    const fetchTaskDetails = async (setFieldValue) => {
        try {
            const response = await axios.get(`/api/tasks/${taskId}/edit`);
            const task = response.data;
            setFieldValue('taskName', task.taskName);
            setFieldValue('categoryName', task.categoryName);
            setFieldValue('assignedStars', task.assignedStars);
            setFieldValue('scheduledDate', task.scheduledDate);
        } catch (error) {
            console.error('Failed to fetch task details', error);
        }
    };

    const handleSubmit = async (values) => {
        const taskData = {
            ...values,
            childId,
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
            <Formik
                initialValues={{
                    taskName: '',
                    categoryName: '',
                    assignedStars: 1,
                    scheduledDate: '',
                }}
                validationSchema={editTaskValidationSchema }
                onSubmit={handleSubmit}
                enableReinitialize
            >
                {({ setFieldValue }) => {
                    useEffect(() => {
                        fetchTaskDetails(setFieldValue);
                    }, [taskId]);

                    return (
                        <Form className="addtask-main-content">
                            <Field
                                type="text"
                                id="task-name"
                                name="taskName"
                                placeholder="Task name"
                                className="add-task-name"
                            />
                            <ErrorMessage name="taskName" component="div" className="error-message" />

                            <Field
                                as="select"
                                name="categoryName"
                                className="category-select"
                            >
                                <option value="">Select category</option>
                                {categories.map((category, index) => (
                                    <option key={category.id || index} value={category.name}>{category.name}</option>
                                ))}
                            </Field>
                            <ErrorMessage name="categoryName" component="div" className="error-message" />

                            <div className="date-inputs">
                                <Field
                                    type="date"
                                    name="scheduledDate"
                                    className="date-input"
                                />
                                <ErrorMessage name="scheduledDate" component="div" className="error-message" />
                            </div>

                            <div className="reward-container">
                                <img src={StarIcon} alt="Star icon" className="star-icon" />
                                <Field
                                    type="number"
                                    id="stars-number"
                                    name="assignedStars"
                                    placeholder="Number of stars"
                                    className="star-input"
                                    min="1"
                                />
                                <ErrorMessage name="assignedStars" component="div" className="error-message" />
                            </div>

                            <button type="submit" className="add-task-button">
                                <img src={PlusSign} alt="Update task icon" className="add-task-icon" />
                                <span className="add-task-text">Update Task</span>
                            </button>
                        </Form>
                    );
                }}
            </Formik>
        </div>
    );
};

export default EditTaskForm;
