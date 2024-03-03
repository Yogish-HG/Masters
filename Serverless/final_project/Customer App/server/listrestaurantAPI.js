const API_ENDPOINT  = 'https://cors-anywhere.herokuapp.com/https://mlpjq9hwih.execute-api.us-east-1.amazonaws.com'

const ListrestaurantAPI = {

  getAllRestaurants: async () => {
    try {
      const response = await fetch(`${API_ENDPOINT}/items`, {
        method: 'GET',
        mode:'cors',
        headers: {
          'Content-Type': 'application/json', // Example header (modify as needed)
          // Add other headers as needed by the API
        },
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Data:', data); // Log the data received
        return data;
      } else {
        throw new Error('Failed to fetch all restaurants data');
      }
    } catch (error) {
      throw new Error(`Error fetching all restaurants data: ${error.message}`);
    }
  },

};

export default ListrestaurantAPI;

