import axios from "axios";
import { v4 as uuidv4 } from "uuid";
import {
  close,
  confirm,
  elicitSlot,
  delegate,
  validateConfirmation,
} from "./utils.mjs";
import { getReview } from "./services/reviews.mjs";

export const handler = async (event, context) => {
  const slotName = Object.keys(event.sessionState.intent.slots);

  console.log("slotName", slotName);
  const outputSessionAttributes = event.sessionState.sessionAttributes || {};

  if (event.sessionState.intent.name === "GetBookingInfo") {
    var bookingDate =
      event.sessionState.intent.slots.BookingDate?.value?.interpretedValue;

    if (bookingDate !== null) {
      console.log("attribute exists");

      // API call for fetching booking informations
      // show booked slots here

      return validateConfirmation(
        event.sessionState,
        {
          contentType: "PlainText",
          content: "Here are reservation details for monday: 1, 2, 3",
        },
        {
          contentType: "PlainText",
          content: `Shall I continue finding reservation details for ${bookingDate}?`,
        },
        outputSessionAttributes,
        event.requestAttributes
      );
    }
  } else if (event.sessionState.intent.name === "GetOpeningTimes") {
    console.log("inside GetOpeningTimes");

    // API call for fetching operating hours for restaurant

    return close(
      event.sessionState,
      "Fulfilled",
      outputSessionAttributes,
      {
        contentType: "PlainText",
        content:
          "Delight Dine will be open 10 AM to 11 PM from Monday to Saturday.",
      },
      event.requestAttributes
    );
  } else if (event.sessionState.intent.name === "EditOpeningTimes") {
    console.log("inside EditOpeningTimes");
    var newOpeningTime =
        event.sessionState.intent.slots.NewOpeningTime?.value?.interpretedValue,
      newClosingTime =
        event.sessionState.intent.slots.NewClosingTime?.value?.interpretedValue;
    if (newOpeningTime !== null && newClosingTime !== null) {
      console.log(
        "attribute exists",
        outputSessionAttributes["NewOpeningTime"],
        outputSessionAttributes["NewClosingTime"]
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
                "Delight Dine will be now open 10 AM to 11 PM from Monday to Saturday.",
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue updating times to ${newOpeningTime} - ${newClosingTime}?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "GetLocationInfo") {
    console.log("inside GetLocationInfo");

    // API call for fetching location information for restaurant

    return event.sessionState.intent.confirmationState === "Confirmed"
      ? close(
          event.sessionState,
          "Fulfilled",
          outputSessionAttributes,
          {
            contentType: "PlainText",
            content: "Delight Dine is located at xyz",
          },
          event.requestAttributes
        )
      : confirm(
          event.sessionState,
          outputSessionAttributes,
          {
            contentType: "PlainText",
            content: `Shall I continue finding location for your restaurant?`,
          },
          event.requestAttributes
        );
  } else if (event.sessionState.intent.name === "EditLocationInfo") {
    console.log("inside EditLocationInfo");
    var newStreetName =
        event.sessionState.intent.slots.NewStreetName?.value?.interpretedValue,
      newPostalCode =
        event.sessionState.intent.slots.NewPostalCode?.value?.interpretedValue;
    if (newStreetName && newPostalCode) {
      console.log(
        "attribute exists",
        outputSessionAttributes["NewStreetName"],
        outputSessionAttributes["NewPostalCode"]
      );
      // API call for editing location information for restaurant

      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Delight Dine's location is updated to ${newStreetName}, ${newPostalCode}`,
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue updating location for your restaurant?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "CheckAvailableMenu") {
    console.log("inside CheckAvailableMenu");
    var menuType =
        event.sessionState.intent.slots.MenuType?.value?.interpretedValue,
      menuName =
        event.sessionState.intent.slots.MenuName?.value?.interpretedValue;
    if (menuType && menuName) {
      console.log(
        "attribute exists",
        outputSessionAttributes["NewStreetName"],
        outputSessionAttributes["NewPostalCode"]
      );

      // API call for fetching specific menu for restaurant

      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Delight Dine's has quantity available for ${menuName}`,
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue checking availability of ${menuName} in ${menuType} cuisine for your restaurant?`,
            },
            event.requestAttributes
          );
    }
  }
  if (event.sessionState.intent.name === "CheckAvailableReservations") {
    var bookingDate =
      event.sessionState.intent.slots.BookingDate?.value?.interpretedValue;

    if (bookingDate) {
      console.log(
        "attribute exists",
        outputSessionAttributes["NewStreetName"],
        outputSessionAttributes["NewPostalCode"]
      );

      // API call for fetching booking info menu for restaurant
      // show only available slots here

      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Delight Dine's has following reservations slots available for ${bookingDate}`,
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue checking slot availability of ${bookingDate} for your restaurant?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "EditReservation") {
  } else if (event.sessionState.intent.name === "CancelReservation") {
  }

  if (event.sessionState.intent.name === "GetBookingInfo") {
    var bookingDate =
      event.sessionState.intent.slots.BookingDate?.value?.interpretedValue;

    if (bookingDate !== null) {
      console.log("attribute exists");

      // API call for fetching booking informations
      // show booked slots here

      return validateConfirmation(
        event.sessionState,
        {
          contentType: "PlainText",
          content: "Here are reservation details for monday: 1, 2, 3",
        },
        {
          contentType: "PlainText",
          content: `Shall I continue finding reservation details for ${bookingDate}?`,
        },
        outputSessionAttributes,
        event.requestAttributes
      );
    }
  } else if (event.sessionState.intent.name === "GetOpeningTimes") {
    console.log("inside GetOpeningTimes");

    // API call for fetching operating hours for restaurant

    return close(
      event.sessionState,
      "Fulfilled",
      outputSessionAttributes,
      {
        contentType: "PlainText",
        content:
          "Delight Dine will be open 10 AM to 11 PM from Monday to Saturday.",
      },
      event.requestAttributes
    );
  } else if (event.sessionState.intent.name === "EditOpeningTimes") {
    console.log("inside EditOpeningTimes");
    var newOpeningTime =
        event.sessionState.intent.slots.NewOpeningTime?.value?.interpretedValue,
      newClosingTime =
        event.sessionState.intent.slots.NewClosingTime?.value?.interpretedValue;
    if (newOpeningTime !== null && newClosingTime !== null) {
      console.log(
        "attribute exists",
        outputSessionAttributes["NewOpeningTime"],
        outputSessionAttributes["NewClosingTime"]
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
                "Delight Dine will be now open 10 AM to 11 PM from Monday to Saturday.",
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue updating times to ${newOpeningTime} - ${newClosingTime}?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "GetLocationInfo") {
    console.log("inside GetLocationInfo");

    // API call for fetching location information for restaurant

    return event.sessionState.intent.confirmationState === "Confirmed"
      ? close(
          event.sessionState,
          "Fulfilled",
          outputSessionAttributes,
          {
            contentType: "PlainText",
            content: "Delight Dine is located at xyz",
          },
          event.requestAttributes
        )
      : confirm(
          event.sessionState,
          outputSessionAttributes,
          {
            contentType: "PlainText",
            content: `Shall I continue finding location for your restaurant?`,
          },
          event.requestAttributes
        );
  } else if (event.sessionState.intent.name === "EditLocationInfo") {
    console.log("inside EditLocationInfo");
    var newStreetName =
        event.sessionState.intent.slots.NewStreetName?.value?.interpretedValue,
      newPostalCode =
        event.sessionState.intent.slots.NewPostalCode?.value?.interpretedValue;
    if (newStreetName && newPostalCode) {
      console.log(
        "attribute exists",
        outputSessionAttributes["NewStreetName"],
        outputSessionAttributes["NewPostalCode"]
      );
      // API call for editing location information for restaurant

      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Delight Dine's location is updated to ${newStreetName}, ${newPostalCode}`,
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue updating location for your restaurant?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "CheckAvailableMenu") {
    console.log("inside CheckAvailableMenu");
    var menuType =
        event.sessionState.intent.slots.MenuType?.value?.interpretedValue,
      menuName =
        event.sessionState.intent.slots.MenuName?.value?.interpretedValue;
    if (menuType && menuName) {
      console.log(
        "attribute exists",
        outputSessionAttributes["NewStreetName"],
        outputSessionAttributes["NewPostalCode"]
      );

      // API call for fetching specific menu for restaurant

      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Delight Dine's has quantity available for ${menuName}`,
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue checking availability of ${menuName} in ${menuType} cuisine for your restaurant?`,
            },
            event.requestAttributes
          );
    }
  }
  if (event.sessionState.intent.name === "CheckAvailableReservations") {
    var bookingDate =
      event.sessionState.intent.slots.BookingDate?.value?.interpretedValue;

    if (bookingDate) {
      console.log(
        "attribute exists",
        outputSessionAttributes["NewStreetName"],
        outputSessionAttributes["NewPostalCode"]
      );

      // API call for fetching booking info menu for restaurant
      // show only available slots here

      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Delight Dine's has following reservations slots available for ${bookingDate}`,
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue checking slot availability of ${bookingDate} for your restaurant?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "EditReservation") {
  } else if (event.sessionState.intent.name === "CancelReservation") {
  } else if (event.sessionState.intent.name === "GetRestaurantReviews") {
    console.log("Slot: GetRestaurantReviews");
    var showMoreReviews =
      event.sessionState.intent.slots.ShowMoreReviews?.value?.interpretedValue;
    var currentPage =
      (event.sessionState.sessionAttributes && event.sessionState.sessionAttributes.CurrentReviewPage) ||
      1;

    if (showMoreReviews) {
      currentPage++;
      outputSessionAttributes["CurrentReviewPage"] = currentPage;
    }
    // API call for fetching reviews for restaurant based on offset
    const response = await getReview(currentPage, {
      id: "1",
    });
    
    const reviews = ["It was an amazing experience", "Dining experience was nice", "Italian, especially, pasta is nice",  "Indian pavbhaji was never I've had before", "Just wow!!"]
    for (let index = 0; index < 5; index++) {
      close(
        event.sessionState,
        "Fulfilled",
        outputSessionAttributes,
        {
          contentType: "PlainText",
          content: `${reviews[index]}`,
        },
        event.requestAttributes
      )
    }
    return event.sessionState.intent.confirmationState === "Confirmed"
      ? close(
          event.sessionState,
          "Fulfilled",
          outputSessionAttributes,
          {
            contentType: "PlainText",
            content: `Delight Dine's had following reviews.`,
          },
          event.requestAttributes
        )
      : confirm(
          event.sessionState,
          outputSessionAttributes,
          {
            contentType: "PlainText",
            content: `Shall I continue getting some reviews for your restaurant?`,
          },
          event.requestAttributes
        );
  } else if (event.sessionState.intent.name === "GetMenuItemReviews") {
    var showMoreReviews = event.currentIntent.slots.ShowMoreReviews;
    var currentPage =
      (event.sessionAttributes && event.sessionAttributes.CurrentReviewPage) ||
      1;
    var menuItem =
      event.sessionState.intent.slots.MenuItem?.value?.interpretedValue;

    if (menuItem) {
      if (showMoreReviews) {
        currentPage++;
        outputSessionAttributes["CurrentReviewPage"] = currentPage;
      }
      // API call for fetching menu item reviews for restaurant based on offset

      return event.sessionState.intent.confirmationState === "Confirmed"
        ? close(
            event.sessionState,
            "Fulfilled",
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Delight Dine's has following reviews for ${menuItem}.`,
            },
            event.requestAttributes
          )
        : confirm(
            event.sessionState,
            outputSessionAttributes,
            {
              contentType: "PlainText",
              content: `Shall I continue getting some reviews for ${menuItem} served in your restaurant?`,
            },
            event.requestAttributes
          );
    }
  } else if (event.sessionState.intent.name === "GetRestaurantRatings") {
    // API call for fetching overall ratings for restaurant

    return event.sessionState.intent.confirmationState === "Confirmed"
      ? close(
          event.sessionState,
          "Fulfilled",
          outputSessionAttributes,
          {
            contentType: "PlainText",
            content: `Delight Dine's has following reviews for ${menuItem}.`,
          },
          event.requestAttributes
        )
      : confirm(
          event.sessionState,
          outputSessionAttributes,
          {
            contentType: "PlainText",
            content: `Shall I continue getting some reviews for ${menuItem} served in your restaurant?`,
          },
          event.requestAttributes
        );
  }
  return delegate(
    event.sessionState,
    outputSessionAttributes,
    event.requestAttributes
  );
};
