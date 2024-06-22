package com.lcwd.user.service.external.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.lcwd.user.service.entities.Rating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "RATING-SERVICE")
public interface RatingService {
	
	@GetMapping("/ratings/{ratingId}")
	Rating getRating(@PathVariable String ratingId);
	
	//POST
	@PostMapping("/ratings")
	Rating createRating(Rating values);
	
	//put
	@PutMapping("/ratings/{ratingId}")
	Rating updateRating(@PathVariable String ratingId, Rating rating);
	
	//delete
	@DeleteMapping("/ratings/{ratingId}")
	Rating deleteRating(@PathVariable String ratingId);

}
