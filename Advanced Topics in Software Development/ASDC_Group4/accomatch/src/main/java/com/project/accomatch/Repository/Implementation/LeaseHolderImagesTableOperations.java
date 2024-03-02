package com.project.accomatch.Repository.Implementation;

//import com.project.accomatch.Credentials;
import com.project.accomatch.Model.LeaseHolderModel;
import com.project.accomatch.Repository.LeaseHolderImagesTableOperationsInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LeaseHolderImagesTableOperations implements LeaseHolderImagesTableOperationsInterface {
    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;
    public boolean addImages(LeaseHolderModel leaseHolderModel,int leaseholder_application_id){
        try {
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

         //   statement.execute("use accomatch;");
            String sql = "INSERT INTO leaseholder_images (application_id,image_link)"+
                    "VALUES (?,?)";
            PreparedStatement stmt = connect.prepareStatement(sql);
            for(String image_link :leaseHolderModel.getImages()){
                stmt.setInt(1,leaseholder_application_id);
                stmt.setString(2,image_link);
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

    public List<String> getImagesByApplicationId(int applicationId) {
        List<String> images = new ArrayList<>();

        try {
            Connection connect;
            Statement statement;

            // Connect to the database
            connect = DriverManager.getConnection(JDBC, username, password);

            // Create a statement object
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statement.execute("USE CSCI5308_4_DEVINT;");

            // Query to fetch images based on the application ID
            String sql = "SELECT image_link FROM leaseholder_images WHERE application_id = ?";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setInt(1, applicationId);

            ResultSet rs = stmt.executeQuery();

            // Fetch images and add them to the list
            while (rs.next()) {
                String imageLink = rs.getString("image_link");
                images.add(imageLink);
            }

            rs.close();
            stmt.close();
            connect.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return images;
    }

}

