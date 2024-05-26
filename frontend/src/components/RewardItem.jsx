import React, { useState } from 'react';
import '../assets/styles/Rewards.css';

const RewardItem = ({ id, imgSrc, name, progress, onSelectReward, isSelected, costInStars, onUpdateCost, isParent }) => {
    const [newCost, setNewCost] = useState(costInStars);

    const handleClick = () => {
        if (progress >= 100 && !isParent) {
            onSelectReward(id);
        }
    };

    const handleCostChange = (e) => {
        setNewCost(e.target.value);
    };

    const handleUpdateCost = () => {
        onUpdateCost(id, newCost);
    };

    const itemClass = isSelected ? "reward-item reward-item-selected" : "reward-item";

    return (
        <article className={itemClass} onClick={!isParent ? handleClick : undefined} style={{ cursor: 'pointer' }}>
            <img src={`data:image/png;base64,${imgSrc}`} alt={`${name} reward`} className="reward-img" />
            <h3 className="reward-name">{name}</h3>
            {isParent ? (
                <>
                    <input type="number" value={newCost} onChange={handleCostChange} className="reward-cost-input" />
                    <button onClick={handleUpdateCost} className="update-cost-btn">Update Cost</button>
                </>
            ) : (
                <>
                    <progress max="100" value={progress} className="reward-progress"></progress>
                    {progress === 100 && <button className="select-reward-btn" onClick={handleClick}></button>}
                </>
            )}
        </article>
    );
};

export default RewardItem;
