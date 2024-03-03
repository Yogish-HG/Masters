import {
  getRestaurantByName,
  getRestaurantByAddress,
  getRestaurantByNameAndAddress,
} from "./ddb.helper.mjs";
export const handler = async (event, context) => {
  let body;
  let statusCode = 200;
  let dbResponse;
  const headers = {
    "Content-Type": "application/json",
  };

  console.log("event", event);
  try {
    switch (event.routeKey) {
      case "POST /restaurant/details":
        const eventBody = JSON.parse(event.body);
        if (eventBody?.restaurant_name && eventBody?.address_line) {
          dbResponse = await getRestaurantByNameAndAddress(
            eventBody?.restaurant_name,
            eventBody?.address_line
          );
        } else if (eventBody?.restaurant_name) {
          dbResponse = await getRestaurantByName(eventBody?.restaurant_name);
        } else if (eventBody?.address_line) {
          dbResponse = await getRestaurantByAddress(eventBody?.address_line);
        } else {
          throw new Error(`Request had bad syntax`);
        }

        body = { details: dbResponse?.Items || [] };
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
