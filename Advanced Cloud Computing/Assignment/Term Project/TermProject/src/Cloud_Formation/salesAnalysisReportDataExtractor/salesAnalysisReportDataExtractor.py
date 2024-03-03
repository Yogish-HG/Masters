import boto3
import package.pymysql as pymysql
import sys

def lambda_handler(event, context):

    # Retrieve the database connection information from the event input parameter.

    dbUrl = event['dbUrl']
    dbName = event['dbName']
    dbUser = event['dbUser']
    dbPassword = event['dbPassword']

    # Establish a connection to the Mop & Pop database, and set the cursor to return results as a Python dictionary.
    
    try:
        conn = pymysql.connect(dbUrl, user=dbUser, passwd=dbPassword, db=dbName, cursorclass=pymysql.cursors.DictCursor)
        
    except pymysql.Error as e:
        print('ERROR: Failed to connect to the Mom & Pop database.')
        print('Error Details: %d %s' % (e.args[0], e.args[1]))
        sys.exit()
    
    # Execute the query to generate the daily sales analysis result set.
    
    with conn.cursor() as cur:
        cur.execute("SELECT  c.product_group_number, c.product_group_name, a.product_id, b.product_name, CAST(sum(a.quantity) AS int) as quantity FROM order_item a, product b, product_group c WHERE b.id = a.product_id AND c.product_group_number = b.product_group GROUP BY c.product_group_number, a.product_id")
        result = cur.fetchall()

    # Close the connection.
    
    conn.close()

    # Return the result set.
    
    return {'statusCode': 200, 'body': result}
