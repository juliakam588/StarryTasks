import React from 'react';
import './AddButton.css';

const AddButton = ({ iconSrc, buttonText, onClick }) => {
  return (
    <button className="add-btn" onClick={onClick}>
      {iconSrc && <img src={iconSrc} alt="icon" className="button-icon" />}
      <span className="button-text">{buttonText}</span>
    </button>
  );
};

export default AddButton;
