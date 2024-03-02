import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class AddPublicationToDatabase {

    private static String username;
    private static String password;

    // Get the credentials to connect to database
    public static void credentialsFinder(){
        Properties identity = new Properties();
        String propertyFilename = "credentials.prop";

        try {
            InputStream stream = new FileInputStream( propertyFilename );

            identity.load(stream);

            username = identity.getProperty("username");
            password = identity.getProperty("password");
        } catch (Exception e) {
            return;
        }
    }

    /**

     This method adds a new journal publication to the database.
     @param identifier the unique identifier of the publication.
     @param publicationInformation a map containing the publication information, including title, journal, pages, volume, issue, pmonth, and pyear.
     @return true if the operation is successful and false if the publication already exists in the database.
     */
    public static boolean addJournal(String identifier, Map<String, String> publicationInformation) {
        try{
            // Get the credentials
            credentialsFinder();
            Connection connect = null;
            Statement statement = null;
            ResultSet rs = null;
            // connect to the database
            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password );
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("use yogish;");
            // checking if the publication already exists.
            rs = statement.executeQuery("SELECT * FROM Publications WHERE identifier = '"+ identifier +"';");
            // return false if publication already present
            if(rs.next()){
                return false;
            }
            // insert the publication into the database.
            String sql = "INSERT INTO Publications (identifier, title, journal, pages, volume, issue, pmonth, pyear) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pStatement = connect.prepareStatement(sql);

            pStatement.setString(1, identifier);
            pStatement.setString(2, publicationInformation.get("title"));
            pStatement.setString(3, publicationInformation.get("journal"));
            pStatement.setString(4, publicationInformation.get("pages"));
            pStatement.setString(5, publicationInformation.get("volume"));
            pStatement.setString(6, publicationInformation.get("issue"));
            pStatement.setString(7, publicationInformation.get("month"));
            pStatement.setString(8, publicationInformation.get("year"));
            pStatement.executeUpdate();

            String authors = publicationInformation.get("authors");
            String[] authorsSplit = authors.split(",");

            for(String s: authorsSplit){
                String sql2 = "INSERT INTO AuthorIdentifier (identifier, author) VALUES(?, ?)";
                PreparedStatement pStatement2 = connect.prepareStatement(sql2);
                pStatement2.setString(1, identifier);
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


    /**
     This method adds a new conference publication to the database.
     @param identifier the unique identifier of the publication.
     @param publicationInformation a map containing the publication information, including title, conference, pages, location, and year.
     @return true if the operation is successful and false if the publication already exists in the database.
     @throws SQLException if there is an error in the SQL syntax or if there is an error in the connection to the database.
     */

    public static boolean addConference(String identifier, Map<String, String> publicationInformation) {
        try{
            // Get credentials
            credentialsFinder();

            Connection connect = null;
            Statement statement = null;
            ResultSet rs = null;
            // connect to database.
            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password );
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("use yogish;");
            // check whether publication already exists
            rs = statement.executeQuery("SELECT * FROM Publications WHERE identifier = '"+ identifier +"';");
            if(rs.next()){
                return false;
            }
            // insert into the database.
            String sql = "INSERT INTO Publications (identifier, title, conference, pages, location, pyear) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pStatement = connect.prepareStatement(sql);

            pStatement.setString(1, identifier);
            pStatement.setString(2, publicationInformation.get("title"));
            pStatement.setString(3, publicationInformation.get("conference"));
            pStatement.setString(4, publicationInformation.get("pages"));
            pStatement.setString(5, publicationInformation.get("location"));
            pStatement.setString(6, publicationInformation.get("year"));

            pStatement.executeUpdate();
            String authors = publicationInformation.get("authors");
            String[] authorsSplit = authors.split(",");

            for(String s: authorsSplit){
                String sql2 = "INSERT INTO AuthorIdentifier (identifier, author) VALUES(?, ?)";
                PreparedStatement pStatement2 = connect.prepareStatement(sql2);
                pStatement2.setString(1, identifier);
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
