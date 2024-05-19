import React, { useState, useEffect } from 'react';
import axios from '../../axiosConfig';
import Header from '../components/Header/Header';
import ChildCard from '../components/ChildCard';
import PlusSign from '../assets/images/plus-sign.png';
import '../assets/styles/Parent.css';

const Parent = () => {
    const [parentName, setParentName] = useState('');
    const [invitationCode, setInvitationCode] = useState('');
    const [children, setChildren] = useState([]);

    useEffect(() => {
        const fetchFamilyData = async () => {
            try {
                const response = await axios.get('/api/parent/overview', {});
                setParentName(response.data.parentName);
                setChildren(response.data.children || []);
            } catch (error) {
                console.error('Error fetching family data:', error);
                setChildren([]);
            }
        };

        fetchFamilyData();
    }, []);


    const handleAddChild = async () => {
        try {
            const response = await axios.post('/api/invitations/generate-for-user', {}, {
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
                                key={child.id}
                                id={child.id}
                                name={child.name}
                                avatarSrc={child.profilePicture}
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
