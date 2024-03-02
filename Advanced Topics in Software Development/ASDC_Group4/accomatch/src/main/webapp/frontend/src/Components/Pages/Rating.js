import React, { useState } from 'react';
import axios from 'axios';
import { useParams , useNavigate } from 'react-router-dom';
export const Rating = () => {
    const navigate = useNavigate();
    const { applicationId } = useParams();
    const [reviews, setReviews] = useState([]);
    const [rating, setRating] = useState(0);
    const [comment, setComment] = useState('');

    const handleRatingChange = (event) => {
        setRating(event.target.value);
    };

    const handleCommentChange = (event) => {
        setComment(event.target.value);
    };

    const handleReviewClick = () => {
        const userId = sessionStorage.getItem('user_id');
        const payload= {
            "userId":userId,
            "applicationId":applicationId,
            "rating": rating,//document.getElementById("ratingNumber"),
            "comment": comment//document.getElementById("comment")
        }

        const authToken = sessionStorage.getItem("token"); // Replace with the actual authentication token
        axios.post("/api/reviews/createReview", payload,{ 
        headers : {
            Authorization: `Bearer ${authToken}`} // Include the authentication token in the headers
          })
            .then((response) => {
                // Update the reviews state with the newly submitted review

                setReviews([...reviews, response.data]);
                // Clear the rating and comment inputs
                console.log("Review added successfully.");
                navigate(`/posts/${applicationId}`);
            })
            .catch((error) => {
                console.log("Error submitting review:", error);
            });
    };
    
    return (
        <div>
            <h2>Reviews</h2>
            {reviews.map((review) => (
                <ReviewItem key={review.id} review={review} />
            ))}
            <div>
                <h3>Add a Review</h3>
                <div>
                    <label>Rating:</label>
                    <input type="number" id="ratingNumber" min={1} max={5} value={rating} onChange={handleRatingChange} />
                </div>
                <div>
                    <label>Comment:</label>
                    <textarea value={comment} id="comment" onChange={handleCommentChange} />
                </div>
                <button onClick={handleReviewClick}>Submit Review</button>
            </div>
        </div>
    );
};

const ReviewItem = ({ review }) => {
    return (
        <div>
            <div>Rating: {review.rating}</div>
            <div>Comment: {review.comment}</div>
        </div>
    );
};