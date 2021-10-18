package com.revature.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	private RestTemplate restTemplate = new RestTemplate();

	public List<Movie> searchByTitle(String title) {
		String result = restTemplate.getForObject("https://api.themoviedb.org/3/search/movie?api_key=" 
	+ apiKey + "&language=en-US&include_adult=false&query=" + title, String.class);
		System.out.println(result);
		List<Movie> movies = new ArrayList<>();
		try {

			JSONArray movieArray = new JSONObject(result).getJSONArray("results");
			for (int n = 0; n < movieArray.length(); n++) {
				JSONObject obj = movieArray.getJSONObject(n);
				Movie m = new Movie();
				m.setTmdb_id(obj.getInt("id"));
				m.setTitle(obj.getString("title"));
				String year = (obj.getString("release_date"));
				m.setYear(Integer.parseInt(year.substring(0, 4)));
				JSONArray arr = obj.getJSONArray("genre_ids");
				m.setGenre(parseGenreID(arr.getInt(0)));
				m.setRating(obj.getDouble("vote_average"));
				m.setImg("http://image.tmdb.org/t/p/w92" + obj.getString("poster_path") + "?api_key=" + apiKey);
				m.setDescription(obj.getString("overview"));
				movies.add(m);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(movies);
		return movies;
	}

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
	
	public List<Movie> getMovieList(int user_id) {
		List<Movie> movieList = new ArrayList<>();
		try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
			String str = "SELECT title FROM com.revature.model.Movie\r\n" + "WHERE user_id = " + user_id;
			String str2 = "SELECT img FROM com.revature.model.Movie\r\n" + "WHERE user_id = " + user_id;
			Query q = ses.createQuery(str);
			List titleList = q.list();
			
			for (int n = 0; n<titleList.size();n++) {
				Movie movie = new Movie();
				movie.setTitle((String) titleList.get(n));
				movieList.add(movie);
				
				
			}
			q = ses.createQuery(str2);
			List imgList = q.list();
			int count = 0;
			for(Movie m: movieList) {
				m.setImg((String) imgList.get(count));
				count++;
			}
		} catch (javax.persistence.PersistenceException | NullPointerException ex) {
			ex.printStackTrace();
		}
		return movieList;
	}


	public List<Movie> getMovieRecommendations(int user_id) {
		int movie_id = getRecommendationId(user_id);
		String result = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movie_id
				+ "/recommendations?api_key=4e03597f829ab368d49ae4a8f0769033&language=en-US", String.class);
		System.out.println(result);
		List<Movie> movies = new ArrayList<>();
		try {

			JSONArray movieArray = new JSONObject(result).getJSONArray("results");
			for (int n = 0; n < movieArray.length(); n++) {
				JSONObject obj = movieArray.getJSONObject(n);
				Movie m = new Movie();
				m.setTmdb_id(obj.getInt("id"));
				m.setTitle(obj.getString("title"));
				String year = (obj.getString("release_date"));
				m.setYear(Integer.parseInt(year.substring(0, 4)));
				JSONArray arr = obj.getJSONArray("genre_ids");
				m.setGenre_id(arr.getInt(0));
				m.setGenre(parseGenreID(arr.getInt(0)));
				m.setRating(obj.getDouble("vote_average"));
				m.setImg("http://image.tmdb.org/t/p/w92" + obj.getString("poster_path") + "?api_key=" + apiKey);
				m.setDescription(obj.getString("overview"));
				movies.add(m);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(movies);
		return movies;
	}

	// returns id of the movie used to get recommendations in the frontend.
	public Integer getRecommendationId(int user_id) {
		List results = new ArrayList<>();
		try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
			String genre = getMostPopularGenre(user_id);
			String str = "SELECT tmdb_id\r\n" + "FROM com.revature.model.Movie\r\n" + "WHERE user_id = " + user_id
					+ " AND genre = '" + genre + "'";
			Query q = ses.createQuery(str);
			results = q.list();
		} catch (javax.persistence.PersistenceException | NullPointerException ex) {
			ex.printStackTrace();
		}

		Random r = new Random();
		int index = r.nextInt(results.size());
		return (int) results.get(index);

	}

	// returns the genre-id
	public static String getMostPopularGenre(int user_id) {
		List results = new ArrayList();
		try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
			String str = "SELECT genre\r\n" + "FROM com.revature.model.Movie \r\n" + "WHERE user_id = " + user_id
					+ "\r\n" + "GROUP BY genre \r\n" + "ORDER BY count(genre) DESC\r\n";
			Query q = ses.createQuery(str);
			results = q.list();
		} catch (javax.persistence.PersistenceException | NullPointerException ex) {
			ex.printStackTrace();
		}

		return (String) results.get(0);

	}

	public static List<Integer> getBuddyByGenre(String genre) {
		List results = new ArrayList();
		List finalReturn = new ArrayList();
		try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
			String str = "SELECT user_id FROM com.revature.model.Movie WHERE genre = \'" + genre + "\' ORDER BY user_id DESC";
			Query q = ses.createQuery(str);
			results = q.list(); //Returns all the user ID entries. Need to sort by most entries and condense into a single entry, sorted by frequency
			if (results.size() != 0) {//If not no entries
				Map<Integer, Integer> entryCondenser = new HashMap<Integer, Integer>();
				for(int i=0; i<results.size(); i++) {//Create map. Find number of entries and map them to the ID
					if(entryCondenser.containsKey(results.get(i))) {//If not new key, add
						entryCondenser.put((Integer)results.get(i), entryCondenser.get(results.get(i))+1);
					}else {//If new key, create
						entryCondenser.put((Integer)results.get(i), 1);
					}
				}
				while (entryCondenser.size() > 0){
					int max = 0;
					int maxKey = 0;
					for (Map.Entry<Integer, Integer> entry : entryCondenser.entrySet()) {//Iterate through the map
						if (Integer.valueOf(entry.getValue())> max) {//if you find the new biggest integer, note the value and key
							max = Integer.valueOf((entry.getValue()));
							maxKey = Integer.valueOf((entry.getKey()));
						}
					}
					//Found max, now add to final list and remove from map.
					finalReturn.add(maxKey);
					entryCondenser.remove(maxKey);
				}
			}
						
		} catch (javax.persistence.PersistenceException | NullPointerException ex) {
			ex.printStackTrace();
		}
		return finalReturn;
	}

	public String parseGenreID(int id) {
		Map<Integer, String> genreMap = new HashMap<Integer, String>() {
			{
				put(28, "Action");
				put(12, "Adventure");
				put(16, "Animation");
				put(35, "Comedy");
				put(99, "Documentary");
				put(80, "Crime");
				put(18, "Drama");
				put(10751, "Family");
				put(14, "Fantasy");
				put(36, "History");
				put(27, "Horror");
				put(10402, "Music");
				put(9648, "Mystery");
				put(10749, "Romance");
				put(878, "Science Fiction");
				put(10770, "TV Movie");
				put(53, "Thriller");
				put(10752, "War");
				put(37, "Western");
			}
		};
		return genreMap.get(id);

	}

}
