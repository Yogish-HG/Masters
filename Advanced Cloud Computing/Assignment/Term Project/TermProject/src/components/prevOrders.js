import React, { useEffect, useState } from 'react';
import { Navbar, Container, Nav, Card, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './prevOrders.css';
import axios from 'axios';
import groc from '../images/card.jpg';
import bg from '../images/background.jpg';
import Cookies from 'js-cookie';

export default function Prevord() {
    const [ordersPrev, setItems] = useState([]);
    const [userId, setUserId] = useState('');

    useEffect(() => {
    const userIdFromCookies = Cookies.get('userId');
    
    // Log the userId obtained from Cookies
    console.log(userIdFromCookies);

    // Set the userId in the component state
    setUserId(userIdFromCookies);
        setUserId(Cookies.get('userId'));
        let payload = {
            "email": userIdFromCookies
        };
        console.log(payload);

        // axios.post("https://b5nnj9oqef.execute-api.us-east-1.amazonaws.com/dev/Prev", payload)
        axios.post(process.env.REACT_APP_API_URL+"/dev/Prev", payload)
            .then((resp) => {
                setItems(resp.data[0].orders);
                console.log(resp);
            })
            .catch((error) => {
                console.error("Error in async operation:", error);
            });

    }, []);

    return (
        <div>
            {ordersPrev && ordersPrev.length > 0 ? (
                ordersPrev.map((order, index) => (
                    <Card  key={index} style={{ display: 'block' }}>
                        {order.map((item, itemIndex) => (
                            
                            <div key={itemIndex}>
                                {item['total amount'] ? (
                                    <p>Total Amount: {item['total amount']} CAD</p>
                                ) : (
                                    item['name'] && item['quantity'] && (
                                        <p>
                                            Name: {item['name']}, Quantity: {item['quantity']}
                                        </p>
                                    )
                                )}
                            </div>
                            
                        ))}
                 
                    </Card>
                ))
            ) : (
                <p>No orders available.</p>
            )}
        </div>
    );
    
}
