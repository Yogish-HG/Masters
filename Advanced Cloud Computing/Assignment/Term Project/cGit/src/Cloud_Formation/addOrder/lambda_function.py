import json
import boto3

# Set the region for DynamoDB
dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
table_name = 'termProjectCustomerOrders'
table = dynamodb.Table(table_name)

def lambda_handler(event, context):
    try:
        # Parse the incoming event to get the email and new order data
        # email = event['email']
        # new_orders = event['orders']
        print("entered")
        body = json.loads(event['body'])
        email = body['email']
        new_orders = body['orders']

        # Retrieve existing data for the given email from DynamoDB
        existing_data = table.get_item(Key={'email': email})
        
        print(existing_data)

        # Check if the email exists in the DynamoDB table
        if 'Item' not in existing_data:
            return {
                'statusCode': 404,
                'body': json.dumps({'message': 'Email not found'})
            }

        # Add the new orders to the existing orders array
        existing_data['Item']['orders'].append(new_orders)

        # Update the DynamoDB item with the new orders array
        table.update_item(
            Key={'email': email},
            UpdateExpression='SET orders = :orders',
            ExpressionAttributeValues={':orders': existing_data['Item']['orders']}
        )
        print("success")
        return {
            'statusCode': 200,
            'body': 'Orders added successfully'
        }
    except Exception as e:
        print('Error:', e)
        return {
            'statusCode': 500,
            'body': 'Internal Server Error'
        }

