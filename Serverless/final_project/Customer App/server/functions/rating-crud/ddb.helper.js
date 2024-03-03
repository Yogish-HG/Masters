import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
  DynamoDBDocumentClient,
  GetCommand,
  ScanCommand,
  PutCommand,
  DeleteCommand,
} from "@aws-sdk/lib-dynamodb";

const client = new DynamoDBClient({ region: "us-east-1" });
const ddbDocClient = DynamoDBDocumentClient.from(client);
const tableName = "Ratings";

export const putItem = async (item) => {
  const putCommand = new PutCommand({
    TableName: tableName,
    Item: item,
  });
  const response = await ddbDocClient.send(putCommand);
  console.log("put response", response);
};

export const getItem = async (id) => {
  const getCommand = new GetCommand({
    TableName: tableName,
    Key: {
      id,
    },
    ConsistentRead: true,
  });
  const getResponse = await ddbDocClient.send(getCommand);

  console.log("reponse", getResponse);
};

export const getAllItems = async () => {
  const getCommand = new ScanCommand({
    TableName: tableName,
  });
  const getResponse = await ddbDocClient.send(getCommand);

  console.log("reponse", getResponse);
};

export const deleteItem = async (item) => {
  const deleteCommand = new DeleteCommand({
    TableName: tableName,
    Key: item,
  });
  const response = await client.send(deleteCommand);
  console.log("delete response", response);
};
