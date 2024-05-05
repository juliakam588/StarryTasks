import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../../axiosConfig';
import '../assets/styles/Role.css';
import familyImage from '../assets/images/role.png'

const Role = () => {
    const [roles, setRoles] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('/api/auth/roles', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        })
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
            const response = await axios.post('/api/auth/roles/select', { name: role.name }, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                }
            });
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
