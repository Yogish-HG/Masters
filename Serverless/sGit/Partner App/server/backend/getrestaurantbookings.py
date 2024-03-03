import json
import boto3
import firebase_admin
from firebase_admin import credentials, firestore

def get_firebase_credentials():
    # Create a Secrets Manager client
    client = boto3.client('secretsmanager')
    secret_name = 'babu'  
    secret = client.get_secret_value(SecretId=secret_name)
    return json.loads(secret['SecretString'])

def lambda_handler(event, context):
    # Initialize Firebase Admin SDK
    if not firebase_admin._apps:
        firebase_creds = get_firebase_credentials()
        cred = credentials.Certificate(firebase_creds)
        firebase_admin.initialize_app(cred)

    db = firestore.client()

    # Extracting the restaurant_id from the event
    restaurant_id = event.get('restaurant_id')
    if not restaurant_id:
        return {'statusCode': 400, 'body': json.dumps({'message': 'restaurant_id is required'})}

    # Query for reservations by restaurant_id
    reservations_query = db.collection('reservations').where('restaurantId', '==', restaurant_id).stream()

    # Constructing response data
    reservations = []
    for reservation in reservations_query:
        reservations.append(reservation.to_dict())

    return {
        'statusCode': 200,
        'body': json.dumps({'reservations': reservations})
    }
