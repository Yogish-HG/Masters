import json
import boto3
import firebase_admin
from firebase_admin import credentials, firestore

def get_firebase_credentials():
    client = boto3.client('secretsmanager')
    secret_name = 'babu'  
    secret = client.get_secret_value(SecretId=secret_name)
    return json.loads(secret['SecretString'])

def lambda_handler(event, context):
    if not firebase_admin._apps:
        firebase_creds = get_firebase_credentials()
        cred = credentials.Certificate(firebase_creds)
        firebase_admin.initialize_app(cred)

    db = firestore.client()

    reservation_id = event.get('reservation_id')
    if not reservation_id:
        return {'statusCode': 400, 'body': json.dumps({'message': 'reservation_id is required'})}

    reservation_ref = db.collection('reservations').document(reservation_id)

    if not reservation_ref.get().exists:
        return {'statusCode': 404, 'body': json.dumps({'message': 'Reservation not found'})}

    reservation_ref.delete()

    return {'statusCode': 200, 'body': json.dumps({'message': 'Reservation deleted successfully'})}
