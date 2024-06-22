package com.lcwd.user.service.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.external.service.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		String randomUserId=UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found for this id on server "+userId));
		//fetch rating of the above user from rating service
		//http://localhost:8083/ratings/users/d3cba309-c2eb-4
		Rating[] ratingOfUser=restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		logger.info("{} ", ratingOfUser);
//		user.setRatings(ratingOfUser);
		List<Rating>ratings= Arrays.stream(ratingOfUser).toList();
		
		List<Rating> ratingList=ratings.stream().map(rating ->{
			//api call to hotel service to get the hotel
			//http://localhost:8082/hotels/fb22cf7f-f41d-4458-9791-317934033f78
			//ResponseEntity<Hotel> forEntity=restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
			//Hotel hotel= forEntity.getBody();
			Hotel hotel= hotelService.getHotel(rating.getHotelId());
			//logger.info("response status code: {}", forEntity.getStatusCode());
			// set the hotel to rating
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList()); 
		user.setRatings(ratingList);
		return user;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User updateUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
