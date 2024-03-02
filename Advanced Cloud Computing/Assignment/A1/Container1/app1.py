from flask import Flask, request, jsonify
import csv
import requests
import os


app = Flask(__name__)

@app.route('/user-info', methods=['POST'])
def create_item():
    recJson = request.get_json()
    if(recJson.get("file") == None):
        data = {
            "file": "null", 
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
    if(recJson.get("key") == "location"):
        nameToFind = recJson.get("name")
        lat = 0.0
        long = 0.0
        with open(filePath, mode='r') as file:
            csv_reader = csv.reader(file)
            for row in csv_reader:
                if(len(row) != 4):
                    data = {
                    "file": str(recJson.get("file")), 
                    "error": "Input file not in CSV format." 
                    } 
                    return jsonify(data)
                name, latitude, longitude, temperature = row
                if nameToFind == name:
                    lat = latitude
                    long = longitude
        data = { 
            "file": str(nameToFind),
            "latitude": float(lat),
            "longitude": float(long),
            }
        return jsonify(data)
    elif(recJson.get("key") == "temperature"):
        response = requests.post(url = "http://C2:5001/calculatetemperature", json = recJson)
        return response.json()
    else:
            data = {
            "file": "null", 
            "error": "Invalid JSON input."
        }
    return jsonify(data)
        

if __name__ == '__main__':
    app.run(host = "0.0.0.0", port = 6000)   