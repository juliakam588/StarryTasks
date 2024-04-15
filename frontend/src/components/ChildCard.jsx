import React from 'react';
import starIcon from '../assets/images/Star-Png-164.png';
import './ChildCard.css';
import { Link } from 'react-router-dom';

const ChildCard = ({ name, avatarSrc, stars }) => {
  return (
    <div className="child-card">
      <img src={avatarSrc} alt={`${name}'s avatar`} className="child-avatar" />
      <div className="child-name">{name}</div>
      <div className="rewards">
        <img src={starIcon} className="stars-icon" alt="Stars" />
        <div className="stars-number">{stars}</div>
      </div>
      <Link to="/child-task" className="tasks-btn">Tasks</Link>
    </div>
  );
};

export default ChildCard;
