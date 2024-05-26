import React, { useEffect, useState } from 'react';
import Header from '../components/Header/Header';
import starIcon from '../assets/images/Star-Png-164.png';
import '../assets/styles/Rewards.css';
import axios from '../../axiosConfig.js';
import RewardItem from '../components/RewardItem';

const RewardsPageChild = () => {
    const [rewards, setRewards] = useState([]);
    const [stars, setStars] = useState(0);
    const [selectedRewardId, setSelectedRewardId] = useState(null);

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        await fetchStars();
        await fetchRewards();
    }

    const fetchStars = async () => {
        try {
            const response = await axios.get('/api/rewards/stars', {});
            setStars(response.data);
        } catch (error) {
            console.error('Failed to fetch stars:', error);
        }
    };

    const fetchRewards = async () => {
        try {
            const { data } = await axios.get('/api/rewards', {});
            setRewards(data);
        } catch (error) {
            console.error('Failed to fetch rewards:', error);
        }
    };

    const exchangeReward = async (rewardId) => {
        try {
            const response = await axios.post(`/api/rewards/exchange?rewardId=${rewardId}`, {});
            alert('Reward exchanged successfully!');
            await loadData();
        } catch (error) {
            console.error('Failed to exchange reward:', error);
            alert('Failed to exchange reward');
        }
    };

    return (
        <>
            <Header/>
            <main className="rewards-page">
                <h2 className="rewards-title">Rewards</h2>
                <section className="stars-container">
                    <div className="stars-content">
                        <div className="stars-text-col">
                            <p className="stars-text">Stars acquired</p>
                        </div>
                        <div className="stars-count-col">
                            <div className="stars-count-wrapper">
                                <span className="stars-count">{stars}</span>
                                <img src={starIcon} alt="Stars icon" className="stars-img"/>
                            </div>
                        </div>
                    </div>
                </section>
                <section className="rewards-list">
                    {rewards.map((reward) => (
                        <RewardItem
                            key={reward.id}
                            id={reward.id}
                            name={reward.name}
                            imgSrc={reward.imageUrl}
                            progress={Math.round((stars / reward.costInStars) * 100)}
                            onSelectReward={setSelectedRewardId}
                            isSelected={selectedRewardId === reward.id}
                            costInStars={reward.costInStars}
                        />
                    ))}
                </section>
                {selectedRewardId !== null &&
                    <button className="exchange-btn" onClick={() => exchangeReward(selectedRewardId)}>Exchange</button>}
            </main>
        </>
    );
};

export default RewardsPageChild;
