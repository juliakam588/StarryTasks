import React, { useEffect, useState } from 'react';
import axios from '../../axiosConfig.js';
import Header from '../components/Header/Header.jsx';
import TaskList from '../components/TaskList';
import LinearProgress from '@mui/material/LinearProgress';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import '../App.css';
import '../assets/styles/HomePage.css';
import CongratulationsImage from '../assets/images/kids high five-pana.svg';
import WorkingImage from '../assets/images/KidsStudyingFromHome.svg';
import NothingToDoImage from '../assets/images/Kids playing with dolls-amico.svg';

const HomePage = () => {
    const [userName, setUserName] = useState('');
    const [tasks, setTasks] = useState([]);
    const [progress, setProgress] = useState(0);
    const [allTasksCompleted, setAllTasksCompleted] = useState(false);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const userResponse = await axios.get('/api/child/profile');
                setUserName(userResponse.data.name);
                const today = new Date().toISOString().slice(0, 10);
                if (userResponse.data.id) {
                    const tasksResponse = await axios.get(`/api/tasks/mytasks`, {
                        params: { date: today }
                    });
                    const fetchedTasks = Array.isArray(tasksResponse.data) ? tasksResponse.data : [];
                    setTasks(fetchedTasks);
                    updateProgress(fetchedTasks);
                } else {
                    console.error("User ID is undefined.");
                }
            } catch (error) {
                console.error('Failed to fetch data', error);
            }
        };

        fetchUserData();
    }, []);

    const updateProgress = (tasks) => {
        const completedTasks = tasks.filter(task => task.completed).length;
        const totalTasks = tasks.length;
        const progressPercentage = totalTasks > 0 ? (completedTasks / totalTasks) * 100 : 0;
        setProgress(progressPercentage);
        setAllTasksCompleted(totalTasks > 0 && completedTasks === totalTasks);
    };

    const toggleTaskCompletion = async (taskId) => {
        try {
            await axios.put(`/api/tasks/${taskId}/toggle-completion`);
            setTasks(prevTasks => {
                const updatedTasks = prevTasks.map(task =>
                    task.taskId === taskId ? { ...task, completed: !task.completed } : task
                );
                updateProgress(updatedTasks);
                return updatedTasks;
            });
        } catch (error) {
            console.error('Failed to toggle task completion', error);
        }
    };

    return (
        <>
            <Header />
            <main className="main-content">
                <div className="content-wrapper">
                    <section className="left-column">
                        <div className="greeting-section">
                            <h1 className="greeting-text">Hello {userName}!</h1>
                            {tasks.length > 0 ? (
                                <>
                                    <Box sx={{ width: '100%', mt: 2, mb: 2 }} className={"progress-section"}>
                                        <Typography variant="h6" gutterBottom>Your Progress</Typography>
                                        <LinearProgress variant="determinate" color={"inherit"} value={progress} />
                                    </Box>
                                    <TaskList tasks={tasks} onToggleCompletion={toggleTaskCompletion} />
                                </>
                            ) : (
                                <div className="nothing-to-do-section">
                                    <img
                                        src={NothingToDoImage}
                                        className="nothing-to-do-image"
                                        alt="Nothing to do"
                                    />
                                    <Typography variant="h6">No tasks for today! Enjoy your free time!</Typography>
                                </div>
                            )}
                        </div>
                    </section>
                    <section className="right-column">
                        {tasks.length > 0 && <CongratulationsSection allTasksCompleted={allTasksCompleted} />}
                    </section>
                </div>
            </main>
        </>
    );
};

const CongratulationsSection = ({ allTasksCompleted }) => (
    <div className="congratulations-section">
        <img
            src={allTasksCompleted ? CongratulationsImage : WorkingImage}
            className="congratulations-image"
            alt={allTasksCompleted ? "Congratulations image" : "Working image"}
        />
        <h4 className="congratulations-text">{allTasksCompleted ? "Great job!" : "Keep working!"}</h4>
    </div>
);

export default HomePage;
