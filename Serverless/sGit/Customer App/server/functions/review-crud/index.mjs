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
      case "GET /reviews/{id}":
        dbResponse = await getItem(event.pathParameters.id);
        body = { review: dbResponse.Item || "", count: dbResponse.Count };
        break;
      case "GET /reviews":
        dbResponse = await getAllItems();
        body = { reviews: dbResponse.Item || [], count: dbResponse.Count };
        break;
      case "GET /reviews/rest/{id}":
        dbResponse = await getItemByRestaurant(event.pathParameters.id);
        body = { review: dbResponse?.Items || [], count: dbResponse?.Count };
        break;
      case "PUT /reviews":
        let req = JSON.parse(event.body);
        let item = {
          review_id: req.review_id,
          type: req.type,
          id: req.id,
          customer_id: req.customer_id,
          review: req.review,
        };
        dbResponse = await putItem(item);
        body = `Review with ${req.review_id} added successfully`;
        break;

      // for future use in frontend
      case "DELETE /reviews/{id}":
        dbResponse = await deleteItem(event.pathParameters.id);
        body = `Review with ${event.pathParameters.id} deleted successfully`;
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
