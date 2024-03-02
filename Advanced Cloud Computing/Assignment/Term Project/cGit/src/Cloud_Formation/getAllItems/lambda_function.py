import boto3
import json
from decimal import Decimal

def lambda_handler(event, context):
    # Specify the DynamoDB table name
    table_name = 'termProjectItemsTable'

    # Create a DynamoDB resource
    dynamodb = boto3.resource('dynamodb')

    # Get the DynamoDB table
    table = dynamodb.Table(table_name)

    try:
        # Scan the table to retrieve all items
        response = table.scan()

        # Extract the items from the response
        items = json.loads(json.dumps(response['Items'], default=lambda x: str(x) if isinstance(x, Decimal) else x))
        print(items)
        # Return a complete response object with a statusCode
        return {
            'statusCode': 200,
            'body': json.dumps(items)
        }

    except Exception as e:
        # Handle any exceptions
        print(f"Error: {e}")
        return {
            'statusCode': 500,
            'body': json.dumps({'error': f'{e}'})
        }
