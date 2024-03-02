package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Exception.*;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Model.Ratings;
import com.project.accomatch.Model.Review;

import com.project.accomatch.Repository.Implementation.LeaseholderAdsDao;
import com.project.accomatch.Repository.Implementation.ReviewRepository;

import com.project.accomatch.Repository.LeaseholderAdsDaoInterface;
import com.project.accomatch.Repository.ReviewRepositoryInterface;
import com.project.accomatch.Service.ReviewService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {
    @Autowired
    public ReviewRepositoryInterface reviewRepository;
    @Autowired
    public LeaseholderAdsDaoInterface leaseholderAdsDao;

    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;

    Logger logger = LoggerClass.getLogger();

    @Override
    public void createReview(Review review) {
        try {
            reviewRepository.createReview(review);
        } catch (Exception e) {
            logger.error("Error during creating reviews {}", e.getMessage(), e);
            throw new ReviewCreationExcecption("Failed to create reviews");
        }
    }

    @Override
    public List<Review> getAllReviews(int application_id) {
        return reviewRepository.getAllReviews(application_id);
    }

    @Override
    public List<Review> getAllPostReviews() {
        try {
            return reviewRepository.getAllPostReviews();
        } catch (Exception e) {
            logger.error("Error during retrieving all post reviews {}", e.getMessage(), e);
            throw new PostNotFoundException("Failed to retrieve the list of Applicants.");
        }
    }

    @Override
    public List<Ratings> getRatingsAverage(int application_id) {
        try{
        int count5Ratings = 0;
        int count4Ratings = 0;
        int count3Ratings = 0;
        int count2Ratings = 0;
        int count1Ratings = 0;
        int totalRatings = 0;
        double averageRating;

        Ratings ratings = new Ratings();
        List<Ratings> listOfRatings = new ArrayList<>();
        List<Review> listOfReviews = reviewRepository.getAllReviews(application_id);
        for (Review review : listOfReviews) {
            int rating = review.getRating();
            if (rating == 5) {
                count5Ratings++;
                ratings.setCount5Ratings(count5Ratings);
            } else if (rating == 4) {
                count4Ratings++;
                ratings.setCount4Ratings(count4Ratings);
            } else if (rating == 3) {
                count3Ratings++;
                ratings.setCount3Ratings(count3Ratings);
            } else if (rating == 2) {
                count2Ratings++;
                ratings.setCount2Ratings(count2Ratings);
            } else if (rating == 1) {
                count1Ratings++;
                ratings.setCount1Ratings(count1Ratings);
            }
        }
        totalRatings = ratings.getCount1Ratings() + ratings.getCount2Ratings() +
                ratings.getCount3Ratings() + ratings.getCount4Ratings() + ratings.getCount5Ratings();

        averageRating = (ratings.getCount1Ratings()+ ratings.getCount2Ratings() * 2 + ratings.getCount3Ratings() * 3 +
                ratings.getCount4Ratings() * 4 + ratings.getCount5Ratings() * 5) / (double) totalRatings;
        averageRating = Math.round(averageRating * 10.0) / 10.0;

        ratings.setAverageRating(averageRating);

        listOfRatings.add(ratings);
        return listOfRatings;
        }
        catch (Exception e){
            logger.error("Error during retrieving all post ratings {}", e.getMessage(), e);
            throw new RatingsNotFoundException("Failed to retrieve ratings.");
        }

    }

    public List<Ratings> getAllRatingsAverage() {
        ArrayList<Ratings> listOfRatings = new ArrayList<>();

        try {
            List<Posts> listOfAllPosts = leaseholderAdsDao.getListOfPosts();
            for (Posts post : listOfAllPosts) {
                Ratings ratings = new Ratings();
                int count5Ratings = 0;
                int count4Ratings = 0;
                int count3Ratings = 0;
                int count2Ratings = 0;
                int count1Ratings = 0;
                int totalRatings =0;
                double averageRating;


                int applicationId = post.getLeaseholderApplicationId();
                List<Review> listOfReviews = reviewRepository.getAllReviews(applicationId);

                for (Review review : listOfReviews) {
                int rating = review.getRating();
                if (rating == 5) {
                    count5Ratings++;
                    ratings.setCount5Ratings(count5Ratings);
                } else if (rating == 4) {
                    count4Ratings++;
                    ratings.setCount4Ratings(count4Ratings);
                } else if (rating == 3) {
                    count3Ratings++;
                    ratings.setCount3Ratings(count3Ratings);
                } else if (rating == 2) {
                    count2Ratings++;
                    ratings.setCount2Ratings(count2Ratings);
                } else if (rating == 1) {
                    count1Ratings++;
                    ratings.setCount1Ratings(count1Ratings);
                }

            }
            totalRatings = ratings.getCount1Ratings() + ratings.getCount2Ratings() +
                    ratings.getCount3Ratings() + ratings.getCount4Ratings() + ratings.getCount5Ratings();

            averageRating = (ratings.getCount1Ratings() + ratings.getCount2Ratings() * 2 + ratings.getCount3Ratings() * 3 +
                    ratings.getCount4Ratings() * 4 + ratings.getCount5Ratings() * 5) / (double) totalRatings;

            averageRating = Math.round(averageRating * 10.0) / 10.0;
            ratings.setAverageRating(averageRating);

            listOfRatings.add(ratings);
            }

        } catch (Exception e) {
            logger.error("Error during retrieving all post ratings {}", e.getMessage(), e);
            throw new RatingsNotFoundException("Failed to retrieve ratings.");
        }
        return listOfRatings;
    }
}