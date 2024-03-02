import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class AddReferencesToDatabase {

    private static String username;
    private static String password;

    // Finds credentials to connect to database
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
    This method adds references to the reference identifier in the database. If the identifier already exists, it performs a
    union of the set of already present references with the given set of references. If the identifier does not exist, it
    adds the given references to the database.
    @param identifier the identifier for the reference.
    @param referencesSet the set of references to add.
    @return true if the operation is successful and false otherwise.
     */
    public static boolean AddReferences(String identifier, Set<String> referencesSet) {
        try{
            // Get the credentials and connect to the database
            credentialsFinder();
            Connection connect = null;
            Statement statement = null;
            ResultSet rs = null;
            ResultSet rs2 = null;
            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password );
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            // Check whether the identifer already exists in the database.
            statement.execute("use yogish;");
            rs2 = statement.executeQuery("SELECT * FROM Publications WHERE identifier = '"+ identifier +"';");
            if(!rs2.next()){
                return false;
            }
            rs = statement.executeQuery("SELECT * FROM Referenceidentifier WHERE identifier = '"+ identifier +"';");

            // If exists, perform a union of set of already present reference with given reference.
            if(rs.next()){
                // Get references of the identifier from the database.
                String presentString = rs.getString("paperreferences");
                Set<String> presentset = new HashSet<>(Arrays.asList(presentString.split(",")));
                Set<String> updatedSet = new HashSet<>(referencesSet);
                // Union of two sets
                presentset.addAll(updatedSet);

                // converting the updated set to a string
                ArrayList<String> refList = new ArrayList<>(presentset);
                String myString = String.join(",", refList);
                // Updating references in the database.
                String sql = "UPDATE Referenceidentifier SET paperreferences = ? WHERE identifier = ?;";
                PreparedStatement pStatement = connect.prepareStatement(sql);
                pStatement.setString(1, myString);
                pStatement.setString(2, identifier);
                pStatement.executeUpdate();
                pStatement.close();
                statement.close();
                connect.close();
                return true;
            }
            // If identifier does not exist, adding references to the database.
            ArrayList<String> refList = new ArrayList<>(referencesSet);
            String myString = String.join(",", refList);
            String sql = "INSERT INTO Referenceidentifier (identifier,paperreferences) VALUES (?,?)";
            PreparedStatement pStatement = connect.prepareStatement(sql);

            pStatement.setString(1, identifier);
            pStatement.setString(2, myString);
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
