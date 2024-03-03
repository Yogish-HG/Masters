import React, { useState } from 'react';
import './register.css';
import { auth } from '../firebase';
import { Card } from 'react-bootstrap';
import quote from '../images/appl.jpeg';
import { createUserWithEmailAndPassword } from 'firebase/auth';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const RegisterPage = () => {

  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');
  const [conpassword, setConPassword] = useState('');
  const [name, setName] = useState('');
  const navigate = useNavigate();

  const handleregisterSubmit = (e) => {
    e.preventDefault();

    try {
      if (password === conpassword && password !== '' && conpassword !== '' && name !== "") {

        // Register user
       createUserWithEmailAndPassword(auth, userId, password)
        .then(async (userCred2) => {
            alert('Registration Successful');
            const response = await axios.get("https://jthj8sqgyg.execute-api.us-east-1.amazonaws.com/count/cust-res/get-count");
            const numCustResponse = response.data;
            if (numCustResponse) {
              const numCust = numCustResponse.body;
              console.log("Number " + (numCust + 1));

              let postData = {
                "CustomerId": (numCust+1),
                "Name": name,
                "Email": userId,
                "operation": "create"
              }
              console.log(postData);
              axios.post("https://jthj8sqgyg.execute-api.us-east-1.amazonaws.com/crud/cust-res", postData)
              .then((resp) => {
                console.log(resp);
                if(resp.data.body === "CustomerId created successfully."){
                  alert("success")
                  navigate("/");
                }
              })
              .catch(error => {
              console.error('Error:', error);
              });
            } else {
              console.log("Invalid response format");
              alert("Couldn't fetch the number of customers");
              return;
            }
            
        })
        .catch((error) => {
            alert(error)
        })
        
        // You might want to do additional actions after successful registration
      } else {
        alert('Passwords don\'t match or are empty. Please try again.');
      }
    } catch (error) {
      alert('Error during registration. Please try again.');
      console.error(error);
    }
  };

  // const handleRegisterLink = () => {
  //   navigate("/")
  // };

  return (
    <Card className='cardStyle' style={{ width: '18rem' }}>
      <Card.Img
        style={{ display: 'block', margin: 'auto', height: '200px', width: '200px' }}
        variant="top"
        src={quote}
      />
      <Card.Body>
        <form onSubmit={handleregisterSubmit}>
          <label style={{ color: '#f2a183', fontWeight: 'bold' }} htmlFor='name'>
            Name:
          </label>
          <input
            type='text'
            id='name'
            autoComplete='off'
            onChange={(e) => setName(e.target.value)}
            value={name}
            required
          />
          <label style={{ color: '#f2a183', fontWeight: 'bold' }} htmlFor='userId'>
            User ID:
          </label>
          <input
            type='text'
            id='userId'
            autoComplete='off'
            onChange={(e) => setUserId(e.target.value)}
            value={userId}
            required
          />
          <label style={{ color: '#f2a183', fontWeight: 'bold' }} htmlFor='password'>
            Password:
          </label>
          <input
            type='password'
            id='password'
            onChange={(e) => setPassword(e.target.value)}
            value={password}
            required
          />
          <label style={{ color: '#f2a183', fontWeight: 'bold' }} htmlFor='password'>
            Confirm Password:
          </label>
          <input
            type='password'
            id='Conpassword'
            onChange={(e) => setConPassword(e.target.value)}
            value={conpassword}
            required
          />
          <button style={{ backgroundColor: '#C08261' }} type='submit'>
            Sign up
          </button>
          <br />
          <a href='/' style={{ display: 'block', margin: 'auto', textAlign: 'center' }}>
  Already have an account?
</a>
        </form>
      </Card.Body>
    </Card>
  );
};

export default RegisterPage;
