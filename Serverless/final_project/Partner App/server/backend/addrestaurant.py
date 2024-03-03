import json
import boto3

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('Restaurant')

def lambda_handler(event, context):
    try:
        print("value is", event)

        restaurant_id = event.get('restaurant_id')
        print(restaurant_id)
        if not restaurant_id or not restaurant_id.strip():
            raise ValueError("Invalid 'restaurant_id'")
        
        restaurant_name = event.get('restaurant_name')
        status = event.get('status')
        menu = event.get('menu')
        address_line = event.get('address_line')
        close_hours = event.get('close_hours')
        open_hours = event.get('open_hours')
        phone = event.get('phone')
        menu = event.get('menu')

        params = {
            'TableName': 'Restaurant',
            'Item': {
                'restaurant_id': restaurant_id,
                'restaurant_name':restaurant_name,
                'status':status,
                'address_line':address_line,
                'close_hours':close_hours,
                'open_hours':open_hours,
                'phone':phone,
                'menu':menu
            },
        }

        table.put_item(Item=params['Item'])

        response = {
            'statusCode': 200,
            'body': json.dumps({'message': 'Restaurant details added successfully'}),
        }

    except Exception as e:
        print(f'Error adding restaurant details: {e}')

        response = {
            'statusCode': 500,
            'body': json.dumps({'error': 'Internal Server Error'}),
        }

    return response
