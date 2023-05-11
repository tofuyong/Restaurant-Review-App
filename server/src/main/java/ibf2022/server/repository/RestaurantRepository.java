package ibf2022.server.repository;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.server.model.Restaurant;
import ibf2022.server.model.Review;
import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class RestaurantRepository {
    private static final String RESTAURANT_COL = "restaurant";
    static final String REVIEW_COL = "review";

	@Autowired
    private MongoTemplate mongoTemplate;

    /* 
        db.restaurant.find({"type_of_food": '<cuisine>'},
        {_id:0, "name":1, "rating":1, "URL":1, "address":1, "address line 2":1 })
    */
    public List<Restaurant> findRestaurantsByCuisine(String cuisine) {
        Criteria criteria = Criteria.where("type_of_food").is(cuisine); 
        Query query = Query.query(criteria);
        query.fields().include("name", "rating", "URL", "address", "address line 2").exclude("_id");

        List<Document> results = mongoTemplate.find(query, Document.class, RESTAURANT_COL);
        List<Restaurant> restaurants = new ArrayList<>();
        for (Document document : results) {
            String jsonStr = document.toJson(); 
            JsonReader reader = Json.createReader(new StringReader(jsonStr));
            JsonObject o = reader.readObject();
            String name = o.getString("name"); 
            // Get rating as a string. If it's a number, convert it to string; if it's not a number, it must be a string.
            String rating = (o.get("rating") instanceof JsonNumber) ? String.valueOf(o.getJsonNumber("rating").doubleValue()) : o.getString("rating");
            String URL = o.getString("URL");
            String address = o.getString("address");
            String address_line_2 = o.getString("address line 2");
    
            Restaurant restaurant = new Restaurant(name, rating, URL, address, address_line_2, cuisine);
            restaurants.add(restaurant);
        }
       return restaurants;
    }

    /* db.review.insertOne({ restaurantName: '', reviewer: 'John', rating: 5, review: 'Great' }) */
    public Review insertReview(Review review) {
        return this.mongoTemplate.insert(review, REVIEW_COL);
    }

}
