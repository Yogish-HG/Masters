from flask import Flask, request, jsonify
import csv
import requests
import os
from google.cloud import storage


app = Flask(__name__)

@app.route('/store-file', methods=["POST"])
def store_file():
    try:
        data = request.get_json()

        if not data or "file" not in data or "data" not in data or data.get("file") is None:
            response = {
            "file": None,
            "error": "Invalid JSON input."
        }
            return jsonify(response)

        file_name = data["file"]
        file_data = data["data"]

        storage_directory = '/vol_dir'

        if not os.path.exists(storage_directory):
            os.makedirs(storage_directory)

        file_path = os.path.join(storage_directory, file_name)

        with open(file_path, 'w') as file:
            file.write(file_data)

        response = {
            "file": file_name,
            "message": "Success."
        }

    except ValueError as ve:
        response = {
            "file": None,
            "error": str(ve)
        }
    except Exception as e:
        response = {
            "file": file_name,
            "error": "Error while storing the file to the storage."
        }

    return jsonify(response)

@app.route('/get-temperature', methods=['POST'])
def create_item():
    recJson = request.get_json()
    if(recJson.get("file") == None or not recJson):
        data = {
            "file": None, 
            "error": "Invalid JSON input."
        }
        return jsonify(data)
    
    filePath = "./data/"+recJson.get("file")
    if not os.path.exists(filePath):
        data = {
        "file": str(recJson.get("file")), 
        "error": "File not found."  
        }
        return jsonify(data)
    
    try:
            headers = {'Content-Type': 'application/json'}
            response = requests.post('http://localhost:5001/getTemp', data=recJson, headers=headers)
            return response.json()
    except Exception as e:
            response = {
                "file": recJson.get("file"),
                "error": "Error while processing the file."
            }
    return jsonify(response)


    # if(recJson.get("key") == "location"):
    #     nameToFind = recJson.get("name")
    #     lat = 0.0
    #     long = 0.0
    #     with open(filePath, mode='r') as file:
    #         csv_reader = csv.reader(file)
    #         for row in csv_reader:
    #             if(len(row) != 4):
    #                 data = {
    #                 "file": str(recJson.get("file")), 
    #                 "error": "Input file not in CSV format." 
    #                 } 
    #                 return jsonify(data)
    #             name, latitude, longitude, temperature = row
    #             if nameToFind == name:
    #                 lat = latitude
    #                 long = longitude
    #     data = { 
    #         "file": str(nameToFind),
    #         "latitude": float(lat),
    #         "longitude": float(long),
    #         }
    #     return jsonify(data)
    # elif(recJson.get("key") == "temperature"):
    #     response = requests.post(url = "http://C2:5001/calculatetemperature", json = recJson)
    #     return response.json()
    # else:
    #         data = {
    #         "file": "null", 
    #         "error": "Invalid JSON input."
    #     }
    # return jsonify(data)
        

if __name__ == '__main__':
    app.run(host = "0.0.0.0", port = 6000)   