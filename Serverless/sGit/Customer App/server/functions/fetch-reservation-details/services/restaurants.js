import axios from "axios";

export const getRestaurantDetails = async (data) => {
  try {
    const response = axios.post(
      "https://2gzybahlmi.execute-api.us-east-1.amazonaws.com/restaurant/details",
      data
    );

    console.log(response);
  } catch (err) {}
};
