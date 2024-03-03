import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './components/login.js';
import RegisterPage from './components/register.js';
import ReservationPage from './components/reservation.js';
import AddRestaurantPage from './components/addrestaurant.js';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/" element={<LoginPage />} />
        <Route path="/reservation" element={<ReservationPage/>}/>
        <Route path="/addrestaurant" element={<AddRestaurantPage/>}/> 
        <Route path="/login" element={<LoginPage />} />


      </Routes>
    </Router>
  );
}

export default App;
