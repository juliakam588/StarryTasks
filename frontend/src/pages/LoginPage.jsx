import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../../axiosConfig';
import AuthenticationCard from '../components/AuthenticationCard';
import '../assets/styles/Login.css';

const LoginPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const handleLogin = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('/api/auth/login', {
                email: email,
                password: password,
            });
            localStorage.setItem('token', response.data.token);


            if (response.data.hasRole) {
                fetchUserDetails();
            } else {
                navigate('/role');
            }
        } catch (error) {
            console.error('Login failed:', error.response);
            alert('Login failed: ' + error.response.data.message);
        }
    };

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
            <form className="login-box" onSubmit={handleLogin}>
                <div>
                    <label htmlFor="email" className="visually-hidden">Email</label>
                    <input type="email" id="email" placeholder="Email" className="input-field" value={email} onChange={e => setEmail(e.target.value)} />
                    <label htmlFor="password" className="visually-hidden">Password</label>
                    <input type="password" id="password" placeholder="Password" className="input-field" value={password} onChange={e => setPassword(e.target.value)} />
                    <button type="submit" className="login-button">Login</button>
                </div>
            </form>
        </AuthenticationCard>
    );
};

export default LoginPage;
