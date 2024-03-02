from flask import Flask, request, jsonify
import csv

app = Flask(__name__)

@app.route('/calculatetemperature', methods=['POST'])
def create_item():
    recJson = request.json
    nameToFind = recJson.get("name")
    filePath = "./data/"+recJson.get("file")
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
                temp = temperature
    data = {
        "file": str(recJson.get("file")),
        "temperature": temp
        }
    response = jsonify(data)
    return response


if __name__ == '__main__':
    app.run(host = "0.0.0.0", port=5001)