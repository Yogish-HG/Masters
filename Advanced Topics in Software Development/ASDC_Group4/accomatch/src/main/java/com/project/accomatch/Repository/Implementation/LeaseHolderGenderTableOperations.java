package com.project.accomatch.Repository.Implementation;

//import com.project.accomatch.Credentials;
import com.project.accomatch.Model.LeaseHolderModel;
import com.project.accomatch.Repository.LeaseHolderGenderTableOperationsInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LeaseHolderGenderTableOperations implements LeaseHolderGenderTableOperationsInterface {
    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;

    public boolean createGenderReferences(LeaseHolderModel leaseHolderModel,int leaseholder_application_id){
        try {
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("use CSCI5308_4_DEVINT;");
            String sql = "INSERT INTO leaseholder_gender_preferences (application_id,gender_pref)"+
                    "VALUES (?,?)";
            PreparedStatement stmt = connect.prepareStatement(sql);
            for(String gender_preference :leaseHolderModel.getGender_preferences()){
                stmt.setInt(1,leaseholder_application_id);
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

    public List<String> getGenderPreferencesByApplicationId(int applicationId) {
        List<String> genderPreferences = new ArrayList<>();

        try {
            Connection connect;
            Statement statement;

            // Connect to the database
            connect = DriverManager.getConnection(JDBC, username, password);

            // Create a statement object
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("USE CSCI5308_4_DEVINT;");

            // Query to fetch gender preferences based on the application ID
            String sql = "SELECT gender_pref FROM leaseholder_gender_preferences WHERE application_id = ?";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setInt(1, applicationId);

            ResultSet rs = stmt.executeQuery();

            // Fetch gender preferences and add them to the list
            while (rs.next()) {
                String genderPref = rs.getString("gender_pref");
                genderPreferences.add(genderPref);
            }

            rs.close();
            stmt.close();
            connect.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return genderPreferences;
    }
}
