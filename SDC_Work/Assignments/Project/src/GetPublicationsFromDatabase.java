import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GetPublicationsFromDatabase {

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
     * This method retrieves publication details based on a given key
     */
    public static Map<String, String> getPublications(String key) {
        try{
            // Get the credentials
            credentialsFinder();
            Map<String, String> publicationDetails = new HashMap<>();
            Connection connect = null;
            Statement statement = null;
            ResultSet rs = null;
            ResultSet rs2 = null;
            ResultSet rs3 = null;
            // connect tto database.
            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password );
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("use yogish;");
            // getting author names from database
            rs2 = statement.executeQuery("SELECT * FROM AuthorIdentifier WHERE identifier = '"+key+"';");
            StringBuilder sb = new StringBuilder();
            while(rs2.next()){
                sb.append(rs2.getString("author")).append(",");
            }
            if(sb.length() > 0){
                sb.deleteCharAt(sb.length() - 1);
            }
            rs2.close();
            // getting references from the database
            rs3 = statement.executeQuery("SELECT * FROM Referenceidentifier WHERE identifier =  '"+key+"';");
            if(rs3.next()){
                String references = rs3.getString("paperreferences");
                publicationDetails.put("references", references);
            }
            rs3.close();
            // getting publication details from the database
            rs = statement.executeQuery("SELECT * FROM Publications WHERE identifier = '"+key+"';");
            if(rs.next()){
                String check = rs.getString("journal");
                if(check == null){
                    publicationDetails.put("authors", String.valueOf(sb));
                    publicationDetails.put("title", rs.getString("title"));
                    publicationDetails.put("conference", rs.getString("conference"));
                    publicationDetails.put("location", rs.getString("location"));
                    publicationDetails.put("year", rs.getString("pyear"));
                    publicationDetails.put("pages", rs.getString("pages"));
                }
                if(check != null){
                    publicationDetails.put("authors", String.valueOf(sb));
                    publicationDetails.put("title", rs.getString("title"));
                    publicationDetails.put("journal", rs.getString("journal"));
                    publicationDetails.put("pages", rs.getString("pages"));
                    publicationDetails.put("volume", rs.getString("volume"));
                    publicationDetails.put("issue", rs.getString("issue"));
                    publicationDetails.put("month", rs.getString("pmonth"));
                    publicationDetails.put("year", rs.getString("pyear"));
                }

            }
            rs.close();
            statement.close();
            connect.close();
            return publicationDetails;
        }
        catch (Exception e){
            return new HashMap<>();
        }

    }
}
