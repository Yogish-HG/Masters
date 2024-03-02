import React, { useEffect, useState } from "react";
import axios from 'axios';
import './UserProfile.css';
import { Link, useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';

export const UserProfile = () => {
    const {user_Id} = useParams();
    const [users, setUser] = useState([]);

    const navigate = useNavigate();


    useEffect(() => {
        const fetchUserDetails = async () => {
            try {
                const userResponse = await axios.get(`/api/users/get/${user_Id}`);
                setUser(userResponse.data);
                console.log(userResponse.data)
            } catch (error) {
                console.log(error);
            }
        };

        fetchUserDetails().then(r => console.log("Axios error"));
    }, [user_Id]);

    const handleDetailsClick = (userID) => {
        navigate(`/userprofile/${user_Id}`);
    };

    return (
        <div className="dashboard-container">


                        <div >
                            <p>email: {users.email}</p>
                            <p>Name: {users.name}</p>
                            <p>Age: {users.age}</p>
                            <p>Gender: {users.gender}</p>
                            <p>Mobile: {users.mobile}</p>
                            <p>Address: {users.address}</p>
                            <p>
                                <span className="line"><a href="/forgetpassword">Reset Password</a>
                                </span>
                            </p>
                        </div>
        </div>

    );
};