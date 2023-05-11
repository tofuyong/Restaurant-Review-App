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

   
    @PostMapping(path = "/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> uploadForAngular(
        @RequestPart MultipartFile imageFile,
        @RequestPart String reviewer,
        @RequestPart String rating,
        @RequestPart String review
    ){
        String key = "";
        System.out.printf("reviewer: %s", reviewer);
        System.out.printf("review: %s", review);
        
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

}
