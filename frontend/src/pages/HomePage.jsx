import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Header from '../components/Header/Header.jsx';
import TaskList from '../components/TaskList';
import '../App.css';
import '../assets/styles/HomePage.css';



const CongratulationsSection = () => (
    <div className="congratulations-section">
        <img
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/f2c248ce3f1cad7c414f881f3fef6f3677d885d743a50b07a8f23d7e883d764d?apiKey=837dd5e8fc9e4355a18fd3b69845df5d&"
            className="congratulations-image"
            alt="Congratulations image"
        />
        <h4 className="congratulations-text">Great job!</h4>
    </div>
);


const HomePage = () => {
    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                const response = await axios.get('/api/tasks');
                setTasks(response.data);
            } catch (error) {
                console.error('Failed to fetch tasks', error);
            }
        };

        fetchTasks();
    }, []);

    return (
        <>
            <Header />
            <main className="main-content">
                <div className="content-wrapper">
                    <section className="left-column">
                        <div className="greeting-section">
                            <h1 className="greeting-text">Hello Sam!</h1>
                            <TaskList tasks={tasks} />
                        </div>
                    </section>
                    <section className="right-column">
                        <CongratulationsSection />
                    </section>
                </div>
            </main>
        </>
    );
};

export default HomePage;
