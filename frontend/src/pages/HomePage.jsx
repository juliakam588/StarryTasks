import React from 'react';
import Header from '../components/Header/Header.jsx';
import '../App.css';
import '../assets/styles/HomePage.css';
import starIcon from '../assets/images/Star-Png-164.png';



const HomePage = () => {
  return (
    <>
      <Header />
      <main className="main-content">
        <div className="content-wrapper">
          <section className="left-column">
            <div className="greeting-section">
              <h1 className="greeting-text">Hello Sam!</h1>
              <div className="progress-section">
                <div className="progress-label">Your Progress</div>
                <div className="progress-bar"></div>
              </div>
              <div className="tasks-section">
                <article className="task-item">
                  <div className="task-details">
                    <h4 className="task-name">Water Plants</h4>
                    <div className="task-reward">
                      <div className="reward-label">Reward:</div>
                      <div className="reward-amount">6</div>
                      <img src={starIcon} className="reward-icon" alt="Reward icon" />
                    </div>
                  </div>
                  <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/8042543954b7b2cddf839b29c60f0922c108d90ebcec1a9133638b53cebdd98c?apiKey=837dd5e8fc9e4355a18fd3b69845df5d&" className="task-icon" alt="Task icon" />
                </article>
                <article className="task-item">
                  <div className="task-details">
                    <h4 className="task-name">Clean your bed</h4>
                    <div className="task-reward">
                      <div className="reward-label">Reward:</div>
                      <div className="reward-amount">2</div>
                      <img src={starIcon} className="reward-icon" alt="Reward icon" />
                    </div>
                  </div>
                  <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/8042543954b7b2cddf839b29c60f0922c108d90ebcec1a9133638b53cebdd98c?apiKey=837dd5e8fc9e4355a18fd3b69845df5d&" className="task-icon" alt="Task icon" />
                </article>
              </div>
            </div>
          </section>
          <section className="right-column">
            <div className="congratulations-section">
              <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/f2c248ce3f1cad7c414f881f3fef6f3677d885d743a50b07a8f23d7e883d764d?apiKey=837dd5e8fc9e4355a18fd3b69845df5d&" className="congratulations-image" alt="Congratulations image" />
              <h4 className="congratulations-text">Great job!</h4>
            </div>
          </section>
        </div>
      </main>
    </>
  );
};

export default HomePage;
