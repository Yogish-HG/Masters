import React, { useEffect, useState } from 'react';
import './addrestaurant.css';
import Cookies from 'js-cookie';
import LoginPage from './login';
import RegisterPage from './register';
import { useNavigate } from 'react-router-dom';

const AddRestaurantPage = () => {
  // State variables
  const navigate = useNavigate();

  const [status, setAvailability] = useState('open');
  const [open_hours, setOpeningHours] = useState('');
  const [close_hours, setClosingHours] = useState('');
  const [menu, setSelectedCuisine] = useState('chinese');
  const [table_count, setTableCount] = useState(0);
  const [table_size, setTableSize] = useState(0);
  const [menu_items, setMenuItems] = useState([]);
  const [newItem, setNewItem] = useState({ name: '', price: '', quantity: '' });
  const [restaurant_id, setRestaurantId] = useState('');
  const [restaurant_name, setRestaurantName] = useState('');
  const [phone, setPhone] = useState('');
  const [address_line, setAddressLine] = useState('');
  const [userId, setUserId] = useState('');

  // Handler functions
  useEffect(() => {
    // Set userId from cookies when the component mounts
    setUserId(Cookies.get('userId'));
  }, []); 

  const handleAvailabilityChange = (e) => {
    setAvailability(e.target.value);
  }
  const handleOpeningHoursChange = (e) => {
    setOpeningHours(e.target.value);
  };

  const handleClosingHoursChange = (e) => {
    setClosingHours(e.target.value);
  };

  const handleCuisineChange = (e) => {
    setSelectedCuisine(e.target.value);
  };

  const handleTableCountChange = (e) => {
    setTableCount(Number(e.target.value));
  };

  const handleTableSizeChange = (e) => {
    setTableSize(Number(e.target.value));
  };

  const handleAddItem = () => {
    setMenuItems([...menu_items, newItem]);
    setNewItem({ name: '', price: '', quantity: '' });
  };

  const handleNewItemChange = (e) => {
    setNewItem({
      ...newItem,
      [e.target.name]: e.target.value,
    });
  };

  const generateRestaurantId = () => {
    // Generate restaurant ID based on current timestamp
    const timestamp = new Date().getTime();
    setRestaurantId(`R${timestamp}`);
  };

  const handleRestaurantNameChange = (e) => {
    setRestaurantName(e.target.value);
  };

  const handlePhoneChange = (e) => {
    setPhone(e.target.value);
  };

  const handleAddressLineChange = (e) => {
    setAddressLine(e.target.value);
  };
  
  const handleLogout = () => {
    // Remove the token from cookies or state (depending on your implementation)
    Cookies.remove('userId');
    // Redirect to the login page or any other page after logout
    navigate('/login');
  };

  const handleSubmit = async () => {
    try {
      const response = await fetch('https://cors-anywhere.herokuapp.com/https://ygkj588pcl.execute-api.us-east-1.amazonaws.com/dev/addrestaurant', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': 'http://localhost:3000'
        },
        body: JSON.stringify({
          restaurant_id,
          restaurant_name,
          phone,
          address_line,
          status,
          open_hours,
          close_hours,
          menu: {
            [menu]: menu_items.map(item => ({
              price: item.price,
              menu_name: item.name,
              quantity: item.quantity
            }))
          },
          table_count,
          table_size,
        }),
      });

      if (response.ok) {
        // Handle success, e.g., show a success message or redirect to another page
        console.log('Restaurant details added successfully');
        navigate("/reservation")
      } else {
        // Handle errors, e.g., show an error message
        console.error('Failed to add restaurant details');
        navigate("/reservation")
      }
    } catch (error) {
      console.error('Error submitting restaurant details:', error);
      navigate("/reservation")
    }
  };

  return (
    <div>
        <h2> Add Restaurant Details </h2>
      <div>
        <label>
          Restaurant ID:
          <input type="text" value={restaurant_id} readOnly />
          <button onClick={generateRestaurantId}>Generate ID</button>
        </label>
      </div>
      
      <div>
        <label>
          Availability:
          <input
            type="radio"
            value="open"
            checked={status === 'open'}
            onChange={handleAvailabilityChange}
          />
          Open
          <input
            type="radio"
            value="closed"
            checked={status === 'closed'}
            onChange={handleAvailabilityChange}
          />
          Closed
        </label>
      </div>
      <div>
        <label>
          Restaurant Name:
          <input type="text" value={restaurant_name} onChange={handleRestaurantNameChange} />
        </label>
      </div>

      <div>
        <label>
          Phone Number:
          <input type="number" value={phone} onChange={handlePhoneChange} />
        </label>
      </div>

      <div>
        <label>
          Address Line:
          <input type="text" value={address_line} onChange={handleAddressLineChange} />
        </label>
      </div>
      <div>
        <label>
          Opening Hours:
          <input type="time" value={open_hours} onChange={handleOpeningHoursChange} />
        </label>
      </div>

      <div>
        <label>
          Closing Hours:
          <input type="time" value={close_hours} onChange={handleClosingHoursChange} />
        </label>
      </div>

      <div>
        <label>
          Number of Tables:
          <input type="number" value={table_count} onChange={handleTableCountChange} />
        </label>
      </div>

      <div>
        <label>
          Table Size:
          <input type="number" value={table_size} onChange={handleTableSizeChange} />
        </label>
      </div>

      <div>
        <label>
          Choose Cuisine:
          <select value={menu} onChange={handleCuisineChange}>
            <option value="chinese">Chinese</option>
            <option value="italian">Italian</option>
            <option value="punjabi">Punjabi</option>
            <option value="gujarati">Gujarati</option>
          </select>
        </label>
      </div>

      <div>
        <h3>Add Item to Menu</h3>
        <label>
          Name:
          <input type="text" name="name" value={newItem.name} onChange={handleNewItemChange} />
        </label>
        <label>
          Price:
          <input type="text" name="price" value={newItem.price} onChange={handleNewItemChange} />
        </label>
        <label>
          Quantity:
          <input type="text" name="quantity" value={newItem.quantity} onChange={handleNewItemChange} />
        </label>
        <button onClick={handleAddItem}>Add Item</button>
      </div>

      <div>
        <h3>Selected Menu Items</h3>
        <ul>
          {menu_items.map((item, index) => (
            <li key={index}>
              {item.name} - ${item.price} - Quantity: {item.quantity}
            </li>
          ))}
        </ul>
      </div>
      <button onClick={handleSubmit}>Submit</button>

      <button onClick={handleLogout}>Logout</button>

    </div>
    
  );
};

export default AddRestaurantPage;
