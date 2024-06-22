package com.lcwd.rating.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.serviceImpl.RatingServiceImpl;

@RestController
@RequestMapping("/ratings")
public class RatingController {
	
	@Autowired
	private RatingServiceImpl ratingService;
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping
	public ResponseEntity<Rating> create(@RequestBody Rating rating) {
		System.out.println(rating.getUserId());
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
		
	}
	//get All
	@GetMapping
	public ResponseEntity<List<Rating>> getAll(){
		
		return ResponseEntity.ok(ratingService.getAllRatings());
	}
	//get all rating of particular user
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<Rating>>getRatingByUserId(@PathVariable String userId){
		return ResponseEntity.ok(ratingService.getAllByUserId(userId));		
	}
	
	//get All rating of particular hotel
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>>getRatingByHotelId(@PathVariable String hotelId){
		return ResponseEntity.ok(ratingService.getAllByHotelId(hotelId));
		
	}
	

}
