import React from 'react';
import starIcon from '../assets/images/Star-Png-164.png';
import './ChildCard.css';
import { Link } from 'react-router-dom';

const ChildCard = ({ id, name, avatarSrc, stars }) => {
    return (
        <div className="child-card">
            <img src={`data:image/jpeg;base64 ,${avatarSrc}`} alt={`${name}'s avatar`} className="child-avatar" />
            <div className="child-name">{name}</div>
            <div className="rewards">
                <img src={starIcon} className="stars-icon" alt="Stars" />
                <div className="stars-number">{stars}</div>
            </div>
            <Link to={`/child-task/${id}/tasks`} className="tasks-btn">Tasks</Link>
        </div>
    );
};

export default ChildCard;
