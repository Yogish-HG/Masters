import React, {useState} from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './components/login.js';
import RegisterPage from './components/register.js';
import Dashboard from './components/Dashboard.js';
import PrevOrd from './components/prevOrders.js';
import NavBar from './components/navbar.js';
import Cookies from 'js-cookie';
function App() {
  const initialLoggedInState = Cookies.get('isLoggedIn') === 'true';
  const [isLoggedIn, setIsLoggedIn] = useState(initialLoggedInState);

  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
    Cookies.set('isLoggedIn', 'true');
  };
  console.log(isLoggedIn);

  const handleLogOut = () => {
    setIsLoggedIn(false);
    Cookies.remove('isLoggedIn', 'false');
  };
  return (
    <Router>
    {isLoggedIn && <NavBar onLogOut={handleLogOut}/>}
      <Routes>
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/prev" element={<PrevOrd />} />
        <Route path="/" element={<LoginPage onLoginSuccess={handleLoginSuccess} />} />
      </Routes>
    </Router>
  );
}

export default App;