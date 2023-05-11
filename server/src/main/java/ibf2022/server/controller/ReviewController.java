package ibf2022.server.controller;

import java.io.IOException;
import java.util.List;

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

import ibf2022.server.model.Review;
import ibf2022.server.repository.ReviewRepository;
import ibf2022.server.service.S3Service;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/review")
public class ReviewController {
    
    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    private S3Service s3svc;

    @GetMapping("/{restaurant}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable String restaurant) {
        List<Review> reviews = reviewRepo.getReviews(restaurant);
		return ResponseEntity.ok(reviews);
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
        
        String key = "";
        try {
            key = s3svc.upload(imageFile);
            String imageUrl = "https://tofuibfb22022.sgp1.digitaloceanspaces.com/myobjects/" + key; 
            reviewObj.setImageUrl(imageUrl); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        reviewRepo.insertReview(reviewObj);

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

        reviewRepo.insertReview(reviewObj);

        return ResponseEntity.ok(reviewObj);
    }

}
