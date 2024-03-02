package com.project.accomatch.Repository.Implementation;

import com.project.accomatch.Exception.DataAccessException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Repository.LeaseholderAdsDaoInterface;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class LeaseholderAdsDao implements LeaseholderAdsDaoInterface {
    Logger logger = LoggerClass.getLogger();

    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;

    public List<Posts> getListOfPosts() throws SQLException {
        List<Posts> listOfPosts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM leaseholder_ads")) {

            while (resultSet.next()) {
                int leaseholderApplicationId = resultSet.getInt("leaseholder_application_id");
                int userId = resultSet.getInt("user_id");
                String title = resultSet.getString("title");
                String subtitle = resultSet.getString("subtitle");
                String address = resultSet.getString("address");
                String locationCity = resultSet.getString("location_city");
                int size = resultSet.getInt("size");
                String roomType = resultSet.getString("room_type");
                String document = resultSet.getString("document_link");
                double rent = resultSet.getDouble("rent");
                String otherPreferences = resultSet.getString("other_preferences");
                Date startDate = resultSet.getDate("start_date");
                int startAge = resultSet.getInt("start_age");
                int endAge = resultSet.getInt("end_age");
                int isVerified = resultSet.getInt("is_verified");
                Date createdAt = resultSet.getTimestamp("createdAt");
                Date updatedAt = resultSet.getTimestamp("updatedAt");

                Posts post = new Posts(leaseholderApplicationId, userId, title, subtitle, address, locationCity, size,
                        roomType, document, rent, otherPreferences, startDate, startAge, endAge, isVerified,
                        createdAt, updatedAt);

                listOfPosts.add(post);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DataAccessException("Failed to retrieve the list of posts.", e);
        }

        return listOfPosts;
    }

    public Posts getPostByApplicationId(int applicationId) {
        Posts post = null;

        try (Connection connection = DriverManager.getConnection(JDBC, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM leaseholder_ads WHERE leaseholder_application_id = ?")) {

            statement.setInt(1, applicationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int leaseholderApplicationId = resultSet.getInt("leaseholder_application_id");
                    int userId = resultSet.getInt("user_id");
                    String title = resultSet.getString("title");
                    String subtitle = resultSet.getString("subtitle");
                    String address = resultSet.getString("address");
                    String locationCity = resultSet.getString("location_city");
                    int size = resultSet.getInt("size");
                    String roomType = resultSet.getString("room_type");
                    String document = resultSet.getString("document_link");
                    double rent = resultSet.getDouble("rent");
                    String otherPreferences = resultSet.getString("other_preferences");
                    Date startDate = resultSet.getDate("start_date");
                    int startAge = resultSet.getInt("start_age");
                    int endAge = resultSet.getInt("end_age");
                    int isVerified = resultSet.getInt("is_verified");
                    Date createdAt = resultSet.getTimestamp("createdAt");
                    Date updatedAt = resultSet.getTimestamp("updatedAt");

                    post = new Posts(leaseholderApplicationId, userId, title, subtitle, address, locationCity, size,
                            roomType, document, rent, otherPreferences, startDate, startAge, endAge, isVerified,
                            createdAt, updatedAt);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DataAccessException("Failed to retrieve the list of posts.", e);
        }

        return post;
    }
    /**
     * Gives list of personal posts for a specific user based on their user ID.
     * @auther Dhrumil Vimalbhai Gosaliya
     * @param user_id of the user for whom to get personal posts.
     * @return list of Posts shows the personal posts of the user.
     */
    public List<Posts> getListOfPersonalPosts(int user_id){
        List<Posts> listOfPosts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM leaseholder_ads WHERE user_id = ?")) {

            statement.setInt(1, user_id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int leaseholderApplicationId = resultSet.getInt("leaseholder_application_id");
                    int userId = resultSet.getInt("user_id");
                    String title = resultSet.getString("title");
                    String subtitle = resultSet.getString("subtitle");
                    String address = resultSet.getString("address");
                    String locationCity = resultSet.getString("location_city");
                    int size = resultSet.getInt("size");
                    String roomType = resultSet.getString("room_type");
                    String document = resultSet.getString("document_link");
                    double rent = resultSet.getDouble("rent");
                    String otherPreferences = resultSet.getString("other_preferences");
                    Date startDate = resultSet.getDate("start_date");
                    int startAge = resultSet.getInt("start_age");
                    int endAge = resultSet.getInt("end_age");
                    int isVerified = resultSet.getInt("is_verified");
                    Date createdAt = resultSet.getTimestamp("createdAt");
                    Date updatedAt = resultSet.getTimestamp("updatedAt");

                    Posts post = new Posts(leaseholderApplicationId, userId, title, subtitle, address, locationCity, size,
                            roomType, document, rent, otherPreferences, startDate, startAge, endAge, isVerified,
                            createdAt, updatedAt);
                    listOfPosts.add(post);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DataAccessException("Failed to retrieve the list of posts.", e);
        }

        return listOfPosts;
    }

    /***
     * Gets the list of post by status for Admin
     * @param status
     * @return List of Posts
     */
    public List<Posts> getListOfPostsByStatus(int status) {
        List<Posts> listOfPosts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM leaseholder_ads WHERE is_verified = ?")) {

            statement.setInt(1, status);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int leaseholderApplicationId = resultSet.getInt("leaseholder_application_id");
                int userId = resultSet.getInt("user_id");
                String title = resultSet.getString("title");
                String subtitle = resultSet.getString("subtitle");
                String address = resultSet.getString("address");
                String locationCity = resultSet.getString("location_city");
                int size = resultSet.getInt("size");
                String roomType = resultSet.getString("room_type");
                String document = resultSet.getString("document_link");
                double rent = resultSet.getDouble("rent");
                String otherPreferences = resultSet.getString("other_preferences");
                Date startDate = resultSet.getDate("start_date");
                int startAge = resultSet.getInt("start_age");
                int endAge = resultSet.getInt("end_age");
                int isVerified = resultSet.getInt("is_verified");
                Date createdAt = resultSet.getTimestamp("createdAt");
                Date updatedAt = resultSet.getTimestamp("updatedAt");

                Posts post = new Posts(leaseholderApplicationId, userId, title, subtitle, address, locationCity, size,
                        roomType, document, rent, otherPreferences, startDate, startAge, endAge, isVerified,
                        createdAt, updatedAt);

                listOfPosts.add(post);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DataAccessException("Failed to retrieve the list of posts.", e);

        }

        return listOfPosts;
    }
}
