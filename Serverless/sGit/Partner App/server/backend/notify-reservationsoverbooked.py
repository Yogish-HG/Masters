import boto3
from boto3.dynamodb.conditions import Key

dynamodb = boto3.resource('dynamodb')
sns = boto3.client('sns')

TOP_MENU_ITEMS_TABLE_NAME = 'TopMenuItems'  # Replace with your DynamoDB table name
SNS_TOPIC_ARN = 'arn:aws:sns:us-east-1:248274840893:reservations-change'  # Replace with your SNS topic ARN

def lambda_handler(event, context):
    try:
        # Check tables overbooking
        overbooked = check_overbooked_tables()

        if overbooked:
            send_notification('Tables Overbooked', 'Tables are being overbooked. Please check.')

        # Get top three menu items
        top_menu_items = get_top_menu_items()

        # Notify every 4 hours
        send_notification('Top Menu Items', f'Top three menu items mostly booked: {", ".join(top_menu_items)}. Notify every 4 hours.')

        return {
            'statusCode': 200,
            'body': 'Notification sent successfully',
        }
    except Exception as e:
        print('Error:', e)
        return {
            'statusCode': 500,
            'body': 'Internal Server Error',
        }

def check_overbooked_tables():
    # Implementing logic to check if tables are overbooked
    # Example: Query a DynamoDB table to get the current reservation count and compare it with the table capacity
    reservation_table = dynamodb.Table('Reservations')
    reservation_count = reservation_table.scan(Select='COUNT')['Count']
    table_capacity = 100  # Replace with the actual table capacity
    return reservation_count >= table_capacity

def get_top_menu_items():
    # Implementing logic to get the top three menu items
    top_menu_items_table = dynamodb.Table(TOP_MENU_ITEMS_TABLE_NAME)
    result = top_menu_items_table.scan()
    top_menu_items = [item['menuItem'] for item in sorted(result['Items'], key=lambda x: x['bookingCount'], reverse=True)[:3]]
    return top_menu_items

def send_notification(subject, message):
    # Send message to SNS topic
    sns.publish(
        TopicArn=SNS_TOPIC_ARN,
        Subject=subject,
        Message=message
    )

