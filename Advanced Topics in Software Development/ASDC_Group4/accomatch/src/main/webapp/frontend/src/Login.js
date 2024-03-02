import './Login.css';
import {useRef, useState , useEffect,useContext} from 'react';
import { Link } from 'react-router-dom'; // Import the Link component from react-router-dom
import axios from 'axios';
import {useNavigate}  from 'react-router-dom';
import Cookies from "js-cookie";
import { AuthContext } from './App';
export const Login =() =>{

    const userRef =useRef();
    const errRef =useRef();
    const { handleLoginSuccess } = useContext(AuthContext);
    const navigate =useNavigate();
    const [user, setUser]= useState('');
    const [pwd, setPwd] = useState('');
    const [errMsg, setErrMsg] =useState ('');
    const [success, setSuccess] = useState(false);

    useEffect(() =>{
        userRef.current.focus();
    },[])

    useEffect(() => {
        setErrMsg('');
    }, [user,pwd])
  

    const handleLoginSubmit =async (e) => {
        e.preventDefault();
        console.log(user,pwd);
        let bodyObj = {
            email:user,
            password:pwd
        }
        setUser('');
        setPwd('');

        fetch("/api/users/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(bodyObj),
        })
        .then((response) => {
            console.log(response);
           
            return response.json(); // Read the response data as json
        })
        .then((data) => {
            console.log(data); // Log the response data
        
            const userDetailsString = data.userDetails;
            const userDetails = JSON.parse(userDetailsString);
        
            console.log(userDetails); // Output the parsed userDetails object
        
            // Access additional data from the userDetails object
            const email = userDetails.additionalData.Email;
            const userId = userDetails.additionalData.User_id;
            const name = userDetails.additionalData.Name;
            const type= userDetails.additionalData.Type;
            const status=userDetails.additionalData.Status;;
            const token = data.token;
            console.log(data.User_id);
            sessionStorage.setItem('user_id', userId);
            sessionStorage.setItem("name", name);
            sessionStorage.setItem("email", email);
            sessionStorage.setItem("type", type);
            sessionStorage.setItem("token", token);
            console.log(sessionStorage.getItem('user_id'));


            if (status === "Success") {
            setSuccess(true);
            } else {
            setErrMsg("Login failed. Please try again."); // Set an appropriate error message
            }
            if(status==="Success"){
                handleLoginSuccess();
                navigate("/posts");
            }
        })
        .catch((error) => {
            setErrMsg("An error occurred. Please try again."); // Set an appropriate error message
        });
        }

    return (
        <>
        {success ? (
            <section>
                <h1>
                    You are logged in!
                </h1>
            <br/>
            <p>
                <a href="#">Go to Home</a>
            </p>

            </section>

        ):(

        
        <section>
            <p ref={errRef} className ={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">
                {errMsg}
            </p>
            <h1>Sign In</h1>
            <form onSubmit={handleLoginSubmit}>
                <label htmlFor ="username">Username:</label>
                <input 
                    type="text"
                    id="username"
                    ref={userRef}
                    autoComplete="off"
                    onChange={(e) => setUser (e.target.value)}
                    value={user}
                    required
                />
                 <label htmlFor ="password">Password:</label>
                <input 
                    type="password"
                    id="password"
                    onChange={(e) => setPwd(e.target.value)}
                    value={pwd}
                    required
                />

                <button>Sign In</button>

            </form>
                <p>
                    Need an Account? <br/>
                    <span className="line">
                    {/* Put the reference for the  link */}
                    <a href="/signup">Sign Up</a>
                    </span>
                </p>
            <p>
                Forgot Password? <br/>
                <span className="line">
                    {/* Put the reference for the  link */}
                    <a href="/forgetpassword">Reset Password</a>
                    </span>
            </p>
        </section>
        )}
        </>
    )
}