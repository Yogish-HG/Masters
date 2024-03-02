import json
import hashlib
import requests

def lambda_handler(event, context):

    value = event['value']
    value_to_hash = event['value'].encode('utf-8')
    
    hashed_value = hashlib.sha256(value_to_hash).hexdigest()
  
    response = {
        "banner": "B00928029",
        "result": hashed_value,
        "arn": "arn:aws:lambda:us-east-1:030702450134:function:MD5",
        "action": "md5",
        "value": value
    }
 
    course_uri = event['course_uri']
   
    post_response = requests.post(course_uri, json=response)
    
    return "response sent"