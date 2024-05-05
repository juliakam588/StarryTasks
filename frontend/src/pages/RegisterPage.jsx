import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthenticationCard from '../components/AuthenticationCard';
import axios from '../../axiosConfig';
import '../assets/styles/Register.css';

const RegisterPage = () => {
    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const navigate = useNavigate();

    const handleRegister = async (event) => {
        event.preventDefault();
        if (password !== confirmPassword) {
            alert("Passwords do not match");
            return;
        }
        try {
            const response = await axios.post('/api/auth/register', {
                email: email,
                firstName: name,
                password: password,
            });
            localStorage.setItem('token', response.data.token);
            navigate('/login');
        } catch (error) {
            console.error('Registration failed:', error.response.data);
            alert('Registration failed');
        }
    };

    return (
        <AuthenticationCard>
            <span className="join-clause">Join us!</span>
            <form className="login-box" onSubmit={handleRegister}>
                <div>
                    <input type="email" id="email" placeholder="Email" className="input-field" value={email} onChange={e => setEmail(e.target.value)} />
                    <input type="text" id="name" placeholder="First name" className="input-field" value={name} onChange={e => setName(e.target.value)} />
                    <input type="password" id="password" placeholder="Password" className="input-field" value={password} onChange={e => setPassword(e.target.value)} />
                    <input type="password" id="confirm-password" placeholder="Confirm password" className="input-field" value={confirmPassword} onChange={e => setConfirmPassword(e.target.value)} />
                    <button type="submit" className="login-button">Register</button>
                </div>
            </form>
        </AuthenticationCard>
    );
};

export default RegisterPage;
