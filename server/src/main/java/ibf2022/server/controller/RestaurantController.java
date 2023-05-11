package ibf2022.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ibf2022.server.model.Restaurant;

import ibf2022.server.repository.RestaurantRepository;


@RestController
@RequestMapping("/api")
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
    
    @Autowired
    RestaurantRepository restaurantRepo;

    
    @GetMapping("/{cuisine}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisines(@PathVariable String cuisine) {
        logger.info(">>>>>Path variable cuisine type: " + cuisine);
        List<Restaurant> restaurants = restaurantRepo.findRestaurantsByCuisine(cuisine);
		return ResponseEntity.ok(restaurants);
	}

    
}
