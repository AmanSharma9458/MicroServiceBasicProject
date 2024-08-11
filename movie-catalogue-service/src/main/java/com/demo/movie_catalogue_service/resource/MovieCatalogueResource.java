package com.demo.movie_catalogue_service.resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.models.CatalogItem;
import com.demo.models.Movie;
import com.demo.models.Rating;
import com.demo.models.UserRating;
import com.netflix.discovery.DiscoveryClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogueResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId)
	{
		
		//RestTemplate restTemplate=new RestTemplate();
		//WebClient.Builder builder=WebClient.builder();
//		List<Rating> ratings=Arrays.asList(
//				new Rating("1234",4),
//				new Rating("2345",5)
//				);
		
		UserRating ratings=restTemplate.getForObject("http://rating-data-service/ratingsdata/user/"+userId, UserRating.class);
			
		return ratings.getRatings().stream().map(rating->
		{
			Movie movie=restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
//			Movie movie=webClient.build().get().uri("http://localhost:8082/movies/"+rating.getMovieId())
//					.retrieve()
//					.bodyToMono(Movie.class)
//					.block();
			return new CatalogItem(movie.getName(),"test",rating.getRating());
		})
			.collect(Collectors.toList());
				
//		return Collections.singletonList(
//				new CatalogItem("3Idiot","test",5)
//				);
	}
}
