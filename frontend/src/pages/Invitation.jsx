import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios, { getUserInfo } from '../../axiosConfig';
import houseSearchImage from '../assets/images/Family stress-pana.svg';
import '../assets/styles/Invitation.css';

const Invitation = () => {
    const [invitationCode, setInvitationCode] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const userInfo = getUserInfo();
        if (!userInfo || (userInfo.role === 'Child' && userInfo.hasParent) || userInfo.role === 'Parent') {
            navigate('/login');
        }
    }, [navigate]);

    const handleInputChange = (e) => {
        setInvitationCode(e.target.value);
    };

    const handleJoinFamily = async (event) => {
        event.preventDefault();
        if (invitationCode.trim()) {
            try {
                await axios.post(`/api/invitations/${invitationCode}/accept`, {});
                alert('Successfully joined the family!');
                navigate('/child');
            } catch (error) {
                console.error('Error joining family:', error.response?.data?.message);
                alert('Failed to join family: ' + (error.response?.data?.message || 'Unknown error'));
            }
        } else {
            alert('Please enter a valid invitation code.');
        }
    };

    return (
        <div className="i-container">
            <div className="i-container1">
                <img src={houseSearchImage} alt="Searching for house"/>
                <h2>Join your household with StarryTasks!</h2>
            </div>
            <form className="i-container2" onSubmit={handleJoinFamily}>
                <div className="right-pane">
                    <p className="join-house"><b>Join an existing household with a code</b>
                        Get an invitation code from your parent. Fill in the code & join the household.</p>
                    <div className="textbox">
                        <input
                            type="text"
                            placeholder="Enter invitation code"
                            name="code"
                            value={invitationCode}
                            onChange={handleInputChange}
                        />
                    </div>
                    <button type="submit" className="btn">Join household</button>
                </div>
            </form>
        </div>
    );
};

export default Invitation;
