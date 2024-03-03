import boto3

topic_arn = "arn:aws:sns:us-east-1:248274840893:restaurant-change"

def send_sns(message, subject):
    try:
        client = boto3.client("sns")
        result = client.publish(TopicArn=topic_arn, Message=message, Subject=subject)
        if result['ResponseMetadata']['HTTPStatusCode'] == 200:
            print("Notification sent successfully..!!!")
            return True
        else:
            print("Failed to send notification. HTTPStatusCode: ", result['ResponseMetadata']['HTTPStatusCode'])
            return False
    except Exception as e:
        print("Error occurred while publishing notifications. Error: ", e)
        return False

def lambda_handler(event, context):
    subject = "Processes Completion Notification"
    message = "Your message content goes here."  # Define your message content

    SNSResult = send_sns(message, subject)
    
    if SNSResult:
        print("Notification Sent..")
        return {
            "statusCode": 200,
            "body": "Notification sent successfully."
        }
    else:
        print("Failed to send notification.")
        return {
            "statusCode": 500,
            "body": "Failed to send notification."
        }
