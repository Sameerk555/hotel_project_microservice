package com.lcwd.rating.services;

import java.util.List;

import com.lcwd.rating.entities.Rating;

public interface RatingService {
	
	//create ratings
	Rating create(Rating rating);
	
	//get all rating
	List<Rating> getAllRatings();
	
	//get all by user id
	List<Rating> getAllByUserId(String userId);
	
	//get Rating by hotel id
	List<Rating> getAllByHotelId(String hotelId);
}