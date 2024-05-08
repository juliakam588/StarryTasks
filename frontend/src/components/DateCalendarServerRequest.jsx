import React, { useState, useEffect } from 'react';
import axios from '../../axiosConfig';
import dayjs from 'dayjs';
import Badge from '@mui/material/Badge';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { PickersDay } from '@mui/x-date-pickers/PickersDay';
import { DateCalendar } from '@mui/x-date-pickers/DateCalendar';
import { DayCalendarSkeleton } from '@mui/x-date-pickers/DayCalendarSkeleton';

function ServerDay(props) {
    const { highlightedDays = [], day, outsideCurrentMonth, ...other } = props;
    const isSelected = !props.outsideCurrentMonth && highlightedDays.includes(props.day.format('YYYY-MM-DD'));

    return (
        <Badge
            key={props.day.toString()}
            overlap="circular"
            badgeContent={isSelected ? '🌚' : undefined}
        >
            <PickersDay {...other} outsideCurrentMonth={outsideCurrentMonth} day={day} />
        </Badge>
    );
}

export default function DateCalendarServerRequest({ childId, onDaySelect }) {
    const [currentDate, setCurrentDate] = useState(dayjs());
    const [tasks, setTasks] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    const fetchTasksForDate = (date) => {
        setIsLoading(true);

        axios.get(`/api/tasks/child/${childId}/date`, {
            params: { date: date.format('YYYY-MM-DD') },
        })
            .then(response => {
                setTasks(response.data || []);
                setIsLoading(false);
            })
            .catch(error => {
                setIsLoading(false);
            });
    };

    useEffect(() => {
        onDaySelect(tasks);
    }, [tasks])

    useEffect(() => {
        fetchTasksForDate(currentDate);
    }, [currentDate, childId]);

    const handleDayChange = (newDate) => {
        setCurrentDate(newDate);
    };

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DateCalendar
                value={currentDate}
                onChange={handleDayChange}
                loading={isLoading}
                renderLoading={() => <DayCalendarSkeleton />}
                slots={{
                    day: ServerDay,
                }}
                slotProps={{
                    day: {
                        highlightedDays: tasks.map(task => task.scheduledDate),

                    },
                }}
            />
        </LocalizationProvider>
    );

}
