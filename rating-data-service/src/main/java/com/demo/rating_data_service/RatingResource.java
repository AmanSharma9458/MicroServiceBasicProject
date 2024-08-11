package com.demo.rating_data_service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rating_data_service.Models.Rating;
import com.demo.rating_data_service.Models.UserRating;

@RestController
@RequestMapping("ratingsdata")
public class RatingResource {
	
	@RequestMapping("/movies/{movieId}")
	public Rating getMovieRating(@PathVariable("movieId") String movieId)
	{
		return new Rating(movieId,4);
	}
	
	@RequestMapping("/user/{userId}")
	public UserRating getUserRating(@PathVariable String userId)
	{
		List<Rating> ratings=Arrays.asList(
				new Rating("1234",4),
				new Rating("2345",5)
				);
//		UserRating ob=new UserRating(userId,new ArrayList<>());
//		return ob;
		UserRating userRating=new UserRating(ratings);
		return userRating;
	}

}
