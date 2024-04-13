import React from 'react';
import AuthenticationCard from '../components/AuthenticationCard';
import '../assets/styles/Register.css';

const RegisterPage = () => {
  return (
    <AuthenticationCard>
      <span className="join-clause">Join us!</span>
      <form className="login-box">
        <div>
          <input type="email" id="email" placeholder="email" className="input-field" />
          <input type="name" id="name" placeholder="first name" className="input-field" />
          <input type="password" id="password" placeholder="password" className="input-field" />
          <input type="password" id="password" placeholder="confirm password" className="input-field" />
          <button type="submit" className="login-button">Register</button>
        </div>

      </form>
    </AuthenticationCard>
  );
};

export default RegisterPage;
