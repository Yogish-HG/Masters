import React, { useState,createContext } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavBar from './Components/NavBar';
import { Home } from './Components/Pages/Home';
import { Posts } from './Components/Pages/Posts';
import { PostsByStatus } from './Components/Pages/PostsByStatus';
import { PostsDetailsPage } from './Components/Pages/PostsDetailsPage';
import { Applicants } from './Components/Pages/Applicants';
import { LeaseHolderPersonlPosts } from './Components/Pages/LeaseHolderPersonlPosts';
import { PersonalPostsDetailsPage } from './Components/Pages/PersonalPostsDetailsPage';
import { AllApplicantPosts } from './Components/Pages/AllApplicantPosts';
import { Login } from './Login';
import { Signup } from './Signup';
import { HouseSeekerCreateApplication } from './Components/Pages/HouseSeekerCreateApplication';
import { LeaseHolderCreateApplication } from './Components/Pages/LeaseHolderCreateApplication';
import { ForgetPasswordEmail } from './ForgetPasswordEmail';
import { ForgetPassword } from './ForgetPassword';
import { ChatModel } from './Components/Pages/ChatModel';
import { LeaseApplicantView } from './Components/Pages/LeaseApplicantView';
import { ApplicantPosts } from './Components/Pages/ApplicantPosts';
import { Rating } from './Components/Pages/Rating';
import { ToastContainer } from 'react-toastify';
import { ErrorBoundary } from 'react-error-boundary';
import ErrorPage from './Components/Pages/ErrorPage';
import { UserProfile } from './Components/Pages/UserProfile';
import { ApplicantAppliedPosts } from './Components/Pages/ApplicantAppliedPosts';

import 'react-toastify/dist/ReactToastify.css';
export const AuthContext = createContext();
function App() {
  const initialLoggedInState = localStorage.getItem('isLoggedIn') === 'true';
  const [isLoggedIn, setIsLoggedIn] = useState(initialLoggedInState);

  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
    localStorage.setItem('isLoggedIn', 'true');
  };
  const handleLogOut=()=>{
    setIsLoggedIn(false);
    // Clear any user-related data from session storage or cookies as needed
    sessionStorage.removeItem('user_id');
    sessionStorage.removeItem('name');
    sessionStorage.removeItem('email');
    sessionStorage.removeItem('type');
    sessionStorage.removeItem('token');
    localStorage.removeItem('isLoggedIn');
  }

  return (
    <Router>
      <AuthContext.Provider value={{ isLoggedIn, handleLoginSuccess }}>
      {isLoggedIn && <NavBar onLogOut={handleLogOut}/>} {/* Render NavBar only if isLoggedIn is true */}
      <div className="pages">
        <ToastContainer />
        <ErrorBoundary FallbackComponent={ErrorPage}>
          <Routes>
            {/* Conditionally render based on isLoggedIn */}
            <Route path="/" element={<Login onLoginSuccess={handleLoginSuccess} />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/home" element={<Home />} />
            <Route path="/posts" element={<Posts />} />
            <Route path="/posts/:applicationId" element={<PostsDetailsPage />} />
            <Route path="/postsbystatus" element={<PostsByStatus />} />
            <Route path="/applicants" element={<Applicants />} />
            <Route path="/personalposts/:user_Id" element={<LeaseHolderPersonlPosts />} />
            <Route path="/personalpostdetails/:applicationId" element={<PersonalPostsDetailsPage/>} />
            <Route path="/allapplicant" element={<AllApplicantPosts />} />
            <Route path="/create" element={<LeaseHolderCreateApplication />} />
            <Route path="/createApplicant" element={<HouseSeekerCreateApplication />} />
            <Route path="/forgetpassword" element={<ForgetPasswordEmail />} />
            <Route path="/updatepassword" element={<ForgetPassword />} />
            <Route path="/leaseapplicantview/:applicationId" element={<LeaseApplicantView />} />
            <Route path='/chat/:roomId' element={<ChatModel/>}/>
            <Route path="/applicantposts/:user_Id" element={<ApplicantPosts />} />
            <Route path="/ratingform/:applicationId" element={<Rating />} />
            <Route path="/userprofile/:user_Id" element={<UserProfile/>}/>
            <Route path="/posts/appliedapplications/:user_Id" element={<ApplicantAppliedPosts/>}/>
          </Routes>
        </ErrorBoundary>
      </div>
      </AuthContext.Provider>
    </Router>
  );
}

export default App;
