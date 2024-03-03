import os
import json
from datetime import datetime, timedelta
from google.cloud import pubsub_v1

project_id = "csci5410f23serverless"

topic_name = "reservation-notifications"

publisher = pubsub_v1.PublisherClient()

def send_notification(request):
    try:
        request_json = request.get_json()
        reservation_time_str = request_json.get("start_time")
    
        reservation_time = datetime.strptime(reservation_time_str, "%Y-%m-%d %H:%M:%S")
        notification_time = reservation_time - timedelta(minutes=30)
        current_time = datetime.now()

        if current_time >= notification_time:
            message = {
                "message": f"Your reservation at {reservation_time} is approaching. Please be prepared! Looking forward to meeting you!",
                "subject": "Reservation Reminder"
            }
            topic_path = publisher.topic_path(project_id, topic_name)
            future = publisher.publish(topic_path, json.dumps(message).encode("utf-8"))
            future.result()

            return "Notification sent successfully"
        else:
            return "Notification not sent (30-minute window not reached)"
        
    except Exception as e:
        return f"Error: {str(e)}"

