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
            # Extracting menu item and its respective description
            menu_item_name = record['dynamodb']['NewImage']['name']['S']
            description = record['dynamodb']['NewImage']['description']['S']
            
            subject = f'Menu Items Changed'
            message = f'Menu Items: {menu_item_name} are changed.\n Description: {description}'
            
            # Send SNS notification
            send_sns(message, subject)
    
    return "Processing Complete"
