// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyAwy-JrXJIpjOaSvgHrAyaAgppQSKUS698",
  authDomain: "serverlessproject-402718.firebaseapp.com",
  projectId: "serverlessproject-402718",
  storageBucket: "serverlessproject-402718.appspot.com",
  messagingSenderId: "393579530114",
  appId: "1:393579530114:web:4d284ddd5c5b7ac1caf19b",
  measurementId: "G-XXW5H9FBRV"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
// const SSO = GoogleAuthProvider(app);
export { auth };