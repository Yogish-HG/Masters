import React, { useEffect, useState } from "react";
import axios from "axios";
import Cookies from 'js-cookie';
import './reservation.css'


// const Today = new Date();

const ReservationList = () => {

const [displayData, setDisplayData] = useState();
const [userId, setUserId] = useState('');
const [reserID, setresID] = useState('');
const getColorForReservation = (index) => {
    // Define a set of colors to rotate through
    const colors = ["#FFB6C1", "#ADD8E6", "#FFD700", "#98FB98", "#FFA07A"];
    return colors[index % colors.length];
};

const handleReject = (reser)=> {
    alert("Reservation with ID "+reser.reservation_id+"Rejected");
}
const handleApprove = (reser)=> {
    alert("Reservation with ID "+reser.reservation_id+"Appoved");
}
useEffect(() => {
    setUserId(Cookies.get('userId'));
    const fetchData = async () => {
    let postdata = {
        "restaurant_id": "restaurant789"
    }  
    try {
        const response = await axios.post("https://35mb0dp42l.execute-api.us-east-1.amazonaws.com/prod/getrestaurantbooking", postdata);
        // let respDict = response.data.body;
        // console.log(respDict);

        setDisplayData([JSON.parse(response.data.body)]); // Assuming the data is in the "data" property of the response

    } catch (error) {
        if (error.response) {
            // The request was made and the server responded with a status code
            console.error("Server responded with a non-2xx status:", error.response.status);
        } else if (error.request) {
            // The request was made but no response was received
            console.error("No response received from the server");
        } else {
            // Something happened in setting up the request that triggered an Error
            console.error("Error setting up the request:", error.message);
        }
    }
};

fetchData();
}, []);

console.log("&&&&&&&&&")
console.log(displayData);
return (
    <div>
        <h4>Restaurant Reservations</h4>

        {/* <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}> */}

            {displayData &&
                displayData.map((reservation, index) => (
                    // <div 
                    //     // key={reservation[0].reservations[0].restaurantId}
                    //     className="reservation-item"
                    //     style={{
                    //         marginBottom: "16px",
                    //         backgroundColor: getColorForReservation(index),
                    //         backgroundImage: "none"
                    //     }}
                    // >
                    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                            
                        
                            {reservation.reservations.map((reser, index) => (
                                <div className="reservation-item"
                                style={{
                                    marginBottom: "16px",
                                    backgroundColor: getColorForReservation(index),
                                    backgroundImage: "none"}}key={index}>
                                    
                                    {new Date(reser.start_time).toLocaleString("en-US", {
                                        weekday: "long",
                                        year: "numeric",
                                        month: "short",
                                        day: "numeric",
                                        hour: "numeric",
                                        minute: "2-digit",
                                    })}{" "}
                                    -{" "}
                                    {new Date(reser.end_time).toLocaleString("en-US", {
                                        hour: "numeric",
                                        minute: "2-digit",
                                    })}

                    <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '8px' }}>
                    <button onClick = {()=>handleApprove(reser)} style={{ marginRight: '8px', backgroundColor: 'green', fontSize: '12px' }}>Approve</button>
                    <button onClick = {()=>handleReject(reser)} style={{ marginRight: '8px', backgroundColor: 'red', fontSize: '12px' }}>Reject</button>
                        </div>
                                    
                                </div>
                                ))}

                            
                        {/* Additional details and buttons */}
                    </div>
                ))}
        {/* </div> */}
    </div>
);
};

export default ReservationList;
