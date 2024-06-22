package com.lcwd.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.external.service.RatingService;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}
//	@Autowired
//	private RatingService ratingService;
	
//	@Test
//	void createRating() {
//		
//		Rating rating= Rating.builder().rating(10).userId("").hotelId("").feedback("testing open fiegn client").build();
//		Rating saveRating=ratingService.createRating(rating);
//		System.out.println("new rating added "+saveRating);
//	}

}
