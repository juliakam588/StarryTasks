import React from 'react';
import AuthenticationCard from '../components/AuthenticationCard';
import '../assets/styles/Login.css';
const LoginPage = () => {
    return (
        <AuthenticationCard>

            <form className="login-box">
                <div>
                    <label htmlFor="email" className="visually-hidden">Email</label>
                    <input type="email" id="email" placeholder="email" className="input-field" />
                    <label htmlFor="password" className="visually-hidden">Password</label>
                    <input type="password" id="password" placeholder="password" className="input-field" />
                    <button type="submit" className="login-button">Login</button>
                </div>
            </form>
        </AuthenticationCard>
    );
};

export default LoginPage;
