package com.project.accomatch.Repository.Implementation;

//import com.project.accomatch.Credentials;
import com.project.accomatch.Model.LeaseHolderModel;
import com.project.accomatch.Repository.LeaseHolderTableOperationsInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.sql.*;

@Repository
public class LeaseHolderTableOperations implements LeaseHolderTableOperationsInterface {
    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;
    public int createAD(LeaseHolderModel leaseHolderModel){
        try {
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

         //   statement.execute("use accomatch;");
            String sql = "INSERT INTO leaseholder_ads (user_id,title,subtitle,address,location_city,size,room_type,document_link,rent,other_preferences,start_date,start_age,end_age,is_verified,createdAt,updatedAt)"+
                         "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1,leaseHolderModel.getUser_id());
            stmt.setString(2,leaseHolderModel.getTitle());
            stmt.setString(3,leaseHolderModel.getSubtitle());
            stmt.setString(4,leaseHolderModel.getAddress());
            stmt.setString(5,leaseHolderModel.getLocation_city());
            stmt.setInt(6,leaseHolderModel.getSize());
            stmt.setString(7,leaseHolderModel.getRoom_type());
            stmt.setString(8,leaseHolderModel.getDocument());
            stmt.setDouble(9,leaseHolderModel.getRent());
            stmt.setString(10,leaseHolderModel.getOther_preferences());
            stmt.setDate(11, new java.sql.Date(leaseHolderModel.getStart_date().getTime()));
            stmt.setInt(12,leaseHolderModel.getStart_age());
            stmt.setInt(13,leaseHolderModel.getEnd_age());
            stmt.setInt(14,leaseHolderModel.getIs_verified());
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(15,currentTimestamp);
            stmt.setTimestamp(16,currentTimestamp);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            int key = 0;
            if (rs.next()) {
                key = rs. getInt(1);
                // Use the generated key as needed
            }
            stmt.close();
            connect.close();
            return key;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public int getLeaseHolderId(int applicationId){
        try{
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT user_id from leaseholder_ads where leaseholder_application_id= ?";
            PreparedStatement stmt = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1,applicationId);
            ResultSet rs= stmt.executeQuery();
            int key=0;
            if(rs.next()){
                key=rs.getInt(1);
            }
            stmt.close();
            connect.close();
            return key;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
