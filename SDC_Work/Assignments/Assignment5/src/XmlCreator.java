import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class XmlCreator {


    public static void CreateDocument(String start, String end, String outputFile, String username, String password) throws SQLException, ParserConfigurationException, TransformerException {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        ResultSet resultSetStore1 = null;
        ResultSet resultSetStore2 = null;

        connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password );
        statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();


        //Activity Summary element
        Element rootElement = doc.createElement("Activity_Summary");
        doc.appendChild(rootElement);
        Element objectElement = doc.createElement("timespan");
        rootElement.appendChild(objectElement);

        //start date element
        Element startDate = doc.createElement("start_date");
        objectElement.appendChild(startDate);
        startDate.appendChild(doc.createTextNode(start));

        //end date element
        Element endDate = doc.createElement("end_date");
        objectElement.appendChild(endDate);
        endDate.appendChild(doc.createTextNode(end));

        //customer_list element
        Element customerListElement = doc.createElement("customer_list");
        rootElement.appendChild(customerListElement);

        statement.execute("use csci3901;");

        // getting the customer data by interacting with the database
        resultSet = statement.executeQuery("SELECT c.customer_id, CONCAT(c.first_name,' ', c.last_name) as customer_name, c.street as street_address, c.city, c.state, c.zip_code, \n" +
                "sum((oi.list_price*oi.quantity) - ((oi.quantity*oi.list_price) * oi.discount)) as order_value, sum(oi.quantity) as bicycles_purchased\n" +
                "FROM customers c\n" +
                "JOIN orders o ON c.customer_id = o.customer_id\n" +
                "JOIN order_items oi ON o.order_id = oi.order_id\n" +
                "JOIN products p ON oi.product_id = p.product_id\n" +
                "\twhere o.order_date BETWEEN '"+ start+"' AND '" + end +"' AND\n" +
                "\tc.customer_id NOT IN (\n" +
                "\t  SELECT customer_id\n" +
                "\t  FROM orders\n" +
                "\t  GROUP BY customer_id, order_date\n" +
                "\t  HAVING order_date > (SELECT MIN(order_date) FROM orders LIMIT 1) AND order_date < '" + start + "'\n" +
                "\n" +
                "\t  )\n" +
                "\t  \n" +
                "GROUP BY c.customer_id;");

        // looping through the result set to add tags to xml document
        while (resultSet.next()) {

            //customer element
            Element customerElement = doc.createElement("customer");
            customerListElement.appendChild(customerElement);

            // Customer name element
            Element customerNameElement = doc.createElement("customer_name");
            customerNameElement.appendChild(doc.createTextNode(resultSet.getString("customer_name")));
            customerElement.appendChild(customerNameElement);

            // Address element
            Element addressElement = doc.createElement("address");
            customerElement.appendChild(addressElement);

            // Street address element
            Element streetAddressElement = doc.createElement("street_address");
            streetAddressElement.appendChild(doc.createTextNode(resultSet.getString("street_address")));
            addressElement.appendChild(streetAddressElement);

            // City element
            Element cityElement = doc.createElement("city");
            cityElement.appendChild(doc.createTextNode(resultSet.getString("city")));
            addressElement.appendChild(cityElement);

            // State element
            Element stateElement = doc.createElement("state");
            stateElement.appendChild(doc.createTextNode(resultSet.getString("state")));
            addressElement.appendChild(stateElement);

            // Zip code element
            Element zipCodeElement = doc.createElement("zip_code");
            zipCodeElement.appendChild(doc.createTextNode(resultSet.getString("zip_code")));
            addressElement.appendChild(zipCodeElement);

            // Order value element
            Element orderValueElement = doc.createElement("order_value");
            orderValueElement.appendChild(doc.createTextNode(resultSet.getString("order_value")));
            customerElement.appendChild(orderValueElement);

            // Bicycles purchased element
            Element bicyclesPurchasedElement = doc.createElement("bicycles_purchased");
            bicyclesPurchasedElement.appendChild(doc.createTextNode(resultSet.getString("bicycles_purchased")));
            customerElement.appendChild(bicyclesPurchasedElement);
        }

        // product list element
        Element productListElement = doc.createElement("product_list");
        rootElement.appendChild(productListElement);


        // getting the product data by interacting with the database
        resultSet2 = statement.executeQuery("SELECT p.product_name, b.brand_name as brand, group_concat(distinct(c.category_name)) as category, s.store_name, sum(oi.quantity) as units_sold\n" +
                "FROM products p\n" +
                "JOIN categories c ON p.category_id = c.category_id\n" +
                "JOIN brands b ON p.brand_id = b.brand_id\n" +
                "JOIN order_items oi ON p.product_id = oi.product_id\n" +
                "JOIN orders o ON oi.order_id = o.order_id\n" +
                "JOIN stores s ON o.store_id = s.store_id\n" +
                "WHERE o.order_date BETWEEN '"+start+"' AND '"+end+"'\n" +
                "and p.product_id NOT IN (\n" +
                "\tSELECT p.product_id \n" +
                "    FROM products p\n" +
                "    JOIN order_items oi ON p.product_id = oi.product_id\n" +
                "    JOIN orders o ON oi.order_id = o.order_id\n" +
                "    GROUP BY p.product_id, o.order_date\n" +
                "    HAVING o.order_date > (SELECT MIN(order_date) FROM orders LIMIT 1) AND o.order_date < '"+start+"')\n" +
                "GROUP BY p.product_name, s.store_id;");

        while(resultSet2.next()){

            //new product element
            Element newProduct = doc.createElement("new_product");
            productListElement.appendChild(newProduct);

            //product name element
            Element productName = doc.createElement("product_name");
            productName.appendChild(doc.createTextNode(resultSet2.getString("product_name")));
            newProduct.appendChild(productName);

            // Storing product name to check whether the next row has the same product name
            String  pName = resultSet2.getString("product_name");

            //brand element
            Element brand = doc.createElement("brand");
            brand.appendChild(doc.createTextNode(resultSet2.getString("brand")));
            newProduct.appendChild(brand);

            //category element
            String categories = resultSet2.getString("category");
            String[] catSplit = categories.split(",");
            ArrayList<String> categoryList = new ArrayList<>(Arrays.asList(catSplit));
            for(String v: catSplit) {
                Element category = doc.createElement("category");
                category.appendChild(doc.createTextNode(v));
                newProduct.appendChild(category);
            }

            //looping through the store sales to create multiple tags for multiple sales of the same store
            while(Objects.equals(resultSet2.getString("product_name"), pName)) {

                // upadting the category tag
                String categories2 = resultSet2.getString("category");
                String[] catSplit2 = categories2.split(",");

                if(catSplit2.length > catSplit.length){
                    catSplit = catSplit2;
                    for(String s : catSplit){
                        if(!categoryList.contains(s)){
                           categoryList.add(s);
                            Element category = doc.createElement("category");
                            category.appendChild(doc.createTextNode(s));
                            newProduct.appendChild(category);
                        }
                    }

                }

                // store sales element
                Element storesSales = doc.createElement("store_sales");
                newProduct.appendChild(storesSales);

                //store name element
                Element storeName = doc.createElement("store_name");
                storeName.appendChild(doc.createTextNode(resultSet2.getString("store_name")));
                storesSales.appendChild(storeName);

                //units sold element
                Element unitsSold = doc.createElement("units_sold");
                unitsSold.appendChild(doc.createTextNode(resultSet2.getString("units_sold")));
                storesSales.appendChild(unitsSold);

                if(!resultSet2.next()){
                    break;
                }

            }
            // going to previous row as the next row has different product name
            resultSet2.previous();
        }


        // getting the number of customers served data by interacting with the database
        resultSetStore1 = statement.executeQuery("SELECT count(distinct c.customer_id) as customers_served\n" +
                "from stores s\n" +
                "JOIN orders o ON s.store_id = o.store_id\n" +
                "JOIN customers c ON o.customer_id = c.customer_id\n" +
                "WHERE o.order_date BETWEEN '" + start + "' AND '"+ end +"' \n" +
                "GROUP BY s.store_id;");

        ArrayList<Integer> customerList = new ArrayList<>();

        while(resultSetStore1.next()){
            customerList.add(Integer.valueOf(resultSetStore1.getString("customers_served")));
        }

        // getting the store data by interacting with the database
        resultSetStore2 = statement.executeQuery("SELECT s.store_name, s.city as store_city, count(DISTINCT(st.staff_id)) as employee_count," +
                "CONCAT(c.first_name,' ', c.last_name) as customer_name,\n" +
                "sum((oi.list_price*oi.quantity) - ((oi.quantity*oi.list_price) * oi.discount)) as customer_sales_value\n" +
                "from stores s\n" +
                "JOIN orders o ON s.store_id = o.store_id\n" +
                "JOIN staffs st ON s.store_id = st.store_id\n" +
                "JOIN order_items oi ON o.order_id = oi.order_id\n" +
                "JOIN customers c ON o.customer_id = c.customer_id\n" +
                "WHERE o.order_date BETWEEN '" + start + "' AND '"+ end +"' \n" +
                "GROUP BY s.store_id, c.customer_id;");

        Element storeList = doc.createElement("store_list");
        rootElement.appendChild(storeList);

        int count = 0;

        while(resultSetStore2.next()){

            // store element
            Element store = doc.createElement("store");
            storeList.appendChild(store);

            // store name element
            Element storeName = doc.createElement("store_name");
            storeName.appendChild(doc.createTextNode(resultSetStore2.getString("store_name")));
            store.appendChild(storeName);

            // store city element
            Element storeCity = doc.createElement("store_city");
            storeCity.appendChild(doc.createTextNode(resultSetStore2.getString("store_city")));
            store.appendChild(storeCity);

            // employee count element
            Element employeeCount = doc.createElement("employee_count");
            employeeCount.appendChild(doc.createTextNode(resultSetStore2.getString("employee_count")));
            store.appendChild(employeeCount);

            // customers served element
            Element customersServed = doc.createElement("customers_served");
            customersServed.appendChild(doc.createTextNode(String.valueOf(customerList.get(count))));
            store.appendChild(customersServed);

            for(int i=0; i< customerList.get(count); i++){
                Element customerSales = doc.createElement("customer_sales");
                store.appendChild(customerSales);

                Element customer_name = doc.createElement("customer_name");
                customer_name.appendChild(doc.createTextNode(resultSetStore2.getString("customer_name")));
                customerSales.appendChild(customer_name);

                Element customer_sales_value = doc.createElement("customer_sales_value");
                customer_sales_value.appendChild(doc.createTextNode(resultSetStore2.getString("customer_sales_value")));
                customerSales.appendChild(customer_sales_value);

                if(i != (customerList.get(count) - 1)){
                    resultSetStore2.next();
                }
            }
            count++;
        }



        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(outputFile));
        transformer.transform(source, result);

        resultSet.close();
        resultSet2.close();
        resultSetStore1.close();
        resultSetStore2.close();
        statement.close();
        connect.close();
    }
}
