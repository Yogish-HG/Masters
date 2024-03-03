import json
import boto3

def lambda_handler(event, context):
    # Create a DynamoDB client
    dynamodb = boto3.resource('dynamodb')
    
    # Specify your DynamoDB table name
    table_name = 'CustomerTable'
    
    # Connect to the DynamoDB table
    table = dynamodb.Table(table_name)

# Perform a scan or query to get items with the specified condition
    response = table.scan(
    Select='ALL_ATTRIBUTES',  # or use Query if applicable
)
    
    items = response['Items']
    
    print(len(items))
    
    return {
        'statusCode': 200,
        'body': 'DynamoDB connection and scan successful!'
    }
