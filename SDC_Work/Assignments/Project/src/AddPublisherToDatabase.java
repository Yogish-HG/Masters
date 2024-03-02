import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

public class AddPublisherToDatabase {

    private static String username;
    private static String password;

    // Get the credentials to connect to database
    public static void credentialsFinder() {
        Properties identity = new Properties();
        String propertyFilename = "credentials.prop";

        try {
            InputStream stream = new FileInputStream(propertyFilename);

            identity.load(stream);

            username = identity.getProperty("username");
            password = identity.getProperty("password");
        } catch (Exception e) {
            return;
        }
    }

    /**

     Adds a new publisher to the database with the given identifier and information.
     @param identifier the identifier of the publisher to be added
     @param publisherInformation a Map containing the contact name, contact email, and location of the publisher
     @return true if the publisher was added successfully, false if the publisher already exists in the database
     */
    public static boolean addPublisher(String identifier, Map<String, String> publisherInformation) {
        try{
            // get the credentials
            credentialsFinder();

            Connection connect = null;
            Statement statement = null;
            ResultSet rs = null;
            // connect to database
            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password );
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("use yogish;");
            // checking whether the publisher already exists
            rs = statement.executeQuery("SELECT * FROM Publisher WHERE publisheridentifier = '"+ identifier +"';");
            if(rs.next()){
                return false;
            }
            // inserting the data into the database
            String sql = "INSERT INTO Publisher (publisheridentifier, contactname, contactemail, location) VALUES (?, ?, ?, ?)";
            PreparedStatement pStatement = connect.prepareStatement(sql);

            pStatement.setString(1, identifier);
            pStatement.setString(2, publisherInformation.get("contact_name"));
            pStatement.setString(3, publisherInformation.get("contact_email"));
            pStatement.setString(4, publisherInformation.get("location"));
            pStatement.executeUpdate();

            pStatement.close();
            statement.close();
            connect.close();
            return true;
        }
        catch (Exception e){
            return false;
        }

    }
}
