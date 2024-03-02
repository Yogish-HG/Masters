package com.project.accomatch.Repository.Implementation;

import com.project.accomatch.Exception.DataAccessException;
import com.project.accomatch.Exception.PostCreationException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.HouseSeekerModel;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Repository.HouseSeekerTableOperationsInterface;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class HouseSeekerTableOperations implements HouseSeekerTableOperationsInterface {
    Logger logger = LoggerClass.getLogger();
    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;

    public int createAD(HouseSeekerModel houseSeekerModel) {
        try {
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //   statement.execute("use accomatch;");
            String sql = "INSERT INTO houseseeker_ads (user_id,location_city,room_type,other_preferences,start_date,createdAt,updatedAt)" +
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, houseSeekerModel.getUser_id());
            stmt.setString(2, houseSeekerModel.getLocation_city());
            stmt.setString(3, houseSeekerModel.getRoom_type());
            stmt.setString(4, houseSeekerModel.getOther_preferences());
            stmt.setDate(5, new java.sql.Date(houseSeekerModel.getStart_date().getTime()));
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(6, currentTimestamp);
            stmt.setTimestamp(7, currentTimestamp);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            int key = 0;
            if (rs.next()) {
                key = rs.getInt(1);
                // Use the generated key as needed
            }
            stmt.close();
            connect.close();
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new PostCreationException("Failed to retrieve the list of posts.",e);
        }
    }

    /**
     * Provides a list of all applicant posts.
     * @auther Dhrumil Vimalbhai Gosaliya
     * @return A list of HouseSeekerModel shows the applicant posts.
     */
    public List<HouseSeekerModel> getListOfAllApplicantPosts() {
        List<HouseSeekerModel> listOfApplicantPosts = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM user u inner join houseseeker_ads h on u.user_id=h.user_id")) {

            while (resultSet.next()) {
                int housekeeperApplicationId = resultSet.getInt("h.houseseeker_application_id");
                int userId = resultSet.getInt("h.user_id");
                String locationCity = resultSet.getString("h.location_city");
                String otherPreferences = resultSet.getString("h.other_preferences");
                String roomType = resultSet.getString("h.room_type");
                String name = resultSet.getString("u.name");
                java.util.Date startDate = resultSet.getDate("h.start_date");

                HouseSeekerModel houseSeekerModel = HouseSeekerModel.builder(userId,locationCity,roomType,startDate).housekeeperApplicationId(housekeeperApplicationId).otherPreferences(otherPreferences).name(name).build();

                listOfApplicantPosts.add(houseSeekerModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DataAccessException("Failed to retrieve the list of posts.", e);
        }
        return listOfApplicantPosts;
    }
}
