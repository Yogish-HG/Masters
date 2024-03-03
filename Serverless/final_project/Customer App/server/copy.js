import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
  DynamoDBDocumentClient,
  QueryCommand,
  BatchGetCommand,
}
from "@aws-sdk/lib-dynamodb";

/**
 * send response to lex
 * @param {*} sessionState
 * @param {*} fulfillmentState
 * @param {*} message
 * @param {*} requestAttributes
 * @returns
 */
function close(sessionState, fulfillmentState, savedAttributes, message, requestAttributes) {
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
// function elicitSlot(sessionAttributes, intentName, slots, slotToElicit, message) {
//     return {
//        sessionAttributes,
//        dialogAction: {
//            type: 'ElicitSlot',
//            intentName,
//            slots,
//            slotToElicit,
//            message,

//        },
//    };
// }

function validateConfirmation(sessionState, closingMessage, confirmationMessage, outputSessionAttributes, requestAttributes) {
  return sessionState.intent.confirmationState === "Confirmed" ?
    close(
      sessionState,
      "Fulfilled",
      outputSessionAttributes,
      closingMessage,
      requestAttributes
    ) : sessionState.intent.confirmationState === "Denied" ? delegate(sessionState, {}, requestAttributes) :
    confirm(
      sessionState,
      outputSessionAttributes,
      confirmationMessage,
      requestAttributes
    );
}

export const handler = async (event, context) => {
  const client = new DynamoDBClient({});
  const docClient = DynamoDBDocumentClient.from(client);

  const slotName = Object.keys(event.sessionState.intent.slots);

  console.log("slotName", slotName);
  var area, menuType, menuItem, restaurantName;
  const outputSessionAttributes = event.sessionState.sessionAttributes || {};

  area = event.sessionState.intent.slots.Area?.value?.interpretedValue;
  menuType = event.sessionState.intent.slots.MenuType?.value?.interpretedValue;
  menuItem = event.sessionState.intent.slots.MenuItem?.value?.interpretedValue;
  restaurantName =
    event.sessionState.intent.slots.RestaurantName?.value?.interpretedValue;

  if (event.sessionState.intent.name === "GetAvailableRestaurants") {
    if (
      outputSessionAttributes["Area"] &&
      outputSessionAttributes["MenuType"]
    ) {
      console.log("attribute exists");
      return validateConfirmation(event.sessionState, {
        contentType: "PlainText",
        content: "Here are few options for your requested cuisine: Delight Dine, Mezza, Spoon Restaurant",
      }, {
        contentType: "PlainText",
        content: `Shall I continue finding ${outputSessionAttributes["MenuType"]} restaurant in ${outputSessionAttributes["Area"]}?`,
      }, outputSessionAttributes, event.requestAttributes)
      // return event.sessionState.intent.confirmationState === "Confirmed" ?
      //   close(
      //     event.sessionState,
      //     "Fulfilled",
      //     outputSessionAttributes, {
      //       contentType: "PlainText",
      //       content: "Here are few options for your requested cuisine: Delight Dine, Mezza, Spoon Restaurant",
      //     },
      //     event.requestAttributes
      //   ) : event.sessionState.intent.confirmationState === "Denied" ? delegate(event.sessionState, {}, event.requestAttributes) :
      //   confirm(
      //     event.sessionState,
      //     outputSessionAttributes, {
      //       contentType: "PlainText",
      //       content: `Shall I continue finding ${outputSessionAttributes["MenuType"]} restaurant in ${outputSessionAttributes["Area"]}?`,
      //     },
      //     event.requestAttributes
      //   );
    }
    if (area !== null) {
      console.log("adding attribute area", area);

      outputSessionAttributes["Area"] = area;
    }
    // console.log("Area", area);
    if (menuType !== null) {
      console.log("adding attribute menuType", menuType);

      outputSessionAttributes["MenuType"] = menuType;
    }
    // console.log("menuType", menuType);
    if (menuItem !== null) {
      console.log("adding attribute menuItem", menuItem);

      outputSessionAttributes["MenuItem"] = menuItem;
    }
    // console.log("MenuItem", menuItem);

    if (area && menuType && menuItem) {
      // API call for fetching restaurants
      // store fetched restaurant name
      outputSessionAttributes["RestaurantName"] = "Delight Dine";
      return close(
        event.sessionState,
        "Fulfilled",
        outputSessionAttributes, {
          contentType: "PlainText",
          content: "Here are few options for your requested cuisine: Delight Dine, Mezza, Spoon Restaurant",
        },
        event.requestAttributes
      );
    }
    else {
      return delegate(
        event.sessionState,
        outputSessionAttributes,
        event.requestAttributes
      );
    }
  }
  else if (event.sessionState.intent.name === "GetOpeningTimings") {
    console.log("inside GetOpeningTimings");
    if (
      outputSessionAttributes["Area"] &&
      outputSessionAttributes["RestaurantName"]
    ) {
      console.log("attribute exists");
      return event.sessionState.intent.confirmationState === "Confirmed" ?
        close(
          event.sessionState,
          "Fulfilled",
          outputSessionAttributes, {
            contentType: "PlainText",
            content: "Delight Dine will be open 10 AM to 11 PM from Monday to Saturday.",
          },
          event.requestAttributes
        ) :
        confirm(
          event.sessionState,
          outputSessionAttributes, {
            contentType: "PlainText",
            content: `Shall I continue finding operating hours for ${outputSessionAttributes["RestaurantName"]} located in ${outputSessionAttributes["Area"]}?`,
          },
          event.requestAttributes
        );
    }
    if (area !== null) {
      console.log("adding attribute area", area);

      outputSessionAttributes["Area"] = area;
    }
    // console.log("Area", area);
    if (restaurantName !== null) {
      console.log("adding attribute restaurantName", restaurantName);

      outputSessionAttributes["RestaurantName"] = restaurantName;
    }

    if (area && restaurantName) {
      // API call for fetching operating hours for restaurant
      return close(
        event.sessionState,
        "Fulfilled",
        outputSessionAttributes, {
          contentType: "PlainText",
          content: "Delight Dine will be open 10 AM to 11 PM from Monday to Saturday.",
        },
        event.requestAttributes
      );
    }
    else {
      return delegate(
        event.sessionState,
        outputSessionAttributes,
        event.requestAttributes
      );
    }
  }
  else if (event.sessionState.intent.name === "GetLocationInformation") {
    restaurantName =
      event.sessionState.intent.slots.RestaurantName?.value?.interpretedValue;

    console.log("inside GetLocationInformation");
  }
  else if (event.sessionState.intent.name === "MenuAvailability") {
    console.log("inside MenuAvailability");
  }
  else if (event.sessionState.intent.name === "ReviewRestaurant") {
    console.log("inside ReviewRestaurant");
  }
  else if (event.sessionState.intent.name === "ReviewMenuItem") {
    console.log("inside ReviewMenuItem");
  }
  else if (event.sessionState.intent.name === "RateRestaurant") {
    console.log("inside RateRestaurant");
  }
  else if (event.sessionState.intent.name === "BookReservation") {
    console.log("inside BookReservation");
  }
  else if (event.sessionState.intent.name === "CheckAvailableReservation") {
    console.log("inside CheckAvailableReservation");
  }

  return delegate(event.sessionState, outputSessionAttributes, event.requestAttributes);
};
