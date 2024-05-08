import * as React from 'react';
import dayjs from 'dayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DateField } from '@mui/x-date-pickers/DateField';

export default function MyDatePicker({ startDate, endDate, setStartDate, setEndDate }) {
    const handleChangeStartDate = (newValue) => {
        setStartDate(newValue.format('YYYY-MM-DD'));
    };

    const handleChangeEndDate = (newValue) => {
        setEndDate(newValue.format('YYYY-MM-DD'));
    };

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <div className="date-inputs">
                <DateField
                    format="DD - MM - YYYY"
                    label="Start Date"
                    value={dayjs(startDate)}
                    onChange={handleChangeStartDate}
                />
                <DateField
                    format="DD - MM - YYYY"
                    label="End Date"
                    value={dayjs(endDate)}
                    onChange={handleChangeEndDate}
                />
            </div>
        </LocalizationProvider>
    );
}
