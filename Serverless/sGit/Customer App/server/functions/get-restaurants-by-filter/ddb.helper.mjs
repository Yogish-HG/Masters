import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
  DynamoDBDocumentClient,
  ExecuteStatementCommand,
} from "@aws-sdk/lib-dynamodb";

const client = new DynamoDBClient({ region: "us-east-1" });
const ddbDocClient = DynamoDBDocumentClient.from(client);
const tableName = "Restaurant";

export const getRestaurantByName = async (restaurantName) => {
  const selectItemStatementCommand = new ExecuteStatementCommand({
    Statement: `SELECT * FROM ${tableName} WHERE restaurant_name= ?`,
    Parameters: [`${restaurantName}`],
    ConsistentRead: true,
  });

  const response = await ddbDocClient.send(selectItemStatementCommand);

  return response;
};

export const getRestaurantByAddress = async (addressLine) => {
  const selectItemStatementCommand = new ExecuteStatementCommand({
    Statement: `SELECT * FROM ${tableName} WHERE address_line= ?`,
    Parameters: [`${addressLine}`],
    ConsistentRead: true,
  });

  const response = await ddbDocClient.send(selectItemStatementCommand);

  return response;
};

export const getRestaurantByNameAndAddress = async (
  restaurantName,
  addressLine
) => {
  const selectItemStatementCommand = new ExecuteStatementCommand({
    Statement: `SELECT * FROM ${tableName} WHERE restaurant_name= ? AND contains(address_line, ?)`,
    Parameters: [`${restaurantName}`, `${addressLine}`],
    ConsistentRead: true,
  });

  const response = await ddbDocClient.send(selectItemStatementCommand);

  return response;
};
