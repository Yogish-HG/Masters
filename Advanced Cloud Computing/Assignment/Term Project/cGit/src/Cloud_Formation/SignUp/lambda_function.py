import json
import boto3
import re

def sanitize_topic_name(topic_name):
    # Remove invalid characters from the topic name
    sanitized_name = re.sub(r'[^a-zA-Z0-9_-]', '', topic_name)
    
    # Ensure the length is within the allowed limit
    sanitized_name = sanitized_name[:256]

    return sanitized_name

def lambda_handler(event, context):
    # Retrieve values from the event
    print(event)
    # customer_email = event['customer_email']
    # name = event['name']
    # address = event['address']
    # password = event['password']
    
    event_body = json.loads(event['body'])
    customer_email = event_body['customer_email']
    name = event_body['name']
    address = event_body['address']
    password = event_body['password']

    # DynamoDB table name
    table_name = 'termProjectCustomerTable'
    orders_table_name = 'termProjectCustomerOrders'

    # Create a DynamoDB resource
    dynamodb = boto3.resource('dynamodb')

    # Connect to the DynamoDB table
    table = dynamodb.Table(table_name)
    orders_table = dynamodb.Table(orders_table_name)
    
    existing_item = table.get_item(Key={'Customer_email': customer_email})

    if 'Item' in existing_item:
        # If data with the same customer_email already exists, return an error response
        return {
            'statusCode': 400,
            'body': json.dumps('Error: Data with the same customer_email already exists!')
        }

    sns = boto3.client('sns')

    # Create an SNS topic for the registered email ID
    sns_topic_name = f'TopicFor{customer_email}'  # Unique topic name for each user
    sns_topic_name = sanitize_topic_name(sns_topic_name)
    print(sns_topic_name)
    response = sns.create_topic(Name=sns_topic_name)
    topic_arn = response['TopicArn']
    
    sns.subscribe(
        TopicArn=topic_arn,
        Protocol='email',
        Endpoint=customer_email
    )
    
    orders_table.put_item(
            Item={
                'email': customer_email,
                'orders': []
            }
        )
    # Insert values into the table
    response = table.put_item(
        Item={
            'Customer_email': customer_email,
            'name': name,
            'address': address,
            'password': password,
            'sns_topic_arn': topic_arn
        }
    )

    return {
        'statusCode': 200,
        'body': json.dumps('Data inserted successfully!')
    }
