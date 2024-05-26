import * as Yup from 'yup';

export const taskValidationSchema = Yup.object({
    name: Yup.string().required('Task name is required'),
    categoryName: Yup.string().required('Category name is required'),
    stars: Yup.number().min(1, 'Stars must be at least 1').required('Stars are required'),
    startDate: Yup.date().required('Start date is required').min(new Date(), 'Start date cannot be in the past'),
    endDate: Yup.date().required('End date is required').min(Yup.ref('startDate'), 'End date cannot be before start date'),
    days: Yup.object().test(
        'at-least-one-day',
        'At least one day must be selected',
        value => Object.values(value).includes(true)
    ),
});

export const editTaskValidationSchema = Yup.object({
    taskName: Yup.string().required('Task name is required'),
    categoryName: Yup.string().required('Category name is required'),
    assignedStars: Yup.number().min(1, 'Stars must be at least 1').required('Stars are required'),
    scheduledDate: Yup.date().required('Scheduled date is required').min(new Date(), 'Scheduled date cannot be in the past'),
});