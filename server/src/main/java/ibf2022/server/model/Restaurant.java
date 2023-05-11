package ibf2022.server.model;

public class Restaurant {
    private String name;
    private String rating;
    private String URL;
    private String address;
    private String address_line_2;
    private String cuisine;
    private int reviewCount;

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public String getRating() {return this.rating;}
    public void setRating(String rating) {this.rating = rating;}
    
    public String getURL() {return this.URL;}
    public void setURL(String URL) {this.URL = URL;}

    public String getAddress() {return this.address;}
    public void setAddress(String address) {this.address = address;}

    public String getAddress_line_2() {return this.address_line_2;}
    public void setAddress_line_2(String address_line_2) {this.address_line_2 = address_line_2;}

    public String getCuisine() {return this.cuisine;}
    public void setCuisine(String cuisine) {this.cuisine = cuisine;}

    public int getReviewCount() {return this.reviewCount;}
    public void setReviewCount(int reviewCount) {this.reviewCount = reviewCount;}


    public Restaurant(String name, String rating, String URL, String address, String address_line_2, String cuisine, int reviewCount) {
        this.name = name;
        this.rating = rating;
        this.URL = URL;
        this.address = address;
        this.address_line_2 = address_line_2;
        this.cuisine = cuisine;
        this.reviewCount = reviewCount;
    }
    

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", rating='" + getRating() + "'" +
            ", URL='" + getURL() + "'" +
            ", address='" + getAddress() + "'" +
            ", address_line_2='" + getAddress_line_2() + "'" +
            ", cuisine='" + getCuisine() + "'" +
            "}";
    }
    
}
