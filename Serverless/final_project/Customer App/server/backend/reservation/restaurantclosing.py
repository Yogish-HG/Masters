import boto3
import json

sns_topic_arn = "arn:aws:sns:us-east-1:248274840893:restaurant-change"

def send_sns(message, subject):
    sns_client = boto3.client("sns")
    sns_client.publish(TopicArn=sns_topic_arn, Message=message, Subject=subject)

def lambda_handler(event, context):
    for record in event['Records']:
        # Making sure the record is modified.
        if record['eventName'] == 'MODIFY':
            new_status = record['dynamodb']['NewImage']['status']['S'].lower()
            # Checking if the actual status of restaurant is closed.
            if new_status == 'closed':
                
                restaurant_name = record['dynamodb']['NewImage']['name']['S']
                new_reason = record['dynamodb']['NewImage']['reason']['S']
                
                subject = f'Restaurant Closing: {restaurant_name}'
                message = f'Restaurant {restaurant_name} is {new_status} due to {new_reason}'
                
                # Sending SNS notification
                send_sns(message, subject)
    
    return "Processing Complete"
