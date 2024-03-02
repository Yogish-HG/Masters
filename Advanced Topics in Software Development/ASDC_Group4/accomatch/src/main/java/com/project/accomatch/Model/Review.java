package com.project.accomatch.Model;


    public class Review {

        private int ratingId;

        private int userId;

        private int applicationId;
        private int rating;

        private String comment;

        private String name;

        public Review(int ratingId, int userId, int applicationId, String name, int rating, String comment) {
            this.ratingId = ratingId;
            this.userId = userId;
            this.applicationId = applicationId;
            this.name = name;
            this.rating = rating;
            this.comment = comment;
        }

        public int getRatingId() {
            return ratingId;
        }

        public void setRatingId(int ratingId) {
            this.ratingId = ratingId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(int applicationId) {
            this.applicationId = applicationId;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


