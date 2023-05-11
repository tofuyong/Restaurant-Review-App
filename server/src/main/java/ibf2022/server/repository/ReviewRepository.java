package ibf2022.server.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.server.model.Review;

@Repository
public class ReviewRepository {
    private static final String REVIEW_COL = "review";

    @Autowired
    private MongoTemplate mongoTemplate;

    /* db.review.insertOne({ restaurantName: '', reviewer: 'John', rating: 5, review: 'Great', imageUrl: '' }) */
    public Review insertReview(Review review) {
        return this.mongoTemplate.insert(review, REVIEW_COL);
    }

    /* db.review.count({ restaurantName: "Adams Place" }) */
	public int countReviews(String restaurantName) {
		Criteria criteria = Criteria.where("restaurantName").is(restaurantName);
		Query query = Query.query(criteria);
		long count = mongoTemplate.count(query, "review");
		return (int) count;
	}

    /* db.review.find({ restaurantName: "Adams Place"}) */
    public List<Review> getReviews(String restaurantName) {
		Criteria criteria = Criteria.where("restaurantName").is(restaurantName);
		Query query = Query.query(criteria);
		List<Review> review = mongoTemplate.find(query, Review.class);
		return review;
	}

    
}
