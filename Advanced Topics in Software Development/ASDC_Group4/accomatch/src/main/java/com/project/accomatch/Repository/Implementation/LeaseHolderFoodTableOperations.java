package com.project.accomatch.Repository.Implementation;

//mport com.project.accomatch.Credentials;
import com.project.accomatch.Model.LeaseHolderModel;
import com.project.accomatch.Repository.LeaseHolderFoodTableOperationsInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LeaseHolderFoodTableOperations implements LeaseHolderFoodTableOperationsInterface {
    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;
    public boolean createFoodReferences(LeaseHolderModel leaseHolderModel,int leaseholder_application_id){
        try {
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

         //   statement.execute("use accomatch;");
            String sql = "INSERT INTO leaseholder_food_preferences (application_id,food_pref)"+
                    "VALUES (?,?)";
            PreparedStatement stmt = connect.prepareStatement(sql);
            for(String food_preference :leaseHolderModel.getFood_preferences()){
                stmt.setInt(1,leaseholder_application_id);
                stmt.setString(2,food_preference);
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

    public List<String> getFoodPreferencesByApplicationId(int applicationId) {
        List<String> foodPreferences = new ArrayList<>();

        try {
            Connection connect;
            Statement statement;

            // Connect to the database
            connect = DriverManager.getConnection(JDBC, username, password);

            // Create a statement object
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("USE CSCI5308_4_DEVINT;");

            // Query to fetch food preferences based on the application ID
            String sql = "SELECT food_pref FROM leaseholder_food_preferences WHERE application_id = ?";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setInt(1, applicationId);

            ResultSet rs = stmt.executeQuery();

            // Fetch food preferences and add them to the list
            while (rs.next()) {
                String foodPref = rs.getString("food_pref");
                foodPreferences.add(foodPref);
            }

            rs.close();
            stmt.close();
            connect.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return foodPreferences;
    }
}
