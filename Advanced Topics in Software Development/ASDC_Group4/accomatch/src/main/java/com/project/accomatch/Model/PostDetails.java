package com.project.accomatch.Model;

import java.util.List;
/**
 * Represents the details of a post, including the post information, images, food preferences, and gender preferences.
 * Author: Ramandeep Kaur
 */
public class PostDetails {
    private Posts post;
    private List<String> images;
    private List<String> foodPreferences;
    private List<String> genderPreferences;

    /**
     * Constructs a new instance of PostDetails.
     *
     * @param post              The post information.
     * @param images            The list of images associated with the post.
     * @param foodPreferences   The list of food preferences for the post.
     * @param genderPreferences The list of gender preferences for the post.
     */
    public PostDetails(Posts post, List<String> images, List<String> foodPreferences, List<String> genderPreferences) {
        this.post = post;
        this.images = images;
        this.foodPreferences = foodPreferences;
        this.genderPreferences = genderPreferences;
    }

    /**
     * Gets the post information.
     *
     * @return The post information.
     */
    public Posts getPost() {
        return post;
    }

    /**
     * Sets the post information.
     *
     * @param post The post information to set.
     */
    public void setPost(Posts post) {
        this.post = post;
    }

    /**
     * Gets the list of images associated with the post.
     *
     * @return The list of images.
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * Sets the list of images associated with the post.
     *
     * @param images The list of images to set.
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * Gets the list of food preferences for the post.
     *
     * @return The list of food preferences.
     */
    public List<String> getFoodPreferences() {
        return foodPreferences;
    }

    /**
     * Sets the list of food preferences for the post.
     *
     * @param foodPreferences The list of food preferences to set.
     */
    public void setFoodPreferences(List<String> foodPreferences) {
        this.foodPreferences = foodPreferences;
    }

    /**
     * Gets the list of gender preferences for the post.
     *
     * @return The list of gender preferences.
     */
    public List<String> getGenderPreferences() {
        return genderPreferences;
    }

    /**
     * Sets the list of gender preferences for the post.
     *
     * @param genderPreferences The list of gender preferences to set.
     */
    public void setGenderPreferences(List<String> genderPreferences) {
        this.genderPreferences = genderPreferences;
    }
}
