import boto3
from botocore.exceptions import ClientError
import json

def lambda_handler(event, context):
    try:
        dynamodb = boto3.resource('dynamodb')
        table_name = 'CustomerTable'
        table = dynamodb.Table(table_name)
        # print(event)
        # customer_email = json.loads(event['Email'])
        # event = event.get_json()
        # customer_email = event['body']
        request_body = json.loads(event['body'])
        customer_email = request_body.get('Email')
        print(customer_email)
        
        # Perform a query operation to get the item with the specified email
        response = table.query(
            IndexName='email-index',
            KeyConditionExpression='email = :email',
            ExpressionAttributeValues={':email': customer_email}
        )

        items = response['Items']

        if items:
            # Return a response with CORS headers
            return {
                'body': items
            }
        else:
            return {
                'body': f'Item with CustomerEmail {customer_email} not found.'
            }
            
    except ClientError as e:
        return {
            'body': f'Error retrieving item: {str(e)}'
        }