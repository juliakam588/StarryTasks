import React, { useEffect, useState } from 'react';
import axios from '../../axiosConfig';
import Header from '../components/Header/Header';
import '../assets/styles/Stats.css'
import {
    BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer
} from 'recharts';

const Stats = () => {
    const [stats, setStats] = useState(null);

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const response = await axios.get('/api/stats', {});
                setStats(response.data);
            } catch (error) {
                console.error('Failed to fetch stats', error);
            }
        };

        fetchStats();
    }, []);

    if (!stats) {
        return <p>Loading...</p>;
    }

    const formatDataForChart = (data) => {
        return data.map((weekData, index) => {
            const formattedData = { name: `Week ${index + 1}` };
            for (const [childName, value] of Object.entries(weekData)) {
                formattedData[childName] = value;
            }
            return formattedData;
        });
    };

    const colors = ["#8884d8", "#82ca9d", "#ffc658", "#ff6347", "#32cd32", "#8a2be2", "#ff4500", "#00ced1"];

    return (
        <>
            <Header />
            <main className="stats-page">
                <h1 className="greeting">Stats</h1>
                <div className="cumulative-stats">
                    <h2>Cumulative Stars</h2>
                    <ResponsiveContainer width="100%" height={300}>
                        <BarChart
                            data={formatDataForChart(stats.weeklyStars)}
                            margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
                        >
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="name" />
                            <YAxis />
                            <Tooltip />
                            <Legend />
                            {Object.keys(stats.weeklyStars[0]).map((childName, index) => (
                                <Bar key={childName} dataKey={childName} fill={colors[index % colors.length]} />
                            ))}
                        </BarChart>
                    </ResponsiveContainer>
                </div>
                <div className="cumulative-stats">
                    <h2>Cumulative Incomplete Tasks</h2>
                    <ResponsiveContainer width="100%" height={300}>
                        <BarChart
                            data={formatDataForChart(stats.weeklyIncompleteTasks)}
                            margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
                        >
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="name" />
                            <YAxis />
                            <Tooltip />
                            <Legend />
                            {Object.keys(stats.weeklyIncompleteTasks[0]).map((childName, index) => (
                                <Bar key={childName} dataKey={childName} fill={colors[index % colors.length]} />
                            ))}
                        </BarChart>
                    </ResponsiveContainer>
                </div>
            </main>
        </>
    );
};

export default Stats;
