import React from 'react';
import './Header.css';
import logo from '../../assets/images/logo.png';

const Header = () => {
  return (
    <header>
      <div className="logo">
        <img src={logo} alt="StarryTasks Logo" />
        <a href="#" className="logo-text">StarryTasks</a>
      </div>
      <input type="checkbox" id="menu-bar" />
      <label htmlFor="menu-bar" id="menu-label">&#9776;</label>
      <nav className="navbar">
        <ul>
          <li><a href="#">Home</a></li>
          <li><a href="#">Rewards</a></li>
          <li><a href="#">Me</a></li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;