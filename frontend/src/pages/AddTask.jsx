import React from 'react';
import '../assets/styles/AddTask.css';
import Header from '../components/Header/Header';
import PlusSign from '../assets/images/plus-sign.png';
import StarIcon from '../assets/images/Star-Png-164.png';


const AddTaskForm = () => {
  return (
    <div className="container1">
        <Header />
      <form className="addtask-main-content">
        <input type="task-name" id="task-name" placeholder="Task name" className="add-task-name" />
        <select className="category-select">
          <option className="category-text">Select category</option>
        </select>

        <div className="checklist">
          <div className="checklist-column">
            <label className="day-container">Monday
              <input type="checkbox" />
              <span className="checkmark"></span>
            </label>
            <label className="day-container">Tuesday
            <input type="checkbox" defaultChecked />
              <span className="checkmark"></span>
            </label>
            <label className="day-container">Wednesday
              <input type="checkbox" />
              <span className="checkmark"></span>
            </label>
            <label className="day-container">Thursday
            <input type="checkbox" defaultChecked />
              <span className="checkmark"></span>
            </label>
          </div>
          <div className="checklist-column">
            <label className="day-container">Friday
              <input type="checkbox" />
              <span className="checkmark"></span>
            </label>
            <label className="day-container">Saturday
              <input type="checkbox" />
              <span className="checkmark"></span>
            </label>
            <label className="day-container">Sunday
            <input type="checkbox" defaultChecked />
              <span className="checkmark"></span>
            </label>
          </div>
        </div>

        <div className="reward-container">
          <img src={StarIcon} alt="Star icon" className="star-icon" />
          <input type="number" id="stars-number" placeholder="Number of stars" className="star-input" />
        </div>
        <button className="add-task-button">
          <img src={PlusSign} alt="Add task icon" className="add-task-icon" />
          <span className="add-task-text">Add Task</span>
        </button>
      </form>
    </div>
  );
};

export default AddTaskForm;
