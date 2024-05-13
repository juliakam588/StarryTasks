import React from 'react';
import {Link, useNavigate} from 'react-router-dom';
import './Header.css';
import logo from '../../assets/images/logo.png';

const Header = () => {

    const navigate = useNavigate();

    const handleLogout = () => {
        window.localStorage.removeItem('token');
        navigate('/login');
    };

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
              <li><Link to="/">Home</Link></li>
              <li><Link to="/rewards">Rewards</Link></li>
              <li><Link to="/stats">Stats</Link></li>
              <li><button onClick={handleLogout}>Logout</button></li>
          </ul>
      </nav>
    </header>
  );
};

export default Header;