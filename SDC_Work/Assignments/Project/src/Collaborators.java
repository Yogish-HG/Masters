//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.sql.*;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Set;
//
//public class Collaborators {
//
//    private static String username;
//    private static String password;
//
//    public static void credentialsFinder() {
//        Properties identity = new Properties();
//        String propertyFilename = "credentials.prop";
//
//        try {
//            InputStream stream = new FileInputStream(propertyFilename);
//
//            identity.load(stream);
//
//            username = identity.getProperty("username");
//            password = identity.getProperty("password");
//        } catch (Exception e) {
//            return;
//        }
//    }
//
//
//    public static Set<String> collabs(String author, int dist) throws SQLException {
//
//        credentialsFinder();
//        Map<String, String> publicationDetails = new HashMap<>();
//        Connection connect = null;
//        Statement statement = null;
//        ResultSet rs = null;
//        ResultSet rs2 = null;
//        int count = 0;
//        // connect to database.
//        connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password);
//        statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//        Map<String,String> MappingAuthors = new HashMap<>();
//
//        statement.execute("use yogish;");
//        rs = statement.executeQuery("SELECT group_concat(author) as collabos from AuthorIdentifier group by identifier;");
//        while(rs.next()){
//            String data = rs.getString("collabos");
//            String[] dataSplit = data.split(",");
//            for(String s: dataSplit){
//                if(!MappingAuthors.containsKey("s")){
//
//                }
//            }
//        }
//
//    }
//}
