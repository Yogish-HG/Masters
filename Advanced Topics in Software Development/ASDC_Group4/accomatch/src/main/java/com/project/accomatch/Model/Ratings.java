package com.project.accomatch.Model;

public class Ratings {
    private double averageRating;

    private int count5Ratings=0;
    private int count4Ratings=0;
    private int count3Ratings=0;
    private int count2Ratings=0;
    private int count1Ratings=0;

    public Ratings(){//double averageRating, int count5Ratings, int count4Ratings, int count3Ratings, int count2Ratings,int count1Ratings) {
        /*this.averageRating = averageRating;
        this.count5Ratings = count5Ratings;
        this.count4Ratings = count4Ratings;
        this.count3Ratings = count3Ratings;
        this.count2Ratings = count2Ratings;
        this.count1Ratings = count1Ratings;*/
    }

    public double getAverageRating() {
        return averageRating;
    }
    public int getCount5Ratings() {
        return count5Ratings;
    }
    public int getCount4Ratings() {
        return count4Ratings;
    }
    public int getCount3Ratings() {
        return count3Ratings;
    }

    public int getCount2Ratings() {
        return count2Ratings;
    }

    public int getCount1Ratings() {
        return count1Ratings;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating=averageRating;
    }

    public void setCount5Ratings(int count5Ratings) {
        this.count5Ratings = count5Ratings;
    }

    public void setCount4Ratings(int count4Ratings) {
        this.count4Ratings = count4Ratings;
    }

    public void setCount3Ratings(int count3Ratings) {
        this.count3Ratings = count3Ratings;
    }

    public void setCount2Ratings(int count2Ratings) {
        this.count2Ratings = count2Ratings;
    }

    public void setCount1Ratings(int count1Ratings) {
        this.count1Ratings = count1Ratings;
    }
}
