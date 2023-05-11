package ibf2022.server.model;

public class Review {
    private String restaurantName;
    private String reviewer;
    private String rating;
    private String review;
    private String imageUrl;


    public String getRestaurantName() {return this.restaurantName;}
    public void setRestaurantName(String restaurantName) {this.restaurantName = restaurantName;}

    public String getReviewer() {return this.reviewer;}
    public void setReviewer(String reviewer) {this.reviewer = reviewer;}

    public String getRating() {return this.rating;}
    public void setRating(String rating) {this.rating = rating;}

    public String getReview() {return this.review;}
    public void setReview(String review) {this.review = review;}

    public String getImageUrl() {return this.imageUrl;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

}
