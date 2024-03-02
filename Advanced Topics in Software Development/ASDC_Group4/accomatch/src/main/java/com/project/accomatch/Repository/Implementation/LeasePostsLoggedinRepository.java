package com.project.accomatch.Repository.Implementation;
import com.project.accomatch.Exception.DataAccessException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Repository.LeasePostsLoggedinRepositoryInterface;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository
public class LeasePostsLoggedinRepository implements LeasePostsLoggedinRepositoryInterface {
    Logger logger = LoggerClass.getLogger();
    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;

    public List<Posts> getListOfLoggedinApplicant(int application_id) {
        List<Posts> listOfApplicants = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC, username, password);
             /*PreparedStatement statement = connection.prepareStatement("(SELECT user_id,name,email,age,gender,mobile FROM user where user_id =" +
                     "(SELECT user_id FROM houseseeker_applicant where application_id = ?))"*/
             PreparedStatement statement = connection.prepareStatement("(SELECT * FROM leaseholder_ads where leaseholder_application_id IN"+
                     "(SELECT application_id FROM leaseholder_applicant where user_id = ?))"))
             {
            statement.setInt(1,application_id);
            ResultSet resultSet= statement.executeQuery();
            while (resultSet.next()) {
                //int applicationId = resultSet.getInt("application_id");
                int leaseholderApplicationId = resultSet.getInt("leaseholder_application_id");
                //int leaseholderApplicantId = resultSet.getInt("leaseholder_applicant_id");
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

                listOfApplicants.add(post);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DataAccessException("Failed to retrieve the reviews.", e);
        }

        return listOfApplicants;
    }
}
