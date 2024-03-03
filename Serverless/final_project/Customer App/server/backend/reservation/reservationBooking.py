import json
import boto3
import firebase_admin
from firebase_admin import credentials, firestore

def get_firebase_credentials():
    client = boto3.client('secretsmanager')
    secret_name = 'babu' 
    secret = client.get_secret_value(SecretId=secret_name)
    return json.loads(secret['SecretString'])

def check_table_availability(db, restaurantId, start_time, end_time):
    reservations = db.collection('reservations').where('restaurantId', '==', restaurantId).stream()

    occupied_tables = set()
    for reservation in reservations:
        res_data = reservation.to_dict()
        if res_data['start_time'] <= end_time and res_data['end_time'] >= start_time:
            occupied_tables.add(res_data.get('table_number'))

    # Find an available table number
    for table_number in range(1, 21):  
        if table_number not in occupied_tables:
            return table_number
    return {'statusCode': 410, 'body': json.dumps({'message': 'No tables available'})}  

def check_duplicate_reservation(db, reservation_data):
    # Check for existing reservation with the same details
    potential_duplicates = db.collection('reservations')\
                             .where('customer_id', '==', reservation_data['customer_id'])\
                             .where('restaurantId', '==', reservation_data['restaurantId'])\
                             .where('start_time', '==', reservation_data['start_time'])\
                             .where('end_time', '==', reservation_data['end_time']).get()

    return len(potential_duplicates) > 0

def lambda_handler(event, context):
    if not firebase_admin._apps:
        firebase_creds = get_firebase_credentials()
        cred = credentials.Certificate(firebase_creds)
        firebase_admin.initialize_app(cred)

    db = firestore.client()

    reservation_data = {key: event.get(key) for key in ['customer_id', 'restaurantId', 'start_time', 'end_time', 'reservationDate', 'status', 'total_no_people', 'order']}

    if not all(reservation_data.values()):
        return {'statusCode': 400, 'body': json.dumps({'message': 'Missing required fields'})}

    if check_duplicate_reservation(db, reservation_data):
        return {'statusCode': 409, 'body': json.dumps({'message': 'Duplicate reservation'})}

    table_number = check_table_availability(db, reservation_data['restaurantId'], reservation_data['start_time'], reservation_data['end_time'])
    if table_number is None:
        return {'statusCode': 409, 'body': json.dumps({'message': 'No available tables for the selected time slot'})}

    reservation_data['table_number'] = table_number

    reservation_id = db.collection('reservations').document().id
    reservation_data['reservation_id'] = reservation_id

    db.collection('reservations').document(reservation_id).set(reservation_data)

    return {'statusCode': 200, 'body': json.dumps({'message': 'Reservation created successfully', 'reservation_id': reservation_id, 'table_number': table_number})}
