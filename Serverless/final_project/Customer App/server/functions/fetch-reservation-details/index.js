import axios from "axios";
import { v4 as uuidv4 } from "uuid";
import { addReview, addRating } from "./services/reviews.mjs";

/**
 * send response to lex
 * @param {*} sessionState
 * @param {*} fulfillmentState
 * @param {*} message
 * @param {*} requestAttributes
 * @returns
 */
function close(
  sessionState,
  fulfillmentState,
  savedAttributes,
  message,
  requestAttributes
) {
  sessionState.intent.state = fulfillmentState;
  sessionState.dialogAction = {
    slotElicitationStyle: "Default",
    slotToElicit: "string",
    type: "Close",
  };
  sessionState.sessionAttributes = savedAttributes;

  return {
    sessionState: sessionState,
    messages: [message],
    requestAttributes: requestAttributes,
  };
}

function delegate(sessionState, savedAttributes, requestAttributes) {
  sessionState.dialogAction = {
    type: "Delegate",
  };

  sessionState.sessionAttributes = savedAttributes;
  return {
    sessionState: sessionState,
    requestAttributes: requestAttributes,
  };
}

function confirm(sessionState, savedAttributes, message, requestAttributes) {
  sessionState.intent.state = "InProgress";
  sessionState.dialogAction = {
    type: "ConfirmIntent",
  };
  sessionState.sessionAttributes = savedAttributes;
  return {
    sessionState: sessionState,
    messages: [message],
    requestAttributes: requestAttributes,
  };
}

function elicitSlot(
  sessionAttributes,
  intentName,
  slots,
  slotToElicit,
  message
) {
  return {
    sessionAttributes,
    dialogAction: {
      type: "ElicitSlot",
      intentName,
      slots,
      slotToElicit,
      message,
    },
  };
}

function validateConfirmation(
  sessionState,
  closingMessage,
  confirmationMessage,
  outputSessionAttributes,
  requestAttributes
) {
  return sessionState.intent.confirmationState === "Confirmed"
    ? close(
        sessionState,
        "Fulfilled",
        outputSessionAttributes,
        closingMessage,
        requestAttributes
      )
    : sessionState.intent.confirmationState === "Denied"
    ? delegate(sessionState, {}, requestAttributes)
    : confirm(
        sessionState,
        outputSessionAttributes,
        confirmationMessage,
        requestAttributes
      );
}

export const handler = async (event, context) => {
  const slotName = Object.keys(event.sessionState.intent.slots);

  console.log("slotName", slotName);
  var area, menuType, menuItem, restaurantName;
  const outputSessionAttributes = event.sessionState.sessionAttributes || {};

  area = event.sessionState.intent.slots.Area?.value?.interpretedValue;
  menuType = event.sessionState.intent.slots.MenuType?.value?.interpretedValue;
  menuItem = event.sessionState.intent.slots.MenuItem?.value?.interpretedValue;
  restaurantName =
    event.sessionState.intent.slots.RestaurantName?.value?.interpretedValue;

  if (area !== null) {
    outputSessionAttributes["Area"] = area;
  }

  if (menuType !== null) {
    outputSessionAttributes["MenuType"] = menuType;
  }

  if (menuItem !== null) {
    outputSessionAttributes["MenuItem"] = menuItem;
  }

  if (restaurantName !== null) {
    outputSessionAttributes["RestaurantName"] = restaurantName;
  }

  if (event.sessionState.intent.name === "GetAvailableRestaurants") {
    if (
      outputSessionAttributes["Area"] &&
      outputSessionAttributes["MenuType"] &&
      outputSessionAttributes["MenuItem"]
    ) {
      console.log("attribute exists");
      // API call for fetching restaurants
      // store fetched restaurant name
      outputSessionAttributes["RestaurantName"] = "Delight Dine";
      return validateConfirmation(
        event.sessionState,
        {
          contentType: "PlainText",
          content:
            "Here are few options for your requested cuisine: Delight Dine, Mezza, Spoon Restaurant",
        },
        {
          contentType: "PlainText",
          content: `Shall I continue finding ${outputSessionAttributes["MenuType"]} restaurant in ${outputSessionAttributes["Area"]}?`,
        },
        outputSessionAttributes,
        event.requestAttributes
      );
    }
  } else if (event.sessionState.intent.name === "GetOpeningTimings") {
    console.log("inside GetOpeningTimings");
    if (
      outputSessionAttributes["Area"] &&
      outputSessionAttributes["RestaurantName"]
    ) {
      // API call for fetching operating hours for restaurant
      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content:
                "Delight Dine will be open 10 AM to 11 PM from Monday to Saturday.",
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue finding operating hours for ${outputSessionAttributes["RestaurantName"]} located in ${outputSessionAttributes["Area"]}?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "GetLocationInformation") {
    console.log("inside GetLocationInformation");
    if (outputSessionAttributes["RestaurantName"]) {
      console.log(
        "attribute exists",
        outputSessionAttributes["RestaurantName"]
      );
      // API call for fetching location info for restaurant
      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content:
                "Delight Dine is located at 123, Robie Street, Halifax B3H0C3",
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue finding location for ${outputSessionAttributes["RestaurantName"]}?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "MenuAvailability") {
    console.log("inside MenuAvailability");
    if (
      outputSessionAttributes["RestaurantName"] &&
      outputSessionAttributes["Area"] &&
      outputSessionAttributes["MenuType"]
    ) {
      console.log(
        "attribute exists",
        outputSessionAttributes["RestaurantName"]
      );
      // API call for fetching menu for restaurant
      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content:
                "Delight Dine is offering Biryani, Butter Chicken, Tandoori Chicken, Chole Bhature, Rogan Josh, Palak Paneer, Masala Dosa, Samosa, Vada Pav, Dhokla, Aloo Gobi, Dal Makhani, Pani Puri, Chicken Tikka Masala, Lassi, Jalebi, Gulab Jamun, Paneer Tikka, Rasgulla and Idli Sambhar",
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue finding location for ${outputSessionAttributes["RestaurantName"]}?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "ReviewRestaurant") {
    console.log("inside ReviewRestaurant");
    const review =
      event.sessionState.intent.slots.Review?.value?.interpretedValue;
    if (
      outputSessionAttributes["RestaurantName"] &&
      // outputSessionAttributes["Area"] &&
      review !== null
    ) {
      console.log("Fulfilled all slots");

      // API call for fetching for getting restaurant details
      if (event.sessionState.intent.confirmationState === "Confirmed") {
        console.log("AInside confirmation block for review");
        return await addReview(
            {
            review_id: uuidv4(),
              type: "RESTAURANT",
              id: "1",
            customer_id: "john@gmail.com",
            review: review,
          },
          (res) => {
            console.log("Success:", res);

          return close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
                content: "Thank your for your valuable review",
            },
            event.requestAttributes
          );
          },
          (err) => {
            console.log("Error:", err);

            return close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
                content: "Oops! Something went wrong.",
            },
            event.requestAttributes
            );
          }
        );
      }

      if (restaurantName && review)
        return confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue posting review for ${outputSessionAttributes["RestaurantName"]}?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "ReviewMenuItem") {
    const review =
      event.sessionState.intent.slots.Review?.value?.interpretedValue;
    console.log("inside ReviewMenuItem");
    if (
      outputSessionAttributes["RestaurantName"] &&
      // outputSessionAttributes["Area"] &&
      // outputSessionAttributes["MenuType"] &&
      outputSessionAttributes["MenuItem"] &&
      review !== null
    ) {
      console.log(
        "attribute exists",
        outputSessionAttributes["RestaurantName"]
      );
      // API call for fetching menu for restaurant

      if (event.sessionState.intent.confirmationState === "Confirmed") {
        console.log("AInside confirmation block for review");
        return await addReview(
            {
            review_id: uuidv4(),
              type: "ITEM",
              id: "1",
            customer_id: "john@gmail.com",
            review: review,
          },
          (res) => {
            console.log("Success:", res);

          return close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
                content: "Thank your for your valuable review",
            },
            event.requestAttributes
          );
          },
          (err) => {
            console.log("Error:", err);

            return close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
                content: "Oops! Something went wrong.",
            },
            event.requestAttributes
            );
          }
        );
      }

      if (restaurantName && menuItem && review)
        return confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue posting review for ${outputSessionAttributes["MenuItem"]} offered by ${outputSessionAttributes["RestaurantName"]}?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "RateRestaurant") {
    const rating =
      event.sessionState.intent.slots.Ratings?.value?.interpretedValue;
    console.log("inside RateRestaurant");
    if (
      outputSessionAttributes["RestaurantName"] &&
      // outputSessionAttributes["Area"] &&
      rating !== null
    ) {
      console.log(
        "attribute exists",
        outputSessionAttributes["RestaurantName"]
      );
      // API call for fetching restaurant details for restaurant_id
      if (event.sessionState.intent.confirmationState === "Confirmed") {
        return await addRating(
            {
            rating_id: uuidv4(),
            customer_id: "john@gmail.com",
            rating: rating,
            restaurant_id: "1",
          },
          (res) => {
            console.log("Success:", res);

          return close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
                content: `Thank your for rating ${restaurantName}`,
            },
            event.requestAttributes
          );
          },
          (err) => {
            console.log("Error:", err);

            return close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
                content: "Oops! Something went wrong.",
            },
            event.requestAttributes
            );
          }
        );
      }

      if (restaurantName && rating)
        return confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
            content: `Shall I continue posting rating ${rating} for ${outputSessionAttributes["RestaurantName"]}?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "BookReservation") {
    console.log("inside BookReservation");
    if (
      outputSessionAttributes["RestaurantName"] &&
      outputSessionAttributes["Area"] &&
      outputSessionAttributes["PrefData"] &&
      outputSessionAttributes["PrefTime"] &&
      outputSessionAttributes["TotalPeople"] &&
      outputSessionAttributes["MenuType"]
      //   &&
      //   outputSessionAttributes["MenuItem"]
    ) {
      console.log(
        "attribute exists",
        outputSessionAttributes["RestaurantName"]
      );
      // API call for fetching restaurant reservation details
      if (event.sessionState.intent.confirmationState === "Confirmed") {
        try {
          const response = await axios.post("api-here", {});
          console.log(response);
        } catch (err) {
          return close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: "Oops! Something went wrong.",
            },
            event.requestAttributes
          );
        }
      }

      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Sounds like a plan! I have booked your reservation at ${outputSessionAttributes["RestaurantName"]} on ${outputSessionAttributes["PrefData"]} for ${outputSessionAttributes["PrefTime"]}. Hope you enjoy!`,
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue booking your reservation at ${outputSessionAttributes["RestaurantName"]} for ${outputSessionAttributes["TotalPeople"]}  on ${outputSessionAttributes["PrefData"]} and time ${outputSessionAttributes["PrefTime"]}?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "CheckAvailableReservation") {
    console.log("inside CheckAvailableReservation");
    if (
      outputSessionAttributes["RestaurantName"] &&
      outputSessionAttributes["Area"] &&
      outputSessionAttributes["PrefData"] &&
      outputSessionAttributes["PrefTime"] &&
      outputSessionAttributes["TotalPeople"]
    ) {
      console.log(
        "attribute exists",
        outputSessionAttributes["RestaurantName"]
      );
      // API call for reservation details
      if (event.sessionState.intent.confirmationState === "Confirmed") {
        try {
          const response = await axios.post("api-here", {});
          console.log(response);
        } catch (err) {
          return close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: "Oops! Something went wrong.",
            },
            event.requestAttributes
          );
        }
      }

      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Hope this was helpful! Let me know if you want to do anything else.`,
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue looking your reservation at ${outputSessionAttributes["RestaurantName"]} for ${outputSessionAttributes["TotalPeople"]}  on ${outputSessionAttributes["PrefData"]} and time ${outputSessionAttributes["PrefTime"]}?`,
            },
            event.requestAttributes
          );
    }
  }

  return delegate(
    event.sessionState,
    outputSessionAttributes,
    event.requestAttributes
  );
};
