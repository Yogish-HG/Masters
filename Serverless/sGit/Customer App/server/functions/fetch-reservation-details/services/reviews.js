import axios from "axios";

export const addReview = async (data, onSuccess, onError) => {
  try {
    const response = await axios.put(
      "https://y9xx9soj89.execute-api.us-east-1.amazonaws.com/reviews",
      data
    );
    return onSuccess && onSuccess(response);
  } catch (err) {
    return onError && onError(err);
  }
};

export const addRating = async (data, onSuccess, onError) => {
    try {
      const response = await axios.put(
        "https://o6u2ibjdxg.execute-api.us-east-1.amazonaws.com/ratings",
        data
      );
      return onSuccess && onSuccess(response);
    } catch (err) {
      return onError && onError(err);
    }
  };