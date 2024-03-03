import boto3
import os
import json
import io
import datetime

def setTabsFor(productName):
    
    # Determine the required number of tabs between Item Name and Quantity based on the item name's length.
    
    nameLength = len(productName)
    
    if nameLength < 20:
        tabs='\t\t\t'
    elif 20 <= nameLength <= 37:
        tabs = '\t\t'
    else:
        tabs = '\t'
    
    return tabs

def lambda_handler(event, context):
    
    # Retrieve the topic ARN and the region where the lambda function is running from the environment variables.

    TOPIC_ARN = os.environ['topicARN']
    FUNCTION_REGION = os.environ['AWS_REGION']

    # Extract the topic region from the topic ARN.
    
    arnParts = TOPIC_ARN.split(':')
    TOPIC_REGION = arnParts[3]

    # Get the database connection information from the Systems Manager Parameter Store.
    
    # Create an SSM client.
    
    ssmClient = boto3.client('ssm', region_name=FUNCTION_REGION)

    # Retrieve the database URL and credentials.
    
    parm = ssmClient.get_parameter(Name='/cafe/dbUrl')
    dbUrl = parm['Parameter']['Value']

    parm = ssmClient.get_parameter(Name='/cafe/dbName')
    dbName = parm['Parameter']['Value']

    parm = ssmClient.get_parameter(Name='/cafe/dbUser')
    dbUser = parm['Parameter']['Value']

    parm = ssmClient.get_parameter(Name='/cafe/dbPassword')
    dbPassword = parm['Parameter']['Value']

    # Create a lambda client and invoke the lambda function to extract the daily sales analysis report data from the database.

    lambdaClient = boto3.client('lambda', region_name=FUNCTION_REGION)
    
    dbParameters = {"dbUrl": dbUrl, "dbName": dbName, "dbUser": dbUser, "dbPassword": dbPassword}
    response = lambdaClient.invoke(FunctionName = 'salesAnalysisReportDataExtractor', InvocationType = 'RequestResponse', Payload = json.dumps(dbParameters))

    # Convert the response payload from bytes to string, then to a Python dictionary in order to retrieve the data in the body.
    
    reportDataBytes = response['Payload'].read()
    reportDataString = str(reportDataBytes, encoding='utf-8')
    reportData = json.loads(reportDataString)
    if "body" in reportData:
        reportDataBody = reportData["body"]
    else:
        print(reportData)
        raise Exception('No body in returned data. Check the error in the cloudwatch logs.')

    # Create an SNS client, and format and publish a message containing the sales analysis report based on the extracted report data.

    snsClient = boto3.client('sns', region_name=TOPIC_REGION)
    
    # Create the message.

    # Write the report header first.
    
    message = io.StringIO()
    message.write('Sales Analysis Report'.center(80))
    message.write('\n')

    today = 'Date: ' + str(datetime.datetime.now().strftime('%Y-%m-%d'))
    message.write(today.center(80))
    message.write('\n')

    if (len(reportDataBody) > 0):

        previousProductGroupNumber = -1
        
        # Format and write a line for each item row in the report data.
        
        for productRow in reportDataBody:
            
            # Check for a product group break.
            
            if productRow['product_group_number'] != previousProductGroupNumber:
                
               # Write the product group header.
               
                message.write('\n')
                message.write('Product Group: ' + productRow['product_group_name'])
                message.write('\n\n')
                message.write('Item Name'.center(40) + '\t\t\t' + 'Quantity' + '\n')
                message.write('*********'.center(40) + '\t\t\t' + '********' + '\n')
                
                previousProductGroupNumber = productRow['product_group_number']
        
            # Write the item line.
            
            productName = productRow['product_name']
            tabs = setTabsFor(productName)
                
            itemName = productName.center(40)
            quantity = str(productRow['quantity']).center(5)
            message.write(itemName + tabs + quantity + '\n')

    else:
        
        # Write a message to indicate that there is no report data.
        
        message.write('\n')
        message.write('There were no orders today.'.center(80))

    # Publish the message to the topic.
    
    response = snsClient.publish(
        TopicArn = TOPIC_ARN,
        Subject = 'Daily Sales Analysis Report',
        Message = message.getvalue()    
    )

    # Return a successful function execution message.
    
    return {
        'statusCode': 200,
        'body': json.dumps('Sale Analysis Report sent.')
    }
