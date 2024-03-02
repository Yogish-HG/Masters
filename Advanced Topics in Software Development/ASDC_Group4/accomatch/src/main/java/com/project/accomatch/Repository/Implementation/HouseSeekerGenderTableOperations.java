package com.project.accomatch.Repository.Implementation;

import com.project.accomatch.Model.HouseSeekerModel;
import com.project.accomatch.Repository.HouseSeekerGenderTableOperationsInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class HouseSeekerGenderTableOperations implements HouseSeekerGenderTableOperationsInterface {
    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;
    public boolean createGenderReferences(HouseSeekerModel houseSeekerModel, int houseseeker_application_id) {
        try {
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("use CSCI5308_4_DEVINT;");
            String sql = "INSERT INTO houseseeker_gender_preferences (application_id,gender_pref)"+
                    "VALUES (?,?)";
            PreparedStatement stmt = connect.prepareStatement(sql);
            for(String gender_preference :houseSeekerModel.getGender_preferences()){
                stmt.setInt(1,houseseeker_application_id);
                stmt.setString(2,gender_preference);
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();
            connect.close();
            return true;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
