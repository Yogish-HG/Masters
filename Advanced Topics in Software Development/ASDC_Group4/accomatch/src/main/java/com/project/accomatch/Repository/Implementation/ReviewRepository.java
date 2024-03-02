package com.project.accomatch.Repository.Implementation;

import com.project.accomatch.Exception.DataAccessException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Applicant;
import com.project.accomatch.Model.Review;
import com.project.accomatch.Repository.ReviewRepositoryInterface;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewRepository implements ReviewRepositoryInterface {

    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;

    Logger logger = LoggerClass.getLogger();

    /**
     * Creates new review for a user.
     *
     * @auther Dhrumil Vimalbhai Gosaliya
     * @param review review containing the details of the review to be created.
     * @return number of rows affected by the database insertion.
     * @throws DataAccessException if SQL-related exception occurs.
     */
    public int createReview(Review review) {
        try {
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "INSERT INTO rating_table (user_id,application_id,rating,review)" +
                    "VALUES (?,?,?,?)";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getApplicationId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());

            int rs = stmt.executeUpdate();
            stmt.close();
            connect.close();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Failed to retrieve the reviews.", e);
        }
    }
        public List<Review> getAllReviews(int application_id) {
            List<Review> listOfReviews = new ArrayList<>();

            try (Connection connection = DriverManager.getConnection(JDBC, username, password);
                 PreparedStatement statement = connection.prepareStatement(
                         "SELECT u.name, u.user_id, rt.application_id, rt.rating_id, rt.rating, rt.review, rt.rating FROM user u JOIN rating_table rt ON u.user_id = rt.user_id where rt.application_id = ?"

                 )) {
                statement.setInt(1,application_id);
                ResultSet resultSet= statement.executeQuery();
                while (resultSet.next()) {
                    //int applicationId = resultSet.getInt("application_id");
                    int userId = resultSet.getInt("user_id");
                    int applicationId = resultSet.getInt("application_id");
                    int ratingId = resultSet.getInt("rating_id");
                    int rating = resultSet.getInt("rating");
                    String comment = resultSet.getString("review");
                    String name = resultSet.getString("name");

                    Review reviews = new Review(ratingId, userId, applicationId, name, rating, comment);

                    listOfReviews.add(reviews);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new DataAccessException("Failed to retrieve the reviews.", e);
            }
            return listOfReviews;
        }

    public List<Review> getAllPostReviews() {
        List<Review> listOfReviews = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT u.name, u.user_id, rt.application_id, rt.rating_id, rt.rating, rt.review, rt.rating FROM user u JOIN rating_table rt ON u.user_id = rt.user_id"

             )) {
            //statement.setInt(1,application_id);
            //ResultSet resultSet= statement.executeQuery();
            while (resultSet.next()) {
                //int applicationId = resultSet.getInt("application_id");
                int userId = resultSet.getInt("user_id");
                int applicationId = resultSet.getInt("application_id");
                int ratingId = resultSet.getInt("rating_id");
                int rating = resultSet.getInt("rating");
                String comment = resultSet.getString("review");
                String name = resultSet.getString("name");

                Review reviews = new Review(ratingId, userId, applicationId, name, rating, comment);

                listOfReviews.add(reviews);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DataAccessException("Failed to retrieve the reviews.", e);
        }
        return listOfReviews;
    }
    }