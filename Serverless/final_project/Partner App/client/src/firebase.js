// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyCEiCgXPYTRnB6UTOExdHaDL_nkCqaN5tE",
  authDomain: "partner-app-1cb30.firebaseapp.com",
  projectId: "partner-app-1cb30",
  storageBucket: "partner-app-1cb30.appspot.com",
  messagingSenderId: "284069895526",
  appId: "1:284069895526:web:c1cb2628ea4356325f7ca7"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

// Initialize Firebase
const auth = getAuth(app);
// const SSO = GoogleAuthProvider(app);
export { auth };