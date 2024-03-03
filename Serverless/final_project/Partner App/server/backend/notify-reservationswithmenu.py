import boto3
import json

sns_topic_arn = "arn:aws:sns:us-east-1:248274840893:reservations-change"

def send_sns(message, subject):
    sns_client = boto3.client("sns")
    sns_client.publish(TopicArn=sns_topic_arn, Message=message, Subject=subject)

def lambda_handler(event, context):
    for record in event['Records']:
        # Making sure the record is modified.
        if record['eventName'] == 'MODIFY':
            # Extracting customer_id and the menu added by customer
            customer_id = record['dynamodb']['NewImage']['customer_id']['S']
            menu_items = record['dynamodb']['NewImage']['menu_id']['S']
            
            subject = f'Reservation Added with menu'
            message = f'New Reservation: from {customer_id}. Menu Items added: {menu_items}.'
            
            # Send SNS notification
            send_sns(message, subject)
    
    return "Processing Complete"
    
