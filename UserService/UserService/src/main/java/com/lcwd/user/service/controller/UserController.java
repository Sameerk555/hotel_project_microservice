package com.lcwd.user.service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User user1= userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	int retryCount=1;
	@GetMapping("/{userId}")
//	@CircuitBreaker(name= "ratingHotelBreaker",fallbackMethod= "ratingHotelFallback" )
//	@Retry(name= "ratingHotelService", fallbackMethod= "ratingHotelFallback")
	@RateLimiter(name="userRateLimiter", fallbackMethod= "ratingHotelFallback")
	public ResponseEntity<User> getUser(@PathVariable String userId){
		retryCount++;
		System.out.println("number of retry "+retryCount);
		User user= userService.getUser(userId);
		return ResponseEntity.ok(user);
		
	}
	
	//creating fallback method for circuit breaker
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
		User user=User.builder().email("dummy@gmail.com").name("dummy").about("this user is created because some sevice is down").userId("12345").build();
//		logger.info("", ex.getMessage());
		
		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
	 List<User>user= userService.getAllUser();
	 
	 return ResponseEntity.ok(user);
	}
	

}
