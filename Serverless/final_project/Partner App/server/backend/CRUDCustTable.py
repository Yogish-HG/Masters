import boto3
from botocore.exceptions import ClientError
import json

dynamodb = boto3.resource('dynamodb')
table_name = 'CustomerTable'
table = dynamodb.Table(table_name)

def lambda_handler(event, context):
    event2 = json.loads(event['body'])
    operation = event2.get('operation').lower()
    print(event)
    if operation == 'create':
        return create_item(event)
    elif operation == 'read':
        return read_item(event)
    # elif operation == 'update':
    #     return update_item(event)
    else:
        return {
            'statusCode': 400,
            'body': 'Invalid operation specified.'
        }

def create_item(event):
    try:
        event2 = json.loads(event['body'])
        customer_id = event2.get('CustomerId')
        name = event2.get('Name')
        email = event2.get('Email')

        # Perform the put operation to create a new item
        table.put_item(Item={'Customer_id': customer_id, 'Name': name, 'email': email})

        return {
            'statusCode': 200,
            'body': f'CustomerId created successfully.'
        }
    except ClientError as e:
        return {
            'statusCode': 500,
            'body': f'Error creating item: {str(e)}'
        }

def read_item(event):
    try:
        event2 = json.loads(event['body'])
        customer_id = event2.get('CustomerId')

        # Perform the get operation to read an item
        response = table.get_item(Key={'Customer_id': customer_id})

        item = response.get('Item')
        if item:
            return {
                'statusCode': 200,
                'body': f'Read item: {item}'
            }
        else:
            return {
                'statusCode': 404,
                'body': f'Item with CustomerId {customer_id} not found.'
            }
    except ClientError as e:
        return {
            'statusCode': 500,
            'body': f'Error reading item: {str(e)}'
        }

# def update_item(event):
#     try:
#         customer_id = event['CustomerId']
#         name = event.get('Name')
#         email = event.get('Email')

#         # Perform the update operation to modify an item
#         update_expression = 'SET '
#         expression_attribute_values = {}

#         if name:
#             update_expression += '#n = :n, '
#             expression_attribute_values[':n'] = name
#             expression_attribute_values['#n'] = 'Name'

#         if email:
#             update_expression += '#e = :e, '
#             expression_attribute_values[':e'] = email
#             expression_attribute_values['#e'] = 'Email'

#         if not name and not email:
#             return {
#                 'statusCode': 400,
#                 'body': 'No attributes provided for update.'
#             }

#         update_expression = update_expression.rstrip(', ')

#         table.update_item(
#             Key={'Customer_id': customer_id},
#             UpdateExpression=update_expression,
#             ExpressionAttributeValues=expression_attribute_values
#         )

#         return {
#             'statusCode': 200,
#             'body': f'Item with CustomerId {customer_id} updated successfully.'
#         }
#     except ClientError as e:
#         return {
#             'statusCode': 500,
#             'body': f'Error updating item: {str(e)}'
#         }

