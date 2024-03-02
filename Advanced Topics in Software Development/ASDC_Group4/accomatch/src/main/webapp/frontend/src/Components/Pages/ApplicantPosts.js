import React, { useEffect, useState } from "react";
import axios from 'axios';
import './ApplicantPosts.css';
import { Link, useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';

export const ApplicantPosts = () => {
    const dummyImage = "https://www.pngitem.com/pimgs/m/581-5813504_avatar-dummy-png-transparent-png.png";
    const {user_Id} = useParams();
    const [posts, setPosts] = useState([]);
    const [selectedPost, setSelectedPost] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const navigate = useNavigate();


    useEffect(() => {
        const fetchPostDetails = async () => {
            try {
              const authToken = sessionStorage.getItem("token"); //  authentication token
              
              const postResponse = await axios.get(`/api/leaseholder/loggedinapplicant/get/list/applicant/${user_Id}`, {
                headers: {
                  Authorization: `Bearer ${authToken}` //  authentication token in the headers
                }
              });
              setPosts(postResponse.data);
            } catch (error) {
                console.log(error);
            }
        };

        fetchPostDetails().then(r => console.log("Axios error"));
    }, [user_Id]);


    const closeModal = () => {
        setIsModalOpen(false);
    }

    const apply = () => {
        // Handle apply logic here
        closeModal();
    }

    return (
        <div className="dashboard-container">
            <div className="post-list">
                {posts.map((post, index) => (
                    <div className="post" key={index}>
                        <div className="post-image">
                            <img src={dummyImage} alt={`Post ${post.title}`}/>
                        </div>
                        <div className="post-details">

                            <p>Address: {post.address}</p>
                            <p>City: {post.locationCity}</p>
                            <p>Rent: {post.rent}</p>
                            <p>Room Type: {post.roomType}</p>
                            <p>Available Spot: {post.size} </p>
                            <p>Available From: {post.startDate}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>)
}