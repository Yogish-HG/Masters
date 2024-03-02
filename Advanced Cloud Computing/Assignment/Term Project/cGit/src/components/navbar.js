import React, { useState } from "react";
import Cookies from 'js-cookie';
import { NavLink } from "react-router-dom";
import "./NavBar.css";
const NavBar = ({ onLogOut }) => {
  const handleLogoutClick = () =>{
    Cookies.remove('userId');
  };
  return (
    <>
      <nav className="navbar">
        <div >
          <NavLink exact to="/prev" className="nav-logo">
            History 
            <i className="fas fa-home"></i>
          </NavLink>

          <NavLink exact to="/dashboard" className="nav-logo">
            Dashboard 
            <i className="fas fa-home"></i>
          </NavLink>

          <NavLink exact to={"/"} className="nav-logo"
                  onClick={()=>{
                    handleLogoutClick();
                    onLogOut();
                  }}
              >
                Logout
              </NavLink>
        </div>
      </nav>
    </>
  );
}

export default NavBar;
