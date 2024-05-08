
import axios from 'axios';


const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

axiosInstance.interceptors.request.use(function (config) {
    const token = localStorage.getItem('token');
    config.headers.Authorization = token ? `Bearer ${token}` : '';
    return config;
}, function (error) {
    return Promise.reject(error);
});

export default axiosInstance;
