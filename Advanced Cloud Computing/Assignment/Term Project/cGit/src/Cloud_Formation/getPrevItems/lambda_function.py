import json
import boto3
from decimal import Decimal

# Set the region for DynamoDB
dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
table_name = 'termProjectCustomerOrders'
table = dynamodb.Table(table_name)

def lambda_handler(event, context):
    try:
        # Get the email value from the request
        print(event)
        # email = event['email'] 
        body = json.loads(event['body'])
        email = body['email']
        # Query the DynamoDB table based on the email value
        response = table.query(
            KeyConditionExpression=boto3.dynamodb.conditions.Key('email').eq(email)
        )

        # Extract the 'Items' from the response
        items = response.get('Items', [])
        print(items)
        # items2 = items['body']

        # Convert the items to a JSON-formatted string
        

        return {
            'statusCode': 200,
            'body': json.dumps(items)
        }
    except Exception as e:
        print('Error:', e)
        return {
            'statusCode': 500,
            'body': 'Internal Server Error'
        }
