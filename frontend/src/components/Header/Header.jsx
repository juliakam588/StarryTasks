import React from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {getUserRole} from '../../../axiosConfig';
import './Header.css';
import logo from '../../assets/images/logo.png';

const Header = () => {

    const navigate = useNavigate();
    const userRole = getUserRole();

    const handleLogout = () => {
        window.localStorage.removeItem('token');
        navigate('/login');
    };

    const handleHomeClick = () => {
        if (userRole === 'Parent') {
            navigate('/parent');
        } else if (userRole === 'Child') {
            navigate('/child');
        }
    };

    const handleRewardsClick = () => {
        if (userRole === 'Parent') {
            navigate('/parent-rewards');
        } else if (userRole === 'Child') {
            navigate('/rewards');
        }
    };


    return (
        <header>
            <div className="logo">
                <img src={logo} alt="StarryTasks Logo"/>
                <a href="#" className="logo-text">StarryTasks</a>
            </div>
            <input type="checkbox" id="menu-bar"/>
            <label htmlFor="menu-bar" id="menu-label">&#9776;</label>
            <nav className="navbar">
                <ul>
                    <li>
                        <button onClick={handleHomeClick}>Home</button>
                    </li>
                    <li>
                        <button onClick={handleRewardsClick}>Rewards</button>
                    </li>
                    {userRole === 'Parent' && <li><Link to="/stats">Stats</Link></li>}
                    <li>
                        <button onClick={handleLogout}>Logout</button>
                    </li>
                </ul>
            </nav>
        </header>
    );
};

export default Header;