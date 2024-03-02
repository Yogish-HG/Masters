package com.project.accomatch.Repository.Implementation;

import com.project.accomatch.Model.HouseSeekerModel;
import com.project.accomatch.Repository.HouseSeekerFoodTableOperationsInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class HouseSeekerFoodTableOperations implements HouseSeekerFoodTableOperationsInterface {
    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;
    public boolean createFoodReferences(HouseSeekerModel houseSeekerModel, int houseseeker_application_id) {
        try {
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //   statement.execute("use accomatch;");
            String sql = "INSERT INTO houseseeker_food_preferences (application_id,food_pref)"+
                    "VALUES (?,?)";
            PreparedStatement stmt = connect.prepareStatement(sql);
            for(String food_preference :houseSeekerModel.getFood_preferences()){
                stmt.setInt(1,houseseeker_application_id);
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
}
