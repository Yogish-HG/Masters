import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './booking.css';

const BookingForm = ({ userId }) => {
  const [restaurantDetails, setRestaurantDetails] = useState(null);
  const { restaurantId } = useParams();
  const [bookingDetails, setBookingDetails] = useState({
    customer_id: userId,
    restaurantId: restaurantId,
    start_time: '',
    reservationDate: '',
    total_no_people: 1,
    order: []
  });

  // Fetch restaurant details
  useEffect(() => {
    const fetchRestaurantDetails = async () => {
      try {
        const response = await axios.post('https://35mb0dp42l.execute-api.us-east-1.amazonaws.com/prod/getallrestaurant', {
          restaurant_id: restaurantId
        });
        const details = response.data && response.data.body ? JSON.parse(response.data.body) : null;
        setRestaurantDetails(details);
      } catch (error) {
        console.error("Error fetching restaurant details:", error);
      }
    };

    fetchRestaurantDetails();
  }, [restaurantId]);

  // Update booking details
  const handleChange = (e) => {
    setBookingDetails({ ...bookingDetails, [e.target.name]: e.target.value });
  };

  // Handle menu selection
  const handleMenuChange = (menuName, price, e) => {
    const order = [...bookingDetails.order];
    const menuIndex = order.findIndex(item => item.menu_name === menuName);
    if (menuIndex > -1) {
      order[menuIndex].quantity = e.target.value;
    } else {
      order.push({ menu_name: menuName, price, quantity: e.target.value });
    }
    setBookingDetails({ ...bookingDetails, order });
  };

  // Generate time options
  const generateTimeOptions = () => {
    if (!restaurantDetails || !restaurantDetails.open_hours || !restaurantDetails.close_hours) {
      return [];
    }

    const openHour = parseInt(restaurantDetails.open_hours.split(':')[0], 10);
    const closeHour = parseInt(restaurantDetails.close_hours.split(':')[0], 10);
    const times = [];
    for (let hour = openHour; hour <= closeHour; hour++) {
      times.push(`${hour.toString().padStart(2, '0')}:00`);
    }
    return times.map(time => <option key={time} value={time}>{time}</option>);
  };

  // Render menu options
  const renderMenuOptions = () => {
    if (!restaurantDetails || !restaurantDetails.menu) {
      return <div>Loading menu...</div>;
    }

    return Object.entries(restaurantDetails.menu).map(([category, items]) => {
      if (!Array.isArray(items)) {
        console.error(`Expected an array for ${category}, but received:`, items);
        return null;
      }

      return (
        <div key={category}>
          <h4>{category}</h4>
          {items.map(item => (
            <div key={item.menu_name}>
              <label>{item.menu_name} - ${item.price}</label>
              <input type="number" min="0" onChange={(e) => handleMenuChange(item.menu_name, item.price, e)} />
            </div>
          ))}
        </div>
      );
    });
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Format start_time and calculate end_time
    const startTime = new Date(`${bookingDetails.reservationDate} ${bookingDetails.start_time}`);
    const endTime = new Date(startTime.getTime() + 60 * 60 * 1000); // Add 1 hour to start_time
    const formattedStartTime = startTime.toISOString();
    const formattedEndTime = endTime.toISOString();

    // Create the payload
    const payload = {
      customer_id: bookingDetails.customer_id,
      restaurantId: bookingDetails.restaurantId,
      start_time: formattedStartTime,
      end_time: formattedEndTime,
      reservationDate: bookingDetails.reservationDate,
      status: "APPROVED",
      total_no_people: bookingDetails.total_no_people,
      order: bookingDetails.order.map(item => ({
        menu_name: item.menu_name,
        price: item.price.toString(),
        quantity: item.quantity.toString()
      }))
    };

    try {
      const response = await axios.post('https://1tjt8xvt44.execute-api.us-east-1.amazonaws.com/test/createreservations', payload);
      if (response.data.statusCode === 200) {
        alert('Booking created successfully');
      } else {
        console.error('Booking creation failed with status:', response.data.statusCode);
        alert(response.data.body);
      }
    } catch (error) {
      console.error('Error submitting booking:', error);
      if (error.response) {
        console.error('Server responded with:', error.response.status, error.response.data);
      }
    }
  };

  if (!restaurantDetails) return <div>Loading...</div>;

  return (
    <div className="booking-form">
      <h2>Create New Booking</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Date </label>
          <input type="date" name="reservationDate" onChange={handleChange} />
        </div>
        <div>
          <label>Time </label>
          <select name="start_time" onChange={handleChange}>
            {generateTimeOptions()}
          </select>
        </div>
        <div>
          <label>Number of People </label>
          <input type="number" name="total_no_people" onChange={handleChange} />
        </div>
        <div>
          <label>Menu </label>
          {renderMenuOptions()}
        </div>
        <button type="submit">BOOK</button>
      </form>
    </div>
  );
};

export default BookingForm;
