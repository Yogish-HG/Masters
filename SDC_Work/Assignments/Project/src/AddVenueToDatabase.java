import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class AddVenueToDatabase {

    private static String username;
    private static String password;

    // Get the credentials for the database.
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

     This method populates the venue information into the database.
     @param vName the name of the venue
     @param vInformation a Map containing the publisher, editor, editor contact, location, and conference year of the venue
     @param rAreas a Set of research areas associated with the venue
     @return true if the information was successfully added to the database, false if the venue already exists in the database
     */
    public static boolean populateVenueInformation(String vName, Map<String , String> vInformation, Set<String> rAreas) {
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
            // checking if venue already exists
            rs = statement.executeQuery("SELECT * FROM VenueInformation WHERE venuename = '"+ vName +"';");
            if(rs.next()){
                return false;
            }
            // Inserting venue information into the database
            String sql = "INSERT INTO VenueInformation (venuename, publisher, editor, editorcontact, location, conferenceyear) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pStatement = connect.prepareStatement(sql);

            pStatement.setString(1, vName);
            pStatement.setString(2, vInformation.get("publisher"));
            pStatement.setString(3, vInformation.get("editor"));
            pStatement.setString(4, vInformation.get("editor_contact"));
            pStatement.setString(5, vInformation.get("location"));
            pStatement.setString(6, vInformation.get("conference_year"));

            pStatement.executeUpdate();

            ArrayList<String> areaList = new ArrayList<>(rAreas);
            String myString = String.join(", ", areaList);
            String[] stringSplit = myString.split(",");

            for(String s: stringSplit){
                String sql2 = "INSERT INTO VenueResearchAreas (venuename, area) VALUES(?, ?)";
                PreparedStatement pStatement2 = connect.prepareStatement(sql2);
                pStatement2.setString(1, vName);
                pStatement2.setString(2, s);
                pStatement2.executeUpdate();
            }

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
