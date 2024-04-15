import React from 'react';
import Header from '../components/Header/Header.jsx';
import '../App.css';
import '../assets/styles/HomePage.css';
import TaskList from '../components/TaskList.jsx';
import starIcon from '../assets/images/Star-Png-164.png';


const tasks = [
  { name: "Water Plants", reward: "6" },
  { name: "Clean your bed", reward: "2" },
];



const CongratulationsSection = () => (
  <div className="congratulations-section">
    <img
      src="https://cdn.builder.io/api/v1/image/assets/TEMP/f2c248ce3f1cad7c414f881f3fef6f3677d885d743a50b07a8f23d7e883d764d?apiKey=837dd5e8fc9e4355a18fd3b69845df5d&"
      className="congratulations-image"
      alt="Congratulations image"
    />
    <h4 className="congratulations-text">Great job!</h4>
  </div>
); 

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
              <TaskList tasks={tasks} />
            </div>
          </section>
          <section className="right-column">
          <CongratulationsSection />
          </section>
        </div>
      </main>
    </>
  );
};

export default HomePage;

