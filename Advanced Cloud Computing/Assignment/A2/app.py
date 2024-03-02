from flask import Flask, request, jsonify
import boto3
import re
import requests
from botocore.exceptions import NoCredentialsError


app = Flask(__name__)
bucketName = "a2yogish"
fileName = "mydata.txt"
region = "us-east-1"

s3 = boto3.resource('s3', aws_access_key_id="ASIAQOJQCGXLH2AX4VFY", aws_secret_access_key="Yy85z8XET9NDOyWlQfYu+VjkroAqDHHJb2yt+CGM", region_name=region,
                    aws_session_token="FwoGZXIvYXdzECoaDLVDnRFUWFQ7e8tkEyLTAfVvP+fFBBznvueEoi2SATnBVXC43iMz/FECbJgtRGuAmymvT7ALEp9eSb6FYp+ptoR8BHx4wV7NNPfvRCpoEu5A94EFXQSypCBTeWHd0/ipwiOnE3FDoYZv2frCcY3YzzCLi4RjiNoXzmlZ5AgJPsaZYnT86XdIAu7KZ4cfI5aGVBtt5FDBq1XEj+djLTdi2w5t7diC08B/PpxhyxDIYNge/ZdedXkJaQXHn5rfrfS9Y2wFCRvx6CCcRf6TuWw/U9SiE8u/Mn/BgFZFKTh0NDv6vaIol9elqQYyLWEBCEgvGJgV0tPkIJZkVB9CRYMoHyZRshNm3yrTak7UM2bYYnk/lq2SnyYXtA==")

@app.route('/store-data', methods=['POST'])
def create_item():
    data = request.json
    dataStr = data.get("data")
    s3.Object(bucketName,fileName).put(Body=dataStr)
    file_url = f"https://s3.amazonaws.com/{bucketName}/{fileName}"
    print(file_url)
    return jsonify({'s3uri': file_url}), 200

@app.route('/append-data', methods =['POST'])
def append_item():
    try:
        data = request.json
        dataStr = data.get("data")
        

        # Retrieve existing data from S3
        existing_data = s3.Object(bucketName, fileName).get()['Body'].read().decode('utf-8')

        # Append new data
        updated_data = existing_data + dataStr

        # Update the file on S3
        s3.Object(bucketName, fileName).put(Body=updated_data)

        return jsonify({'status': 'success'}), 200

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)}), 500

@app.route('/search-data', methods =['POST'])
def search_item():
    try:
        data = request.json
        regex_pattern = data.get('regex')
        print(regex_pattern)
        

        # Retrieve contents of the file from S3
        file_contents = s3.Object(bucketName, fileName).get()['Body'].read().decode('utf-8')

        # Search for the first line that matches the regex
        matched_line = ""
        for line in file_contents.split('\n'):
            if re.search(regex_pattern, line):
                matched_line = line
                data2 = {'found': True, 'result': matched_line}
                return jsonify(data2), 200
        data2 = {'found': False, 
                        'result': ""
                }
        return jsonify(data2), 200

    except Exception as e:
        return jsonify({'status': False, 'message': str(e)}), 500
    
@app.route('/delete-file', methods=['POST'])
def delete_file():
    try:
        data = request.json
        file_url = data.get('s3uri')

        # Extract file name from the URL
        file_name = re.search(r'https://.+/(.+)', file_url).group(1)

        bucket_name = "a2yogish"

        # Delete the file from S3
        s3.Object(bucket_name, file_name).delete()

        return jsonify({'status': 'success'}), 200

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)}), 500

if __name__ == '__main__':
    app.run(host = "0.0.0.0", port = 80)   
