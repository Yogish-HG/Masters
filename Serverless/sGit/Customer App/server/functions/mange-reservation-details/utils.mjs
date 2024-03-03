/**
 * send response to lex
 * @param {*} sessionState
 * @param {*} fulfillmentState
 * @param {*} message
 * @param {*} requestAttributes
 * @returns
 */
export const close = (
  sessionState,
  fulfillmentState,
  savedAttributes,
  message,
  requestAttributes
) => {
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
};

export const delegate = (sessionState, savedAttributes, requestAttributes) => {
  sessionState.dialogAction = {
    type: "Delegate",
  };

  sessionState.sessionAttributes = savedAttributes;
  return {
    sessionState: sessionState,
    requestAttributes: requestAttributes,
  };
};

export const confirm = (
  sessionState,
  savedAttributes,
  message,
  requestAttributes
) => {
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
};

export const validateConfirmation = (
  sessionState,
  closingMessage,
  confirmationMessage,
  outputSessionAttributes,
  requestAttributes
) => {
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
};
