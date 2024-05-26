import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../../axiosConfig';
import AuthenticationCard from '../components/AuthenticationCard';
import * as Yup from 'yup';
import { useFormik } from 'formik';
import '../assets/styles/Login.css';
import { loginValidationSchema } from '../validation/authorizationValidationSchema';



const LoginPage = () => {
    const navigate = useNavigate();
    const formik = useFormik({
        initialValues: {
            email: '',
            password: '',
        },
        validationSchema: loginValidationSchema,
        onSubmit: async (values, { setSubmitting }) => {
            try {
                const response = await axios.post('/api/auth/login', values);
                localStorage.setItem('token', response.data.token);

                if (response.data.hasRole) {
                    fetchUserDetails();
                } else {
                    navigate('/role');
                }
            } catch (error) {
                console.error('Login failed:', error.response);
                alert('Login failed: ' + error.response.data.message);
                setSubmitting(false);
            }
        },
    });

    const fetchUserDetails = async () => {
        try {
            const response = await axios.get('/api/auth/user-details', {});

            if (response.data.role.name === 'Parent') {
                navigate('/parent');
            } else if (response.data.role.name === 'Child') {
                if (response.data.hasParent) {
                    navigate('/child');
                } else {
                    navigate('/invitation');
                }
            }
        } catch (error) {
            console.error('Error fetching user details:', error.response);
            alert('Failed to fetch user details: ' + error.response.data.message);
        }
    };

    return (
        <AuthenticationCard>
            <form className="login-box" onSubmit={formik.handleSubmit}>
                    <label htmlFor="email" className="visually-hidden">Email</label>
                    <input
                        type="email"
                        id="email"
                        placeholder="Email"
                        className="input-field"
                        {...formik.getFieldProps('email')}
                    />
                    {formik.touched.email && formik.errors.email ? (
                        <div className="error-message">{formik.errors.email}</div>
                    ) : null}
                    <label htmlFor="password" className="visually-hidden">Password</label>
                    <input
                        type="password"
                        id="password"
                        placeholder="Password"
                        className="input-field"
                        {...formik.getFieldProps('password')}
                    />
                    {formik.touched.password && formik.errors.password ? (
                        <div className="error-message">{formik.errors.password}</div>
                    ) : null}
                    <button type="submit" className="login-button" disabled={formik.isSubmitting}>
                        Login
                    </button>
            </form>
            <div className="register-navigation">
                <p>No account? <a href="/register">Register now!</a></p>
            </div>
        </AuthenticationCard>

    );
};

export default LoginPage;
