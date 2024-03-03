import boto3
import json

sns_topic_arn = "arn:aws:sns:us-east-1:248274840893:reservations-change"

def send_sns(message, subject):
    sns_client = boto3.client("sns")
    sns_client.publish(TopicArn=sns_topic_arn, Message=message, Subject=subject)

def lambda_handler(event, context):
    for record in event['Records']:
        # Extracting the event name (INSERT, MODIFY, REMOVE)
        event_name = record['eventName']

        if event_name in ['INSERT', 'MODIFY', 'REMOVE']:
            # Extracting customer_id
            customer_id = record['dynamodb']['NewImage']['customer_id']['S'] if event_name != 'REMOVE' else 'Unknown'

            subject = f'Reservation {event_name.capitalize()}'
            message = f'Reservation {event_name.lower()}: from {customer_id}.'

            # Send SNS notification
            send_sns(message, subject)

    return "Processing Complete"
