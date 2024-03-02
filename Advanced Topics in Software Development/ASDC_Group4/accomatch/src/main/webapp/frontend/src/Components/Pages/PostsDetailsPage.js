import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './PostsDetailsPage.css';
import "react-responsive-carousel/lib/styles/carousel.min.css";

import { Carousel } from 'react-responsive-carousel';
import { Link, useNavigate } from 'react-router-dom';

export const PostsDetailsPage = () => {
  const navigate =useNavigate();

  const { applicationId } = useParams();
  const [alreadyApplied,setAlreadyApplied] = useState(false);
  const [post, setPost] = useState(null);
  const [ratings,setRatings] = useState([]);
  const [images, setImages] = useState([]);
  const [foodPreferences, setFoodPreferences] = useState([]);
  const [genderPreferences, setGenderPreferences] = useState([]);
  const [reviewResponse, setReviewResponse] = useState([]);
  const [errMsg, setErrMsg] =useState ('');
  const [success, setSuccess] = useState(false);
  const [roomId,setRoomId]=useState();
  let isLeaseHolder=false;
  if(sessionStorage.getItem("type")=="LH"){
    isLeaseHolder=true;
  }
  const handleReviewClick =()=>{
      navigate(`/ratingform/${applicationId}`)
  }
  const handleChatSubmit=async ()=>{
    let bodyObj={
      user_id : Number(sessionStorage.getItem("user_id")),
      application_id : Number(applicationId)
    }
    fetch(`/api/room/getRoomId`,{
      method:"POST",
      headers: { "Content-Type": "application/json",
      "Authorization": `Bearer ${sessionStorage.getItem("token")}`}, // Include the authentication token in the headers
      body: JSON.stringify(bodyObj),
    }).then((response) => {
      console.log(response);
      if(response.status===200){
      }
      return response.text(); // Read the response data as text
    })
    .then((data) => {
      console.log(data); // Log the response data
      setRoomId(data);
      navigate(`/chat/${data}`,{
      roomId:data
      });
      if (data === "success") {
      setSuccess(true);
      } else {
      setErrMsg("Login failed. Please try again."); // Set an appropriate error message
      }
    })
    .catch((error) => {
      console.error(error);
      toast.error('An error occurred while loading posts. Please try again.', {
      position: toast.POSITION.TOP_RIGHT
    });
      setErrMsg("An error occurred. Please try again."); // Set an appropriate error message
    });

  }
  const handleApplySubmit =async () => {
    let bodyObj = {
        user_id:sessionStorage.getItem("user_id"),
        application_id:applicationId,
        status:"Pending"
    }

    fetch("/api/applicant/apply", {
        method: "POST",
        headers: { "Content-Type": "application/json",
        "Authorization": `Bearer ${sessionStorage.getItem("token")}`}, // Include the authentication token in the headers
        body: JSON.stringify(bodyObj),
    })
    .then((response) => {
        console.log(response);
        if(response.status===200){
            navigate("/posts");
        }
        return response.json; // Read the response data as text
    })
    .then((data) => {
        console.log(data); // Log the response data
        if (data === "Applied Successfully") {
        setSuccess(true);
        } else {
        setErrMsg("Login failed. Please try again."); // Set an appropriate error message
        }
    })
    .catch((error) => {
      console.error(error);
      toast.error('An error occurred while loading posts. Please try again.', {
        position: toast.POSITION.TOP_RIGHT
      });
        setErrMsg("An error occurred. Please try again."); // Set an appropriate error message
    });
    }
  useEffect(() => {
  
  const fetchPostDetails = async () => {
  console.log(applicationId);
  try {
    const authToken = sessionStorage.getItem("token"); // Retrieve the authentication token from sessionStorage

    const postResponse = await axios.get(`/api/leaseholder/dashboard/get/post/details/${applicationId}`, {
      headers: {
        'Authorization': `Bearer ${authToken}` // Include the retrieved authentication token in the headers
      }
    });
    setPost(postResponse.data);
    console.log(postResponse.data);
    const isUserApplied = await axios.post(`/api/applicant/isApplied`,{
      user_id:sessionStorage.getItem("user_id"),
      application_id:applicationId
    },{
      headers: {
        'Authorization': `Bearer ${authToken}` // Include the retrieved authentication token in the headers
    }});
    setAlreadyApplied(isUserApplied.data);
    const imagesResponse = await axios.get(`/api/leaseholder/dashboard/get/list/images/${applicationId}`, {
      headers: {
        'Authorization': `Bearer ${authToken}` // Include the retrieved authentication token in the headers
      }
    });
    setImages(imagesResponse.data);

    const foodPreferencesResponse = await axios.get(`/api/leaseholder/dashboard/get/list/food/${applicationId}`, {
      headers: {
        'Authorization': `Bearer ${authToken}` // Include the retrieved authentication token in the headers
      }
    });
    setFoodPreferences(foodPreferencesResponse.data);

    const genderPreferencesResponse = await axios.get(`/api/leaseholder/dashboard/get/list/gender/${applicationId}`, {
      headers: {
        'Authorization': `Bearer ${authToken}` // Include the retrieved authentication token in the headers
      }
    });
    setGenderPreferences(genderPreferencesResponse.data);

    const response = await axios.get(`/api/reviews/getListOfAllRatings/${applicationId}`, {
      headers: {
        'Authorization': `Bearer ${authToken}` // Include the retrieved authentication token in the headers
      }
    });
    setReviewResponse(response.data);
    console.log(response.data);

    const ratingResponse = await axios.get(`/api/reviews/getAverageRatings/${applicationId}`, {
      headers: {
        'Authorization': `Bearer ${authToken}` // Include the retrieved authentication token in the headers
      }
    }
    );
    // setAlreadyApplied(await isUserAlreadyApplied());
    setRatings(ratingResponse.data);
    console.log(ratingResponse.data);
  } catch (error) {
    console.log(error.response.data);
    toast.error(error.response.data.message, {
      position: toast.POSITION.TOP_RIGHT
    });
  }
};


    console.log(isLeaseHolder)
    fetchPostDetails();
  }, [applicationId]);

  if (!post) {
    return <div>Loading...</div>;
  }
  return (
    <div className="details-container">
      <div className="details-title">
        <h2>{post.title}</h2>
        <h4>{post.subtitle}</h4>
      </div>

      <h3>Images</h3>
      <div className="carousel-container">
        <Carousel>
          {images.map((image, index) => (
            <div key={index}>
              <img src={image} alt={`Image ${index + 1}`} />
            </div>
          ))}
        </Carousel>
      </div>

      <div className="details-section">
        <h3>Details</h3>
        <div className="details-list-container">
          <ul>
            <li>
              <span className="details-label">Address:</span> {post.address}
            </li>
            <li>
              <span className="details-label">Location:</span> {post.locationCity}
            </li>
            <li>
              <span className="details-label">Size:</span> {post.size}
            </li>
            <li>
              <span className="details-label">Room Type:</span> {post.roomType}
            </li>
            <li>
              <span className="details-label">Rent:</span> {post.rent}
            </li>
            <li>
              <span className="details-label">Other Preferences:</span> {post.otherPreferences}
            </li>
            <li>
              <span className="details-label">Start Date:</span> {post.startDate}
            </li>
          </ul>
        </div>
      </div>

      <div className="preferences-section">
        <h3>Food Preferences</h3>
        <div className="preferences-container">
          {foodPreferences.map((preference, index) => (
            <span key={index} className="preference preference-food">{preference}</span>
          ))}
          
        </div>
      </div>

      <div className="preferences-section">
        <h3>Gender Preferences</h3>
        <div className="preferences-container">
         
          {genderPreferences.map((preference, index) => (
            <span key={index} className="preference preference-gender">{preference}</span>
          ))}
        </div>
      </div>
      {!isLeaseHolder ?
      (alreadyApplied ?
      (<button className="chat-button" onClick={()=>handleChatSubmit()}>Chat</button>):
        (<button className="apply-button" onClick={()=>handleApplySubmit()}>Apply</button>) 
      ):null
      }

      {/*/!* Review button *!/*/}
      <button onClick={() => handleReviewClick(post.application_id)}>Review the post</button>
        <div >

            <h3>Reviews and Ratings</h3>
            <div className="preferences-section">
                {ratings.map((rate, index) => (
                    <div className="preferences-section" key={index}>
                        <div className="preferences-section">
                            <h3>Average rating: {rate.averageRating}</h3>
                        </div>
                    </div>
                ))}
            </div>

            <div >

                        {reviewResponse.map((review, index) => (

                            <div className="preferences-section" key={index}>
                                    <p> </p>
                                    <p>@{review.name}</p>
                                    <p>Rating: {review.rating}</p>
                                    <p>Comment: {review.comment}</p>
                                </div>
                        ))}
            </div>
        </div>



    </div>

  );
};
