package com.revature.service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import com.revature.model.Movie;
import com.revature.util.HibernateUtil;

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
		
		Set<Movie> results = new HashSet<>(); // move this parsing method down below?
//		obj.keys().forEachRemaining(key -> {
//				Movie m = new Movie();
//				m.setParam(obj.getParam);
//				m.setParam(obj.getParam);
//				results.add(m);
//		}
		return results;
	}
	
	public Movie findById(int tmdb_id) { // may be easier than extracting from above set
		JSONObject obj = (JSONObject) restTemplate.getForObject(
		"https://api.themoviedb.org/3/movie/" + tmdb_id + "?api_key=" + apiKey + "&language=en-US",
		JSONObject.class);
		
		Movie m = new Movie();
//		m.setParam(obj.getParam);
//		m.setParam(obj.getParam);
		return m;
	}
	//returns id of the movie used to get recommendations in the frontend. 
	public int getRecommendationId(int user_id) {
		Session ses = HibernateUtil.getSessionFactory().openSession();
		String genre = getMostPopularGenre(user_id);
		String str = "SELECT id\r\n"
				+ "FROM com.revature.model.Movie\r\n"
				+ "WHERE user_id = "+user_id+ " AND genre = '"+genre+"'";
		Query q = ses.createQuery(str);
		List results = q.list();

		if (ses != null && ses.isOpen()) {
            ses.close();
        }
		Random r = new Random();
		int index = r.nextInt(results.size());
		return (int) results.get(index);
		
	}
	
	//returns the genre-id 
	public static String getMostPopularGenre(int user_id) {
		Session ses = HibernateUtil.getSessionFactory().openSession();
		String str = "SELECT genre\r\n"
				+ "FROM com.revature.model.Movie \r\n"
				+ "WHERE user_id = "+user_id+"\r\n"
				+ "GROUP BY genre \r\n"
				+ "ORDER BY count(genre) DESC\r\n";
		Query q = ses.createQuery(str);
		List results = q.list();
		if (ses != null && ses.isOpen()) {
            ses.close();
        }
		return (String) results.get(0);
		
	}

}
