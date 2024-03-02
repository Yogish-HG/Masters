import json
import boto3

def lambda_handler(event, context):
    # Retrieve values from the event
    # customer_email = event['email']
    # orders = event['orders']

    body = json.loads(event['body'])
    customer_email = body['email']
    orders = body['orders']
    
    
    # DynamoDB table name
    table_name = 'termProjectCustomerTable'

    # Create a DynamoDB resource
    dynamodb = boto3.resource('dynamodb')

    # Connect to the DynamoDB table
    table = dynamodb.Table(table_name)

    # Get data from DynamoDB based on email ID
    response = table.get_item(Key={'Customer_email': customer_email})

    if 'Item' not in response:
        # If no data is found for the given email ID, return an error response
        return {
            'statusCode': 404,
            'body': 'No data found for the provided email ID!'
        }

    # Extract SNS topic ARN from DynamoDB response
    sns_topic_arn = response['Item'].get('sns_topic_arn')

    if not sns_topic_arn:
        # If no SNS topic ARN is found, return an error response
        return {
            'statusCode': 400,
            'body': 'Error: SNS topic ARN not found in the DynamoDB record!'
        }

    # Create an SNS client
    sns = boto3.client('sns')
    order_lines = []

    # Add header
    order_lines.append("Hello\n\nThank you for choosing our services. Below is the invoice for your recent order:\n")
    i = 1
    # Add order items
    for order_item in orders:
        name = order_item.get("name", "")
        quantity = order_item.get("quantity", "")
        metric = order_item.get("metric", "")
        if(name != ""):
            order_lines.append(f"{i}. {name} - {quantity} {metric}/s")
            i = i+1

    # Add total amount
    total_amount = orders[-1].get("total amount", "")
    order_lines.append(f"Your Total bill is {total_amount} CAD\n")
    order_lines.append("Please make the payment at your earliest convenience using the provided details below:\n")
    
    order_lines.append("Account Number: 12345")
    order_lines.append("Bank : Scotia Bank\n")
    
    
    

    # Join the lines to form the final string
    formatted_orders = "\n".join(order_lines)
    # Send a sample email message to the user using the SNS topic
    message = formatted_orders
    subject = 'Your Bill'

    # Publish a message to the SNS topic
    sns.publish(
        TopicArn=sns_topic_arn,
        Message=message,
        Subject=subject
    )

    return {
        'statusCode': 200,
        'body': 'Email sent successfully!'
    }

