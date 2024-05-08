import React, { useState, useEffect } from 'react';
import axios from '../../axiosConfig';
import Header from '../components/Header/Header';
import TaskList from '../components/TaskList';
import AddButton from '../components/AddButton';
import { Link, useParams } from 'react-router-dom';
import plusSign from '../assets/images/plus-sign.png';
import dayjs from 'dayjs';
import DateCalendarServerRequest from '../components/DateCalendarServerRequest';

const ChildTasksPage = () => {
    const [tasks, setTasks] = useState([]);
    const [childName, setChildName] = useState('');
    const [selectedDate, setSelectedDate] = useState(dayjs());
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

    const handleDayChange = (tasks) => {
        setTasks(tasks);
    };

    return (
        <div className="container">
            <Header />
            <main className='lesser-container'>
                <h1 className="greeting">{childName}'s Tasks</h1>
                <DateCalendarServerRequest
                    childId={childId}
                    currentDate={selectedDate}
                    onDaySelect={handleDayChange}
                />
                <TaskList
                    key={selectedDate.format('YYYY-MM-DD')}
                    tasks={tasks}
                />
                <Link to={`/add-task/${childId}`}>
                    <AddButton iconSrc={plusSign} buttonText="Add Task" />
                </Link>
            </main>
        </div>
    );
};

export default ChildTasksPage;
