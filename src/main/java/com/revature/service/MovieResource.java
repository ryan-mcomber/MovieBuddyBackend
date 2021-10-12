package com.revature.service;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import com.revature.model.Movie;

public class MovieResource { // all external api calls go here
	
	@Value("${api.key}") // from application.properties
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;

	public Set<Movie> searchByTitle(String query) { // this may change to Jackson ObjectMapper
		JSONObject obj = (JSONObject) restTemplate.getForObject(
				"https://api.themoviedb.org/3/search/movie?api_key=" + apiKey
				+ "&language=en-US&page=1&include_adult=false&query=" + query,
				JSONObject.class);
		
		Set<Movie> results; // move this parsing method down below?
		obj.keys().forEachRemaining(key -> {
				Movie m = new Movie();
				m.setParam(obj.getParam);
				m.setParam(obj.getParam);
				results.add(m);
		}
		return results;
	}
	
	public Movie findById(int tmdb_id) { // may be easier than extracting from above set
		JSONObject obj = (JSONObject) restTemplate.getForObject(
		"https://api.themoviedb.org/3/movie/" + tmdb_id + "?api_key=" + apiKey + "&language=en-US",
		JSONObject.class);
		
		Movie m = new Movie();
		m.setParam(obj.getParam);
		m.setParam(obj.getParam);
		return m;
	}
	
	public Set<Movie> getRecommendations(int genre_id) { // for example
		
	}

}
