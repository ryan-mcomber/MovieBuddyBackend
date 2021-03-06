package com.revature.controller;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.Movie;
import com.revature.model.User;
import com.revature.service.MovieService;
import com.revature.service.UserService;

@RestController // RestController automatically puts ResponseBody on every public method (that is mapped) within this class
@RequestMapping("/movies") // we can access the methods of this controller at http://localhost:5000/app/movies
@CrossOrigin(origins = "*") // this exposes this controller to all ports
public class MovieController {

	@Autowired
	public MovieService movieService;

	// search
	@GetMapping("/find/{title}") // http://localhost:5000/api/movies/find/<title>
	public ResponseEntity<List<Movie>> search(@PathVariable("title") String title)
			throws JSONException {
		return ResponseEntity.ok(movieService.searchByTitle(title));
	}
	// insert
	@GetMapping("/add/{user-id}&{movie-id}") // http://localhost:5000/api/movies/add
	public ResponseEntity<Boolean> insert(@PathVariable("user-id") int user_id, @PathVariable("movie-id") int movie_id) throws JSONException {
		return ResponseEntity.ok(movieService.insert(movie_id, user_id));
	}
	@GetMapping("/list/{user-id}")// http://localhost:5000/api/movies/list
	public ResponseEntity<List<Movie>> movieList(@PathVariable("user-id") int user_id) throws JSONException {
		return ResponseEntity.ok(movieService.getMovieList(user_id));
	}
	@GetMapping("/recommend/{user-id}")// http://localhost:5000/api/movies/recommend
	public ResponseEntity<Integer> recommend(@PathVariable("user-id") int user_id) throws JSONException {
		return ResponseEntity.ok(movieService.getRecommendMovieId(user_id));
	}
	@GetMapping("/recommended/{movie-id}")// http://localhost:5000/api/movies/recommend
	public ResponseEntity<List<Movie>> recommended(@PathVariable("movie-id") int movie_id) throws JSONException {
		return ResponseEntity.ok(movieService.getMovieRecommendations(movie_id));
	}

//	@DeleteMapping("/{id}")
//	public void deleteMovie(@PathVariable("id") int id) {
//
//		movieService.remove(id);
//
//	}

}
