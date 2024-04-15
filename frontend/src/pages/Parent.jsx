import React from 'react';
import Header from '../components/Header/Header';
import ChildCard from '../components/ChildCard';
import '../assets/styles/Parent.css';
import SamAvatar from '../assets/images/sam-avatar.jpg';
import AshleyAvatar from '../assets/images/ashley-avatar.jpg';


const Parent = () => {
  return (
    <div className="container">
      <Header />
      <main className="parent-main-content">
        <h1 className="greeting">Hello Anne!</h1>
        <div className="children-container">
          <div className="children-list">
            <ChildCard name="Sam" avatarSrc={SamAvatar} stars="10" />
            <ChildCard name="Ashley" avatarSrc={AshleyAvatar} stars="8" />
          </div>
        </div>
      </main>
    </div>
  );
};

export default Parent;
