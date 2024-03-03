import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
  DynamoDBDocumentClient,
  GetCommand,
  ScanCommand,
  PutCommand,
  DeleteCommand,
  ExecuteStatementCommand,
} from "@aws-sdk/lib-dynamodb";

const client = new DynamoDBClient({ region: "us-east-1" });
const ddbDocClient = DynamoDBDocumentClient.from(client);
const tableName = "Reviews";

export const putItem = async (item) => {
  const putCommand = new PutCommand({
    TableName: tableName,
    Item: item,
  });
  const response = await ddbDocClient.send(putCommand);
  console.log("put response", response);
  return response;
};

export const getItem = async (id) => {
  const getCommand = new GetCommand({
    TableName: tableName,
    Key: {
      review_id: id,
    },
    ConsistentRead: true,
  });
  const response = await ddbDocClient.send(getCommand);

  console.log("reponse", response);
  return response;
};

export const getAllItems = async () => {
  const getCommand = new ScanCommand({
    TableName: tableName,
  });
  const response = await ddbDocClient.send(getCommand);

  console.log("reponse", response);
  return response;
};

export const getItemByRestaurant = async (id) => {
  const getCommand = new ExecuteStatementCommand({
    Statement: `SELECT * FROM ${tableName} WHERE id=? AND type=?`,
    Parameters: [`${id}`, "RESTAURANT"],
    ConsistentRead: true,
  });
  const response = await ddbDocClient.send(getCommand);

  console.log("reponse", response);
  return response;
};

export const deleteItem = async (item) => {
  const deleteCommand = new DeleteCommand({
    TableName: tableName,
    Key: item,
  });
  const response = await client.send(deleteCommand);
  console.log("delete response", response);
  return response;
};
