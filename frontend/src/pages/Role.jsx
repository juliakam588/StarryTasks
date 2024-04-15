import React from 'react';
import familyImage from '../assets/images/role.png';
import '../assets/styles/Role.css';

const Role = () => {
  return (
    <main className="parent-child-container">
      <div className="role-content-wrapper">
        <img src={familyImage} alt="Family portrait" className="family-image" />
        <button className="parent-label">Parent</button>
        <button className="child-label">Child</button>
      </div>
    </main>
  );
};

export default Role;
