package com.revature.service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.revature.model.Movie;
import com.revature.util.HibernateUtil;

@Service
public class MovieResource { // all external api calls go here

	@Value("${api.key}") // from application.properties
	private String apiKey = "093c734f042b18166bbf417d9a8701e6";

	@Autowired
	private RestTemplate restTemplate;

//	public Set<Movie> searchByTitle(String query) { // no longer necessary?
//		JSONObject obj = (JSONObject) restTemplate.getForObject(
//				"https://api.themoviedb.org/3/search/movie?api_key=" + apiKey
//				+ "&language=en-US&page=1&include_adult=false&query=" + query,
//				JSONObject.class);
//		
//		Set<Movie> results = new HashSet<>(); // move this parsing method down below?
////		obj.keys().forEachRemaining(key -> {
////				Movie m = new Movie();
////				m.setParam(obj.getParam);
////				m.setParam(obj.getParam);
////				results.add(m);
////		}
//		return results;
//	}

	public Movie findById(int tmdb_id) throws JSONException {
		String result = restTemplate.getForObject(
				"https://api.themoviedb.org/3/movie/" + tmdb_id + "?api_key=" + apiKey + "&language=en-US",
				String.class);

		JSONObject obj = new JSONObject(result);

		Movie m = new Movie();
		m.setTmdb_id(tmdb_id);
		m.setTitle(obj.getString("title"));
		String year = (obj.getString("release_date"));
		m.setYear(Integer.parseInt(year.substring(0, 4)));
		JSONArray arr = obj.getJSONArray("genres");
		m.setGenre_id(arr.getJSONObject(0).getInt("id"));
		m.setGenre(arr.getJSONObject(0).getString("name"));
		m.setRating(obj.getDouble("vote_average"));
		m.setImg("http://image.tmdb.org/t/p/w92" + obj.getString("poster_path") + "?api_key=" + apiKey);
		m.setDescription(obj.getString("overview"));
		return m;
	}

	// returns id of the movie used to get recommendations in the frontend.
	public int getRecommendationId(int user_id) {
		List results = null;
		try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
			String genre = getMostPopularGenre(user_id);
			String str = "SELECT tmdb_id\r\n" + "FROM com.revature.model.Movie\r\n" + "WHERE user_id = " + user_id
					+ " AND genre = '" + genre + "'";
			Query q = ses.createQuery(str);
			results = q.list();
		} catch (javax.persistence.PersistenceException ex) {
			ex.printStackTrace();
		}

		Random r = new Random();
		int index = r.nextInt(results.size());
		return (int) results.get(index);

	}

	// returns the genre-id
	public static String getMostPopularGenre(int user_id) {
		List results = null;
		try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
			String str = "SELECT genre\r\n" + "FROM com.revature.model.Movie \r\n" + "WHERE user_id = " + user_id
					+ "\r\n" + "GROUP BY genre \r\n" + "ORDER BY count(genre) DESC\r\n";
			Query q = ses.createQuery(str);
			results = q.list();
		} catch (javax.persistence.PersistenceException ex) {
			ex.printStackTrace();
		}

		return (String) results.get(0);

	}

	public static List<Integer> getBuddyByGenre(String genre) {
		List results = null;
		try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
			String str = "SELECT genre\r\n" + "FROM com.revature.model.Movie \r\n" + "WHERE genre = " + genre + "\r\n"
					+ "GROUP BY user_id \r\n" + "ORDER BY count(user_id) DESC\r\n";
			Query q = ses.createQuery(str);
			results = q.list();
		} catch (javax.persistence.PersistenceException ex) {
			ex.printStackTrace();
		}
		return results;
	}

}
