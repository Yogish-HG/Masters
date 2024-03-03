import React, { useState, useEffect } from 'react';
import './listrestaurant.css';
import Cookies from 'js-cookie';
import { useNavigate, Link } from 'react-router-dom';

function Listrestaurants() {
    const [restaurants, setRestaurants] = useState([]);
    const [token, setToken] = useState('');
    const navigate = useNavigate();
  
    const API_ENDPOINT = 'https://mlpjq9hwih.execute-api.us-east-1.amazonaws.com/dev';
  
    const handleLogout = () => {
      Cookies.remove('userId');
      navigate('/login');
    };
  
    const ListrestaurantAPI = {
      getAllRestaurants: async () => {
        try {
          const response = await fetch(`${API_ENDPOINT}/items`, {
            method: 'GET',
            headers: {
              'origin': 'http://localhost:3000',
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`,
            },
          });
  
          if (response.ok) {
            const data = await response.json();
            console.log('Data:', data);
            return data;
          } else {
            throw new Error('Failed to fetch all restaurants data');
          }
        } catch (error) {
          throw new Error(`Error fetching all restaurants data: ${error.message}`);
        }
      },
    };
  
    useEffect(() => {
      const fetchRestaurants = async () => {
        try {
          const data = await ListrestaurantAPI.getAllRestaurants();
          setRestaurants(data || []);
        } catch (error) {
          console.error('Error fetching restaurants:', error);
        }
      };
  
      fetchRestaurants();
    }, []);
  
    return (
      <div>
        <div>
          <h2 style={{ backgroundColor:"#fff", color: "#000", padding:'10px'}}>List of Available Restaurants</h2>
            {restaurants.map((restaurant) => (
              <p key={restaurant.restaurant_id}>
                <AccordionItem
                  restaurantId={restaurant.restaurant_id} 
                  title={restaurant.restaurant_name}
                  openHours={restaurant.open_hours}
                  closeHours={restaurant.close_hours}
                  address={restaurant.address_line}
                  phone={restaurant.phone}
                  status={restaurant.status}
                  menu={restaurant.menu}
                />
              </p>
            ))}
        </div>
        <div>
          <button onClick={handleLogout}>Logout</button>
        </div>
      </div>
    );
  }
  
  const AccordionItem = ({ restaurantId, title, openHours, closeHours, address, phone, status, menu }) => {
    const [isOpen, setIsOpen] = useState(false);
  
    const toggleAccordion = () => {
      setIsOpen(!isOpen);
    };
    const navigate = useNavigate();

  const onReserveClick = (restaurantData) => {
        // Implement the logic to handle the reservation
        console.log("Reserving:", restaurantData);
        navigate('/reservation', { state: { restaurantData } });

        // You can navigate to a reservation page or perform other actions
    };
    return (
      <div className="accordion-item">
        <div className="accordion-header" onClick={toggleAccordion}>
          <h3>{title}</h3>
          <span style={{ fontSize: "40px"}}>{isOpen ? "-" : "+"}</span>
        </div>
        {isOpen && (
          <div className="accordion-content">
            {restaurantId && <p>RestaurantId {restaurantId}</p>}
            {title && <p> Title{title}</p>}
            {openHours && <p>Open Hours: {openHours}</p>}
            {closeHours && <p>Close Hours: {closeHours}</p>}
            {address && <p>Address: {address}</p>}
            {phone && <p>Phone: {phone}</p>}
            {status ? <p>Status: {status === "open" ? "Open" : "Closed"}</p> : <p>Status: Closed</p>}
  
            {menu && (
              <div className="accordion-content">
                <h2>Menu</h2>
                {Object.keys(menu).map((cuisine) => (
                  <div key={cuisine}>
                    <h3>{cuisine}</h3>
                    <ul>
                      {Object.keys(menu[cuisine]).map((id) => (
                        <li key={id}>
                          <div>Menu Name: {menu[cuisine][id].menu_name}</div>
                          <div>Price: {menu[cuisine][id].price}</div>
                          <div>Quantity: {menu[cuisine][id].quantity}</div>
                        </li>
                      ))}
                    </ul>
                  </div>
                ))}
              </div>
            )}
            
            <button onClick={() => onReserveClick({ restaurantId, title, openHours, closeHours, address, phone, status, menu })}>
            Reserve </button>
          </div>
        )}
      </div>
    );
  };
  
  export default Listrestaurants;
  