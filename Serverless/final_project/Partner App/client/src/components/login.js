import React, { useState } from 'react';
import './login.css'; // Import the CSS file
import { auth } from "../firebase"
import {Card} from "react-bootstrap";
import { signInWithEmailAndPassword, GoogleAuthProvider, signInWithPopup } from 'firebase/auth';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';


const LoginPage = () => {
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  
  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    try {
      const userCred = await signInWithEmailAndPassword(auth, userId, password);
      Cookies.set('userId', userCred._tokenResponse.email);
      navigate("/addrestaurant");
    } catch (error) {
      console.log(error);
      alert('Invalid credentials. Please try again.');
      
      console.error(error);
    }
  };

  let handleLoginWithGoogle = async (e) => {
    const provider = new GoogleAuthProvider();
    await signInWithPopup(auth, provider).then((userCred) => {
      Cookies.set('userId', userCred.user.email);
      navigate("/addrestaurant");

    }).catch((err) => {
      alert(err);
    })
  };

  return (
    <Card className='cardStyle' style={{ width: '18rem' }}>
    <Card.Body>
    <Card.Title>Welcome, Esteemed Restaurant Owner!</Card.Title>
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
            <button style= {{backgroundColor: "#C08261"}}type="submit">Sign In</button>
            <br/>
            <button onClick = {handleLoginWithGoogle} style= {{backgroundColor: "#C08261"}}type="submit"> Sign In with Google</button> 
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
