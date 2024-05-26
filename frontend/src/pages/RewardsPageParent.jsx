import React, { useEffect, useState } from 'react';
import Header from '../components/Header/Header';
import '../assets/styles/Rewards.css';
import axios, {getUserInfo} from '../../axiosConfig.js';
import RewardItem from '../components/RewardItem';
import {useNavigate} from "react-router-dom";

const RewardsPageParent = () => {
    const navigate = useNavigate();
    const [rewards, setRewards] = useState([]);
    const [selectedRewardId, setSelectedRewardId] = useState(null);
    useEffect(() => {
        const userInfo = getUserInfo();
        if (!userInfo || userInfo.role === 'Child') {
            navigate('/login');
        }
    }, [navigate]);
    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        await fetchRewards();
    }

    const fetchRewards = async () => {
        try {
            const { data } = await axios.get('/api/rewards', {});
            setRewards(data);
        } catch (error) {
            console.error('Failed to fetch rewards:', error);
        }
    };

    const updateRewardCost = async (rewardId, newCostInStars) => {
        try {
            await axios.put(`/api/rewards/${rewardId}/cost`, { customCostInStars: newCostInStars });
            alert('Cost updated successfully');
            await fetchRewards();
        } catch (error) {
            console.error('Failed to update cost:', error);
            alert('Failed to update cost');
        }
    };

    return (
        <>
            <Header />
            <main className="rewards-page">
                <h2 className="rewards-title">Rewards</h2>
                <section className="rewards-list">
                    {rewards.map((reward) => (
                        <RewardItem
                            key={reward.id}
                            id={reward.id}
                            name={reward.name}
                            imgSrc={reward.imageUrl}
                            costInStars={reward.costInStars}
                            onUpdateCost={updateRewardCost}
                            isParent={true}
                        />
                    ))}
                </section>
            </main>
        </>
    );
};

export default RewardsPageParent;
