import React, { useState } from 'react';
import { useFormik } from 'formik';
import { useNavigate } from 'react-router-dom';
import AuthenticationCard from '../components/AuthenticationCard';
import axios from '../../axiosConfig';
import { registerValidationSchema } from '../validation/authorizationValidationSchema.js';
import '../assets/styles/Register.css';

const RegisterPage = () => {
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState('');

    const formik = useFormik({
        initialValues: {
            email: '',
            firstName: '',
            password: '',
            confirmPassword: '',
            file: null,
        },
        validationSchema: registerValidationSchema,
        onSubmit: async (values, { setSubmitting }) => {
            try {
                const response = await axios.post('/api/auth/register', {
                    email: values.email,
                    firstName: values.firstName,
                    password: values.password,
                });
                localStorage.setItem('token', response.data.token);
                await upload(values.file);
                navigate('/login');
            } catch (error) {
                setSubmitting(false);
                const errorMsg = error.response && error.response.data && error.response.data.message
                    ? error.response.data.message
                    : (error.response && error.response.data) ? error.response.data : 'Registration failed';
                setErrorMessage(errorMsg);
                alert(errorMsg);
            }
        },
    });

    const upload = async (file) => {
        if (file) {
            const formData = new FormData();
            formData.append('file', file);
            try {
                await axios.post('/api/users/avatar', formData, {
                    headers: {
                        "Content-Type": "multipart/form-data"
                    }
                });
            } catch (error) {
                console.error('Upload failed:', error.response.data, localStorage.getItem("token"));
            }
        }
    };

    const onFileSelected = (event) => {
        const file = event.currentTarget.files[0];
        formik.setFieldValue('file', file);
    };

    return (
        <AuthenticationCard>
            <span className="join-clause">Join us!</span>
            <form className="login-box" onSubmit={formik.handleSubmit}>
                <div>
                    <input
                        type="email"
                        id="email"
                        placeholder="Email"
                        className="input-field"
                        value={formik.values.email}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                    />
                    {formik.touched.email && formik.errors.email ? (
                        <div className="error-message">{formik.errors.email}</div>
                    ) : null}

                    <input
                        type="text"
                        id="firstName"
                        placeholder="First name"
                        className="input-field"
                        value={formik.values.firstName}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                    />
                    {formik.touched.firstName && formik.errors.firstName ? (
                        <div className="error-message">{formik.errors.firstName}</div>
                    ) : null}

                    <input
                        type="password"
                        id="password"
                        placeholder="Password"
                        className="input-field"
                        value={formik.values.password}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                    />
                    {formik.touched.password && formik.errors.password ? (
                        <div className="error-message">{formik.errors.password}</div>
                    ) : null}

                    <input
                        type="password"
                        id="confirmPassword"
                        placeholder="Confirm password"
                        className="input-field"
                        value={formik.values.confirmPassword}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                    />
                    {formik.touched.confirmPassword && formik.errors.confirmPassword ? (
                        <div className="error-message">{formik.errors.confirmPassword}</div>
                    ) : null}

                    <input
                        type="file"
                        name="cover-select"
                        id="formFile"
                        accept="image/*"
                        onChange={onFileSelected}
                        onBlur={formik.handleBlur}
                    />
                    {formik.touched.file && formik.errors.file ? (
                        <div className="error-message">{formik.errors.file}</div>
                    ) : null}

                    {errorMessage && (
                        <div className="error-message">{errorMessage}</div>
                    )}

                    <button type="submit" className="login-button" disabled={formik.isSubmitting}>
                        Register
                    </button>
                </div>
            </form>
            <div className="register-navigation">
                <a href="/login">Go back to login page</a>
            </div>
        </AuthenticationCard>
    );
};

export default RegisterPage;
