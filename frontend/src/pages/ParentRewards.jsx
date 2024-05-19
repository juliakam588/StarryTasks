import React, { useEffect, useState } from 'react';
import axios from '../../axiosConfig';
import Header from '../components/Header/Header';
import '../assets/styles/Rewards.css';

const ParentRewards = () => {
    const [childrenRewards, setChildrenRewards] = useState([]);

    useEffect(() => {
        const fetchChildrenRewards = async () => {
            try {
                const response = await axios.get('/api/parent/rewards', {});
                setChildrenRewards(response.data);
            } catch (error) {
                console.error('Failed to fetch children rewards:', error);
            }
        };

        fetchChildrenRewards();
    }, []);

    const handleApprove = async (userRewardId) => {
        try {
            await axios.post(`/api/rewards/approve?userRewardId=${userRewardId}`, {}, {});
            alert('Reward approved successfully!');
            const response = await axios.get('/api/parent/rewards', {});
            setChildrenRewards(response.data);
        } catch (error) {
            console.error('Failed to approve reward:', error);
            alert('Failed to approve reward');
        }
    };

    const handleReject = async (userRewardId) => {
        try {
            await axios.post(`/api/rewards/reject?userRewardId=${userRewardId}`, {}, {});
            alert('Reward rejected successfully!');
            const response = await axios.get('/api/parent/rewards', {});
            setChildrenRewards(response.data);
        } catch (error) {
            console.error('Failed to reject reward:', error);
            alert('Failed to reject reward');
        }
    };

    return (
        <>
            <Header />
            <main className="parent-rewards-page">
                <h1 className="greeting">Children's Rewards</h1>
                {childrenRewards.map(child => (
                    <div key={child.childId} className="child-rewards">
                        <h3>{child.childName}</h3>
                        <div key={child.childId} className="child-rewards-content">

                            {child.rewards.map(reward => (
                                <div key={reward.userRewardId} className="reward-item">
                                    <img src={`data:image/png;base64,${reward.imageUrl}`} alt={reward.name}
                                         className="reward-img"/>
                                    <h4>{reward.name}</h4>
                                    <p>Requested on: {reward.redemptionDate}</p>
                                    {!reward.isGranted && (
                                        <>
                                            <button className="decision-btn" onClick={() => handleApprove(reward.userRewardId)}>Approve</button>
                                            <button className="decision-btn" onClick={() => handleReject(reward.userRewardId)}>Reject</button>
                                        </>
                                    )}
                                    {reward.isGranted && <span>Approved</span>}
                                </div>
                            ))}
                        </div>
                        </div>
                        ))}
                    </main>
                    </>
                    );
                };

                export default ParentRewards;
