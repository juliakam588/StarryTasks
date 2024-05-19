import React from 'react';
import '../assets/styles/Rewards.css';

const RewardItem = ({ id, imgSrc, name, progress, onSelectReward, isSelected }) => {
    const handleClick = () => {
        if (progress >= 100) {
            onSelectReward(id);
        }
    };
    const itemClass = isSelected ? "reward-item reward-item-selected" : "reward-item";

    return (
        <article className={itemClass} onClick={handleClick} style={{ cursor: 'pointer' }}>
            <img src={`data:image/png;base64,${imgSrc}`} alt={`${name} reward`} className="reward-img" />
            <h3 className="reward-name">{name}</h3>
            <progress max="100" value={progress} className="reward-progress"></progress>
            {progress === 100 && <button className="select-reward-btn" onClick={handleClick}>Select</button>}
        </article>
    );
};

export default RewardItem;
