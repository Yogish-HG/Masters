import React, { useEffect, useState } from "react";
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

export const AllApplicantPosts = () => {
    const dummyImage = "https://www.pngitem.com/pimgs/m/581-5813504_avatar-dummy-png-transparent-png.png";
    const [posts, setPosts] = useState([]);
    const [selectedPost, setSelectedPost] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isFilterOpen, setIsFilterOpen] = useState(false);
    const [filter, setFilter] = useState({
        gender: {
            male: false,
            female: false,
        },
        food: {
            vegetarian: false,
            nonVegetarian: false,
        },
        age: ''
    });
    const navigate = useNavigate();

    useEffect(() => {
        loadPosts();
    }, []);

    const loadPosts = async () => {
        try {
          const authToken = sessionStorage.getItem("token"); //  authentication token
          
          const response = await axios.get("/api/houseSeeker/getListOfAllApplicantPosts", {
            headers: {
              Authorization: `Bearer ${authToken}` // authentication token in the headers
            }
          });
          console.log(response.data);
          setPosts(response.data);
        } catch (error) {
          console.error(error);
        }
      };
      

    const handleDetailsClick = (postId) => {
        navigate(`/posts/${postId}`);
    };

    const openModal = (post) => {
        setSelectedPost(post);
        console.log(post)
        setIsModalOpen(true);
    }

    const closeModal = () => {
        setIsModalOpen(false);
    }

    const handleCheckboxChange = (event) => {
        const { name, checked } = event.target;
        const filterType = name.split('-')[0];
        const option = name.split('-')[1];

        setFilter((prevFilter) => ({
            ...prevFilter,
            [filterType]: {
                ...prevFilter[filterType],
                [option]: checked ? 1 : 0,
            },
        }));
    };

    const handleAgeChange = (event) => {
        const { value } = event.target;
        setFilter(prevFilter => ({
            ...prevFilter,
            age: value
        }));
    };

    const handleFilterSubmit = async (event) => {
        event.preventDefault();

        const genderValue = filter.gender.male ? 1 : 0;
        const foodValue = filter.food.vegetarian ? 1 : 0;
        const ageValue = filter.age;

        try {
            const response = await axios.post("/api/applicant/posts/filter", {
                Male: genderValue,
                Female: filter.gender.female ? 1 : 0,
                Veg: foodValue,
                NonVeg: filter.food.nonVegetarian ? 1 : 0,
                age: ageValue
            });
            setPosts(response.data);
        } catch (error) {
            console.error(error);
        }
    };
    const toggleFilter = () => {
        setIsFilterOpen(prevValue => !prevValue);
    };

    const toggleFilterOptions = () => {
        setIsFilterOpen(true);
    };

    return (
        <div className="dashboard-container">
            <button onClick={toggleFilterOptions}>Filter</button>
            <div className={`filter-container ${isFilterOpen ? 'open' : ''}`}>
                <div className="filter-header" onClick={toggleFilter}>
                    <h3>Filter:</h3>
                    <div className="filter-toggle">
                        <span className="toggle-icon">{isFilterOpen ? '▲' : '▼'}</span>
                    </div>
                </div>
                <form onSubmit={handleFilterSubmit}>
                    <div className="filter-content">
                        <div className="filter-row">
                            <label>Gender:</label>
                            <div className="checkbox-group">
                                <div>
                                    <input
                                        type="checkbox"
                                        name="gender-male"
                                        checked={filter.gender.male}
                                        onChange={handleCheckboxChange}
                                    />
                                    <label>Male</label>
                                </div>
                                <div>
                                    <input
                                        type="checkbox"
                                        name="gender-female"
                                        checked={filter.gender.female}
                                        onChange={handleCheckboxChange}
                                    />
                                    <label>Female</label>
                                </div>
                            </div>
                        </div>
                        <div className="filter-row">
                            <label>Food:</label>
                            <div className="checkbox-group">
                                <div>
                                    <input
                                        type="checkbox"
                                        name="food-vegetarian"
                                        checked={filter.food.vegetarian}
                                        onChange={handleCheckboxChange}
                                    />
                                    <label>Vegetarian</label>
                                </div>
                                <div>
                                    <input
                                        type="checkbox"
                                        name="food-nonVegetarian"
                                        checked={filter.food.nonVegetarian}
                                        onChange={handleCheckboxChange}
                                    />
                                    <label>Non Vegetarian</label>
                                </div>
                            </div>
                        </div>
                        <div className="filter-row">
                            <label>Age:</label>
                            <input
                                type="number"
                                name="age"
                                value={filter.age}
                                onChange={handleAgeChange}
                            />
                        </div>
                    </div>
                    <button type="submit">Apply Filter</button>
                </form>
            </div>
            <div className="post-list">
                {posts.map((post, index) => (
                    <div className="post" key={index} >
                        <div className="post-image">
                            <img src={dummyImage} alt={`Post ${post.title}`} />
                        </div>
                        <div className="post-details">
                            <h3 > {post.name}</h3>
                            <p>City: {post.location_city}</p>
                            <p>Other Preferences: {post.other_preferences}</p>
                            <p>Room Type: {post.room_type}</p>
                            <p>Start Date: {post.start_date}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};
