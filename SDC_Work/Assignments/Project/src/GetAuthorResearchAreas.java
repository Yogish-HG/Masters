import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class GetAuthorResearchAreas {

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

     Returns a set of research areas for a given author if they have published papers
     having threshold values in that area.
     @param authorName the name of the author to search for
     @param threshold the minimum number of papers required to be considered in a research area
     @return a set of research areas meeting the threshold for the given author
     */
    public static Set<String> AuthorRAs(String authorName, int threshold) {
        try {
            // Get the credentials
            credentialsFinder();
            Map<String, String> publicationDetails = new HashMap<>();
            Connection connect = null;
            Statement statement = null;
            ResultSet rs = null;
            Set<String> areaSet = new HashSet<>();
            // connect tto database.
            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password);
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("use yogish;");
            // getting research areas from database
            rs = statement.executeQuery("SELECT ah1.area as paperarea, ah2.area AS parent_area, COUNT(DISTINCT p.identifier) AS paper_count\n" +
                    "FROM AuthorIdentifier ai\n" +
                    "JOIN Publications p ON ai.identifier = p.identifier\n" +
                    "JOIN VenueResearchAreas vra ON p.journal = vra.venuename OR p.conference = vra.venuename\n" +
                    "JOIN AreaHierarchy ah1 ON vra.area = ah1.area\n" +
                    "LEFT JOIN AreaHierarchy ah2 ON ah1.parentarea = ah2.area\n" +
                    "WHERE ai.author = '"+ authorName+"'\n" +
                    "GROUP BY ah1.area\n" +
                    "HAVING paper_count >= '"+ threshold+"' OR\n" +
                    "    (ah2.area IS NOT NULL AND COUNT(DISTINCT p.identifier) >= '"+ threshold+"');");
            while (rs.next()) {
                areaSet.add(rs.getString("paperarea"));
            }
            rs.close();
            statement.close();
            connect.close();
            return areaSet;
        } catch (Exception e) {
            return new HashSet<>();
        }
    }
}
