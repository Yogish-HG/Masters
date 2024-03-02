import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './PostsDetailsPage.css';
import "react-responsive-carousel/lib/styles/carousel.min.css";

import { Carousel } from 'react-responsive-carousel';
import { Link, useNavigate } from 'react-router-dom';

export const ApplicantAppliedPosts = () => {
    const navigate = useNavigate();
    const [posts, setPosts] = useState([]);
    const [selectedPost, setSelectedPost] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const handleDetailsClick = (postId) => {
    navigate(`/posts/${postId}`);
    };
  const openModal = (post) => {
    setSelectedPost(post);
    setIsModalOpen(true);
  }

  const closeModal = () => {
    setIsModalOpen(false);
  }


  useEffect(() => {
    const fetchPostDetails = async () => {
        try {
          const authToken = sessionStorage.getItem("token"); //  authentication token
          const user_Id = sessionStorage.getItem('user_id');          
          const postResponse = await axios.get(`/api/leaseholder/loggedinapplicant/get/list/applicant/${user_Id}`, {
            headers: {
              Authorization: `Bearer ${authToken}` //  authentication token in the headers
            }
          });
          setPosts(postResponse.data);
          console.log(posts)
        } catch (error) {
            console.log(error);
        }
    };

    fetchPostDetails().then(r => console.log("Axios error"));
}, []);
  return (
    <div className="dashboard-container">
    <div className="post-list">
    {posts.map((post, index) => (
          <div className="post" key={index}  onClick={()=>openModal(post)}>
            <div className="post-image">
              <img src={post.document} alt={`Post ${post.title}`} />
            </div>
            <div className="post-details"
            >
              <h3>{post.title}</h3>
              <p>{post.subtitle}</p>
              <p>Address: {post.address}</p>
              <p>City: {post.locationCity}</p>
              <p>Rent: {post.rent}</p>
              <p>Room Type: {post.roomType}</p>
              <p>Spots available: {post.size} </p>
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
            <p>Spots available: {selectedPost.size} </p>
            <p>Available From: {selectedPost.startDate}</p>
            <button onClick={() => handleDetailsClick(selectedPost.leaseholderApplicationId)}>More Details</button>
            <button onClick={closeModal}>Close</button>
          </div>
        </div>
      )}

</div>
);
   
};
