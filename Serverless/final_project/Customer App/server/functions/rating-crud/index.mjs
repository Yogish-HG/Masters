import { getItem, getAllItems, putItem, deleteItem } from "./ddb.helper.mjs";

export const handler = async (event, context) => {
  let body;
  let statusCode = 200;
  let dbResponse;
  const headers = {
    "Content-Type": "application/json",
  };

  try {
    switch (event.routeKey) {
      case "PUT /ratings":
        let req = JSON.parse(event.body);
        let item = {
          rating_id: req.rating_id,
          restaurant_id: req.restaurant_id,
          customer_id: req.customer_id,
          rating: req.rating,
        };
        dbResponse = await putItem(item);
        body = `Review with ${req.rating_id} added successfully`;
        break;

      // for future use in frontend
      case "DELETE /ratings/{id}":
        dbResponse = await deleteItem(event.pathParameters.id);
        body = `Rating with ${event.pathParameters.id} deleted successfully`;
        break;
      default:
        throw new Error(`Internal Server Error`);
    }
  } catch (err) {
    statusCode = 400;
    body = err.message;
  } finally {
    body = JSON.stringify(body);
  }

  return {
    statusCode,
    body,
    headers,
  };
};
