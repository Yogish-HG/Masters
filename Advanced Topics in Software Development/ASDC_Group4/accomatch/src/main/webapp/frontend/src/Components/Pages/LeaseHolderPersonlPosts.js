import React, { useEffect, useState } from "react";
import axios from 'axios';
import './Posts.css';
import { Link, useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';

export const LeaseHolderPersonlPosts = () => {
    const { user_Id } = useParams();
    const userid=sessionStorage.getItem("user_id");
    const [posts, setPosts] = useState([]);
    const [selectedPost, setSelectedPost] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const navigate = useNavigate();




    useEffect(() => {
        const fetchPostDetails = async () => {
            try {
              const authToken = sessionStorage.getItem("token"); //  authentication token
              
              const postResponse = await axios.get(`/api/leaseholder/dashboard/get/list/getListOfPersonalPosts/${userid}`, {
                headers: {
                  Authorization: `Bearer ${authToken}` // authentication token in the headers
                }
              });
              setPosts(postResponse.data);
            } catch (error) {
              console.error(error);
            }
          };
          

        fetchPostDetails().then(r => console.log("Axios error coming"));
    }, [userid]);

    const handleDetailsClick = (postId) => {
        navigate(`/personalpostdetails/${postId}`);
    };

    const openModal = (post) => {
        setSelectedPost(post);
        setIsModalOpen(true);
    }
console.log(isModalOpen,selectedPost);
    const closeModal = () => {
        setIsModalOpen(false);
    }


    return (
        <div className="dashboard-container">
            <div className="post-list">
                {posts.map((post, index) => (
                    <div className="post" key={index}  onClick={()=>openModal(post)}>
                        <div className="post-image">
                            <img src={post.document} alt={`Post ${post.title}`} />
                        </div>
                        <div className="post-details">
                            <h3>{post.title}</h3>
                            <p>{post.subtitle}</p>
                            <p>Address: {post.address}</p>
                            <p>City: {post.locationCity}</p>
                            <p>Rent: {post.rent}</p>
                            <p>Room Type: {post.roomType}</p>
                            <p>Spots available: {post.size}</p>
                            <p>Available From: {post.startDate}</p>
                        </div>
                    </div>
                ))}
            </div>
            {isModalOpen && (
                <div className="custom-modal">
                    <div className="modal-content">
                        <h3>{selectedPost.title}</h3>
                        <p>{selectedPost.subtitle}</p>
                        <p>Address: {selectedPost.address}</p>
                        <p>City: {selectedPost.locationCity}</p>
                        <p>Rent: {selectedPost.rent}</p>
                        <p>Room Type: {selectedPost.roomType}</p>
                        <p>Spots Available: {selectedPost.size}</p>
                        <p>Available From: {selectedPost.startDate}</p>
                        <button onClick={() => handleDetailsClick(selectedPost.leaseholderApplicationId)}>More Details</button>
                        <button onClick={closeModal}>Close</button>
                    </div>
                </div>
            )}
        </div>
    );
};
