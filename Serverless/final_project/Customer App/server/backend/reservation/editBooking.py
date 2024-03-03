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
    update_data = {
        'customer_id': event.get('customer_id'),
        'restaurantId': event.get('restaurantId'),
        'start_time': event.get('start_time'),
        'end_time': event.get('end_time'),
        'reservationDate': event.get('reservationDate'),
        'status': event.get('status'),
        'total_no_people': event.get('total_no_people'),
        'order': event.get('order')
    }

    if not all([reservation_id, update_data['customer_id'], update_data['restaurantId'], update_data['start_time'], update_data['end_time'], update_data['reservationDate'], update_data['status'], update_data['total_no_people'], update_data['order']]):
        return {'statusCode': 400, 'body': json.dumps({'message': 'Missing required fields'})}

    reservation_ref = db.collection('reservations').document(reservation_id)

    if not reservation_ref.get().exists:
        return {'statusCode': 404, 'body': json.dumps({'message': 'Reservation not found'})}

    reservation_ref.update(update_data)

    return {'statusCode': 200, 'body': json.dumps({'message': 'Reservation updated successfully'})}
