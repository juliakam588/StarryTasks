import React from 'react';
import '../assets/styles/authStyles.css';
import logo from '../assets/images/logo.png';

const AuthenticationCard = ({ children }) => {
  return (
    <main className="login-container">
      <section className="login-card">
        <div className="logo-container">
          <img src={logo} alt="StarryTasks Logo" className="logo-img" />
          <h1 className="app-name">StarryTasks</h1>
          {children}
        </div>
      </section>
    </main>
  );
};

export default AuthenticationCard;
