import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GetAuthorCitations {

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

     This method returns the number of times an author's publications have been cited by other publications.
     It takes in the name of the author as a parameter.
     The method then queries the database to find all the references to the author's publications and counts the number of times they appear.
     The count is returned as an integer value.
     If an exception occurs, the method returns 0.
     */
    public static int authorCitations(String authorName) {
        try {
            // Get the credentials
            credentialsFinder();
            Map<String, String> publicationDetails = new HashMap<>();
            Connection connect = null;
            Statement statement = null;
            ResultSet rs = null;
            ResultSet rs2 = null;
            int count = 0;
            // connect tto database.
            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password);
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("use yogish;");
            // getting count from database
            rs = statement.executeQuery("SELECT identifier\n" +
                    "    FROM AuthorIdentifier\n" +
                    "    WHERE author = '"+authorName+"';");
            ArrayList<String> identifierrs = new ArrayList<>();
            while(rs.next()){
                // parsing it into integer and returning the count.
                identifierrs.add(rs.getString("identifier"));
            }
            rs.close();
            rs2 = statement.executeQuery("select paperreferences from Referenceidentifier;");
            while(rs2.next()){
                String data = rs2.getString("paperreferences");
                for(String s: identifierrs){
                    if(data.contains(s)){
                        count++;
                    }
                }
            }
            rs2.close();
            statement.close();
            connect.close();
            return count;
        } catch (Exception e) {
            return 0;
        }
    }
}

