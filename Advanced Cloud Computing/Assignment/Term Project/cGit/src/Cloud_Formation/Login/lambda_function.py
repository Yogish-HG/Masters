import json
import boto3
from boto3.dynamodb.conditions import Key

def lambda_handler(event, context):
    # Retrieve values from the event
    # customer_email = event['customer_email']
    # password = event['password']

    body = json.loads(event['body'])
    customer_email = body['customer_email']
    password = body['password']
    # DynamoDB table name
    table_name = 'termProjectCustomerTable'

    # Create a DynamoDB resource
    dynamodb = boto3.resource('dynamodb')

    # Connect to the DynamoDB table
    table = dynamodb.Table(table_name)

    # Query DynamoDB to check if the customer_email and password match
    response = table.query(
        KeyConditionExpression=Key('Customer_email').eq(customer_email),
        FilterExpression=Key('password').eq(password)
    )

    # Check if there is a match
    if response.get('Items'):
        # Successful login
        return {
            'statusCode': 200,
            'body':'Login successful!'
        }
    else:
        # Failed login
        return {
            'statusCode': 401,
            'body': 'Error: Invalid credentials. Login failed!'
        }
