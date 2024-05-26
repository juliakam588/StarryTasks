import React, { useEffect, useState } from 'react';
import axios from '../../axiosConfig';
import Header from '../components/Header/Header';
import ConfirmationDialog from '../components/ConfirmationDialog';
import emptyImage from '../assets/images/empty.png';
import DeleteIcon from '@mui/icons-material/Delete';
import '../assets/styles/Rewards.css';

const ParentRewards = () => {
    const [childrenRewards, setChildrenRewards] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [openDialog, setOpenDialog] = useState(false);
    const [rewardIdToDelete, setRewardIdToDelete] = useState(null);

    useEffect(() => {
        const fetchChildrenRewards = async () => {
            try {
                const response = await axios.get('/api/parent/rewards', {});
                setChildrenRewards(response.data);
            } catch (error) {
                console.error('Failed to fetch children rewards:', error);
            } finally {
                setIsLoading(false);
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

    const handleDelete = async (userRewardId) => {
        try {
            await axios.delete(`/api/rewards/user-reward/${userRewardId}`, {});
            alert('Reward deleted successfully!');
            const response = await axios.get('/api/parent/rewards', {});
            setChildrenRewards(response.data);
        } catch (error) {
            console.error('Failed to delete reward:', error);
            alert('Failed to delete reward');
        }
    };

    const confirmDelete = (userRewardId) => {
        setRewardIdToDelete(userRewardId);
        setOpenDialog(true);
    };

    const handleConfirmDelete = () => {
        if (rewardIdToDelete) {
            handleDelete(rewardIdToDelete);
            setRewardIdToDelete(null);
        }
        setOpenDialog(false);
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
        setRewardIdToDelete(null);
    };

    return (
        <>
            <Header />
            <main className="parent-rewards-page">
                <h1 className="greeting">Children's Rewards</h1>
                {isLoading ? (
                    <p>Loading...</p>
                ) : childrenRewards.length === 0 ? (
                    <div className="empty-state">
                        <img src={emptyImage} alt="No rewards" className="empty-image" />
                        <p>No rewards to display</p>
                    </div>
                ) : (
                    childrenRewards.map(child => (
                        <div key={child.childId} className="child-rewards">
                            <h3>{child.childName}</h3>
                            {child.rewards.length === 0 ? (
                                <div className="empty-state">
                                    <img src={emptyImage} alt="No rewards" className="empty-image" />
                                    <p>No rewards to display for {child.childName}</p>
                                </div>
                            ) : (
                                <div key={child.childId} className="child-rewards-content">
                                    {child.rewards.map(reward => (
                                        <div key={reward.userRewardId} className="reward-item">
                                            <DeleteIcon className="delete-btn" onClick={() => confirmDelete(reward.userRewardId)}>
                                            </DeleteIcon>
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
                            )}
                        </div>
                    ))
                )}
                <ConfirmationDialog
                    open={openDialog}
                    onClose={handleCloseDialog}
                    onConfirm={handleConfirmDelete}
                    title="Confirm Delete"
                    message="Are you sure you want to delete this reward?"
                />
            </main>
        </>
    );
};

export default ParentRewards;
