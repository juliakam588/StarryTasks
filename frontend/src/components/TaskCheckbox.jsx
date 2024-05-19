import * as React from 'react';
import Checkbox from '@mui/material/Checkbox';
import CheckCircleRoundedIcon from '@mui/icons-material/CheckCircleRounded';
import CheckCircleOutlineRoundedIcon from '@mui/icons-material/CheckCircleOutlineRounded';
import { amber } from '@mui/material/colors';

const TaskCheckbox = ({ checked = false, onChange }) => {
    return (
        <Checkbox
            checked={checked}
            onChange={onChange}
            sx={{
                color: amber[500],
                '&.Mui-checked': {
                    color: amber[500],
                },
            }}
            icon={<CheckCircleOutlineRoundedIcon />}
            checkedIcon={<CheckCircleRoundedIcon />}
        />
    );
};

export default TaskCheckbox;