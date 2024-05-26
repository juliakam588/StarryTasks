import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios, {getUserInfo} from '../../axiosConfig';
import '../assets/styles/Role.css';
import familyImage from '../assets/images/role.png'

const Role = () => {
    const [roles, setRoles] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const userInfo = getUserInfo();
        if (userInfo.role === 'Child' || userInfo.role === 'Parent') {
            navigate('/login');
        }
    }, [navigate]);

    useEffect(() => {
        axios.get('/api/auth/roles', {})
            .then(response => {
                setRoles(response.data);
            })
            .catch(error => {
                console.error('Error fetching roles', error);
                alert('Failed to fetch roles, please login again');
                navigate('/login');
            });
    }, [navigate]);

    const handleRoleSelection = async (role) => {
        try {
            const response = await axios.post('/api/auth/roles/select', { name: role.name }, {});
            const newToken = response.data.token;
            localStorage.setItem('token', newToken);
            alert('Role selection successful');
            if (role.name === 'Parent') {
                navigate('/parent');
            } else if (role.name === 'Child') {
                navigate('/invitation');
            }
        } catch (error) {
            console.error('Error posting role selection:', error);
            alert('Failed to select role');
        }
    };

    return (
        <main className="parent-child-container">
            <div className="role-content-wrapper">
                <img src={familyImage} alt="Family portrait" className="family-image" />
            {roles.map(role => (
                <button key={role.id} className="child-label" onClick={() => handleRoleSelection(role)}>{role.name}</button>
            ))}
            </div>
        </main>
    );
};

export default Role;
