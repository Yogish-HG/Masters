import React, { useState } from 'react';
import './login.css'; // Import the CSS file
import {Card} from "react-bootstrap";
import quote from "../images/appl.jpeg"
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Cookies from 'js-cookie';


const LoginPage = ({ onLoginSuccess }) => {
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  

  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    Cookies.set('userId', userId);
    
    let postdata = {
      "customer_email": userId,
      "password": password
    }

    try {
      // const response = await axios.post("https://b5nnj9oqef.execute-api.us-east-1.amazonaws.com/dev/in", postdata);
      const response = await axios.post(process.env.REACT_APP_API_URL+"/dev/in", postdata);
      
      console.log(response);
  
      if (response.data === "Login successful!") {
        onLoginSuccess();
        alert(response.data);
        navigate("/dashboard");
      } else {
        alert("Please try again.");
        console.log(response.data);
      }
    } catch (error) {
      console.error("Error during login:", error);
      alert("An error occurred during login. Please try again.");
    }
    
  };


  return (
    <Card className='cardStyle' style={{ width: '30rem' }}>
    <Card.Body>
    <Card.Title>Hola from ShopVista</Card.Title>
      <form onSubmit={handleLoginSubmit}>
            <label style={{color:"#f2a183", fontWeight: 'bold'}}htmlFor="userId">User ID:</label>
            <input
              type="text"
              id="userId"
              autoComplete="off"
              onChange={(e) => setUserId(e.target.value)}
              value={userId}
              required
            />
            <label style={{color:"#f2a183", fontWeight: 'bold'}} htmlFor="password">Password:</label>
            <input
              type="password"
              id="password"
              onChange={(e) => setPassword(e.target.value)}
              value={password}
              required
            />
            <br />
            <button style= {{backgroundColor: "#C08261"}}type="submit">Sign In</button>
            <br/>
            <a href="/register" style={{ display: 'block', margin: 'auto', textAlign: 'center' }}>
  Not Registered?
</a>
          </form>
    </Card.Body>
  </Card>
  );
};

export default LoginPage;
