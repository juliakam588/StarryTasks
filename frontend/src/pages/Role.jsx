import React, { useState, useEffect } from 'react';
import axios from '../../axiosConfig'; // Make sure this path is correct
import familyImage from '../assets/images/role.png';
import '../assets/styles/Role.css';

const Role = () => {
    const [roles, setRoles] = useState([]);

    useEffect(() => {
        axios.get('/api/auth/roles')
            .then(response => {
                setRoles(response.data);
            })
            .catch(error => console.error('Error fetching roles', error));
    }, []);

    // Function to handle role selection
    const handleRoleSelection = (role) => {
        console.log(`Role selected: ${role.name}`); // Log to the console which role was selected

        // Send the selected role to the backend
        axios.post('/api/auth/roles/select', role)
            .then(response => {
                console.log('Role selection successful:', response.data);
                // You can do something here after the role has been successfully posted
            })
            .catch(error => {
                console.error('Error posting role selection:', error);
                // Handle errors here
            });
    };

    return (
        <main className="parent-child-container">
            <div className="role-content-wrapper">
                <img src={familyImage} alt="Family portrait" className="family-image" />
                {roles.map(role => (
                    <button key={role.id} className="child-label" onClick={() => handleRoleSelection(role)}>
                        {role.name}
                    </button>
                ))}
            </div>
        </main>
    );
};

export default Role;
