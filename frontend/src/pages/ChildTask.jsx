import React from 'react';
import Header from '../components/Header/Header';
import '../assets/styles/ChildTask.css';
import TaskList from '../components/TaskList';
import AddButton from '../components/AddButton';
import plusSign from '../assets/images/plus-sign.png';
import { Link } from 'react-router-dom';


const tasks = [
    { name: "Water Plants", reward: "6" },
    { name: "Clean your bed", reward: "2" },
];

const TaskDay = ({ day, date, status }) => {
    return (
        <div className="day">
            <span>{day}</span><span className="date">{date}</span>
            <div className={`task-status ${status}`}></div>
        </div>
    );
};

const TasksCalendar = () => {
    const days = [
        { day: 'Mon', date: '09', status: 'completed' },
        { day: 'Tue', date: '10', status: 'not-completed' },
        { day: 'Wed', date: '11', status: 'completed' },
        { day: 'Thu', date: '12', status: 'not-completed' },
        { day: 'Fri', date: '13', status: 'completed' },
        { day: 'Sat', date: '14', status: 'not-completed' },
        { day: 'Sun', date: '15', status: 'completed' }
    ];

    return (
        <section className="calendar">
            <button className="nav-button prev">&lt;</button>
            <div className="week">
                {days.map((dayInfo) => (
                    <TaskDay key={dayInfo.day} {...dayInfo} />
                ))}
            </div>
            <button className="nav-button next">&gt;</button>
        </section>
    );
};

const ChildTasksPage = () => {
    return (
        <div className="container">
            <Header />

            <main className='lesser-container'>
                <h1 className="greeting">Sam's tasks</h1>
                <TasksCalendar />
                <TaskList tasks={tasks} />
                
            </main>
            <Link to="/add-task">
                <AddButton
                    iconSrc={plusSign}
                    buttonText="Add Task"
                    onClick={() => console.log('Add Task Clicked')}
                />
            </Link>


        </div>
    );
};

export default ChildTasksPage;
