package com.project.accomatch.Repository.Implementation;

import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Repository.AdminRepositoryInterface;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class AdminRepository implements AdminRepositoryInterface {

    Logger logger = LoggerClass.getLogger();
    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;

    /**
     * Updates the verification status of a specific leaseholder ad in the database.
     * @author Yogish Honnadevipura Gopalakrishna
     * @param posts The Posts object representing the ad to be updated with verification status.
     * @return A string indicating the result of the update process. "Success" on successful update,
     *         "Error" if there was an error during the update.
     */
    public String verifyOneAd(Posts posts){
        try (Connection connect = DriverManager.getConnection(JDBC, username, password);
             Statement statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            String sql = "update leaseholder_ads set is_verified = ? where leaseholder_application_id = ?";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setInt(1, posts.isVerified());
            stmt.setInt(2, posts.getLeaseholderApplicationId());
            stmt.executeUpdate();
            stmt.close();
            logger.info(posts.isVerified()+" :Verification Successful for one AD");
            return "Success";
        }catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return "Error";
        }
        }

    /**
     * Updates the verification status of all leaseholder ads in the database.
     * @author Yogish Honnadevipura Gopalakrishna
     * @param posts The Posts object representing the ad with verification status to be applied to all ads.
     * @return A string indicating the result of the update process. "Success" on successful update,
     *         "Error" if there was an error during the update.
     */
    public String verifyAllAd(Posts posts){
        try (Connection connect = DriverManager.getConnection(JDBC, username, password);
             Statement statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            String sql = "update leaseholder_ads set is_verified = ?";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setInt(1, posts.isVerified());
            stmt.executeUpdate();
            stmt.close();
            logger.info(posts.isVerified()+" :Verification Successful for All ADs");
            return "Success";
        }catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return "Error";
        }
    }
    }

