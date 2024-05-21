import React, { useState, useEffect } from 'react';
import axios from '../../axiosConfig';
import Header from '../components/Header/Header';
import ChildCard from '../components/ChildCard';
import PlusSign from '../assets/images/plus-sign.png';
import '../assets/styles/Parent.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { CopyToClipboard } from 'react-copy-to-clipboard';

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
            const response = await axios.post('/api/invitations/generate-for-user', {});
            setInvitationCode(response.data.invitationCode);
            toast(
                <div>
                    <span>Invitation code: {response.data.invitationCode}</span>
                    <CopyToClipboard text={response.data.invitationCode}>
                        <button onClick={() => toast.success("Copied to clipboard!")}>Copy</button>
                    </CopyToClipboard>
                </div>,
                {
                    position: "top-center",
                    autoClose: false,
                    hideProgressBar: false,
                    closeOnClick: false,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                }
            );
        } catch (error) {
            console.error('Failed to generate invitation:', error);
            toast.error('Failed to generate invitation', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
            });
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
                <ToastContainer />
            </main>
        </div>
    );
};

export default Parent;
