import React, { useState, useEffect } from 'react';
import Axios from 'axios';
import Header from '../components/Header/Header';
import ChildCard from '../components/ChildCard';
import PlusSign from '../assets/images/plus-sign.png';
import '../assets/styles/Parent.css';

const Parent = () => {
    const [parentName, setParentName] = useState('');
    const [children, setChildren] = useState([]);
    const [invitationCode, setInvitationCode] = useState('');

    useEffect(() => {
        const fetchFamilyData = async () => {
            try {
                const response = await Axios.get('/api/parent/overview', {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                });
                setParentName(response.data.parentName);
                setChildren(response.data.children);
            } catch (error) {
                console.error('Error fetching family data:', error);
            }
        };

        fetchFamilyData();
    }, []);

    const handleAddChild = async () => {
        try {
            const response = await Axios.post('/api/invitations/generate-for-user', {}, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                }
            });
            setInvitationCode(response.data.invitationCode);
            alert(`Invitation code: ${response.data.invitationCode} - Give this code to your child to join.`);
        } catch (error) {
            console.error('Failed to generate invitation:', error);
            alert('Failed to generate invitation');
        }
    };

    return (
        <div className="container">
            <Header />
            <main className="parent-main-content">
                <h1 className="greeting">Hello {parentName}!</h1>
                <div className="children-container">
                    <div className="children-list">
                        {children.map(child => (
                            <ChildCard
                                key={child.name}
                                name={child.name}
                                avatarSrc={child.profilePictureUrl}
                                stars={child.currentStars}
                            />
                        ))}
                    </div>
                </div>
                <button className="add-task-button" onClick={handleAddChild}>
                    <img src={PlusSign} alt="Add child icon" className="add-task-icon" />
                    <span className="add-task-text">Add Child</span>
                </button>
            </main>
        </div>
    );
};

export default Parent;
