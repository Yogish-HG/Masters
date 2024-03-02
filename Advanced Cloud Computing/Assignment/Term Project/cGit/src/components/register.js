import React, { useState } from 'react';
import './register.css';
import { Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';



const RegisterPage = () => {
  

  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');
  const [conpassword, setConPassword] = useState('');
  const [name, setName] = useState('');
  const [address, setaddress] = useState('');
  const navigate = useNavigate();

  const handleregisterSubmit = (e) => {
    e.preventDefault();

    try {
      if (password === conpassword && password !== '' && conpassword !== '' && name !== "") {

        let data = {
          "customer_email": userId,
          "name": name,
          "address": address,
          "password": password
        }
        // Register user
      //  axios.post("https://vt1byeh9we.execute-api.us-east-1.amazonaws.com/signup/signup", data)
      // console.log(process.env.REACT_APP_API_URL+"/dev/up");
      console.log(data);
       axios.post(process.env.REACT_APP_API_URL+"/dev/up", data)
      //  axios.post("https://b5nnj9oqef.execute-api.us-east-1.amazonaws.com/dev/up", data)
        .then((userCred2) => {
            console.log(userCred2.data);
            alert(userCred2.data);
            navigate("/")
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

  return (
    <Card className='cardStyle' style={{ width: '18rem' }}>
      
      <Card.Body>
      <Card.Title className="card-title">Register with us</Card.Title>
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
          <label style={{ color: '#f2a183', fontWeight: 'bold' }} htmlFor='add_ress'>
            Address:
          </label>
          <input
            type='text'
            id='add_ress'
            autoComplete='off'
            onChange={(e) => setaddress(e.target.value)}
            value={address}
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
          <label style={{ color: '#f2a183', fontWeight: 'bold' }} htmlFor='Conpassword'>
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
