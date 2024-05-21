import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Role from './pages/Role';
import HomePage from './pages/HomePage';
import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import ChildTask from './pages/ChildTask';
import Stats from './pages/Stats';
import Parent from './pages/Parent';
import Rewards from './pages/Rewards';
import AddTask from './pages/AddTask';
import Invitation from './pages/Invitation';
import ParentRewards from './pages/ParentRewards';
import EditTaskForm from './pages/EditTask';



const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/child" element={<HomePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/child-task/:childId/tasks" element={<ChildTask />} />
                <Route path="/role" element={<Role />} />
                <Route path="/stats" element={<Stats />} />
                <Route path="/parent" element={<Parent />} />
                <Route path="/rewards" element={<Rewards />} />
                <Route path="/add-task/:childId" element={<AddTask />} />
                <Route path="/invitation" element={<Invitation />} />
                <Route path="/parent-rewards" element={<ParentRewards />} />
                <Route path="/edit-task/:childId/:taskId" element={<EditTaskForm />} />
            </Routes>
        </Router>
    );
};

export default App;
