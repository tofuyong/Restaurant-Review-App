package ibf2022.server.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.server.model.Restaurant;
import ibf2022.server.model.Review;
import ibf2022.server.repository.RestaurantRepository;
import ibf2022.server.service.S3Service;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/api")
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
    
    @Autowired
    RestaurantRepository restaurantRepo;

    @Autowired
    private S3Service s3svc;
    
    @GetMapping("/{cuisine}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisines(@PathVariable String cuisine) {
        logger.info(">>>>>Path variable cuisine type: " + cuisine);
        List<Restaurant> restaurants = restaurantRepo.findRestaurantsByCuisine(cuisine);
		return ResponseEntity.ok(restaurants);
	}

   
    @PostMapping(path = "/uploadwimage", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> uploadForAngularWithImage(
        @RequestPart MultipartFile imageFile,
        @RequestPart String restaurantName,
        @RequestPart String reviewer,
        @RequestPart String rating,
        @RequestPart String review
    ){
        Review reviewObj = new Review();
        reviewObj.setRestaurantName(restaurantName);
        reviewObj.setReviewer(reviewer);
        reviewObj.setRating(rating);
        reviewObj.setReview(review);

        restaurantRepo.insertReview(reviewObj);
        
        String key = "";
        try {
            key = s3svc.upload(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject payload = Json.createObjectBuilder()
            .add("imageKey", key)
            .build();

        return ResponseEntity.ok(payload.toString());
    }

    @PostMapping(path = "/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<Review> uploadForAngular(
        @RequestPart String restaurantName,
        @RequestPart String reviewer,
        @RequestPart String rating,
        @RequestPart String review
    ){
        Review reviewObj = new Review();
        reviewObj.setRestaurantName(restaurantName);
        reviewObj.setReviewer(reviewer);
        reviewObj.setRating(rating);
        reviewObj.setReview(review);

        restaurantRepo.insertReview(reviewObj);

        return ResponseEntity.ok(reviewObj);
    }

}
