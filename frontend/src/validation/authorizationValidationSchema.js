import * as Yup from 'yup';

export const loginValidationSchema = Yup.object({
    email: Yup.string()
        .email('Invalid email address')
        .matches(/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/, 'Email must contain "@" and a valid domain')
        .required('Email is required'),
    password: Yup.string().required('Password is required')
});

export const registerValidationSchema = Yup.object({
    email: Yup.string()
        .email('Invalid email address')
        .required('Email is required'),
    firstName: Yup.string()
        .required('First name is required'),
    password: Yup.string()
        .required('Password is required')
        .min(8, 'Password must be at least 8 characters'),
    confirmPassword: Yup.string()
        .oneOf([Yup.ref('password'), null], 'Passwords must match')
        .required('Confirm password is required'),
    file: Yup.mixed()
        .required('Profile picture is required')
        .test('fileSize', 'File size is too large', value => {
            return value && value.size <= 5 * 1024 * 1024; // 5 MB
        })
        .test('fileType', 'Unsupported file format', value => {
            return value && ['image/jpeg'].includes(value.type);
        }),
});