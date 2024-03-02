import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

public class AddAreaToDatabase {

    private static String username;
    private static String password;

    // Get the credentials to connect to the database.
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
     * This method populates the research areas hierarchy by inserting a new record for a research area and its parent areas.
     *
     * @param researchArea the research area to insert.
     * @param parentArea   the set of parent areas for the research area.
     * @return true if the operation is successful and false if the research area already exists in the hierarchy.
     */
    public static boolean populateResearchAreas(String researchArea, Set<String> parentArea) {
        try {
            // Call the credentialsFinder method to get the database username and password.
            credentialsFinder();

            Connection connect = null;
            Statement statement = null;
            ResultSet rs = null;
            // Connect to the database.
            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("use yogish;");
            // Check if the research area already exists in the hierarchy.
            rs = statement.executeQuery("SELECT * FROM AreaHierarchy WHERE area = '" + researchArea + "';");
            // Return false if the research area already exists in the hierarchy.
            if (rs.next()) {
                return false;
            }
            // Convert the set of parent areas to an ArrayList and join them with a comma separator.
            ArrayList<String> areaList = new ArrayList<>(parentArea);
            String myString = String.join(", ", areaList);
            // Prepare the SQL statement to insert the new record.
            String sql = "INSERT INTO AreaHierarchy (area, parentarea) VALUES (?, ?)";
            PreparedStatement pStatement = connect.prepareStatement(sql);

            pStatement.setString(1, researchArea);
            pStatement.setString(2, myString);
            pStatement.executeUpdate();

            pStatement.close();
            statement.close();
            connect.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }
}
