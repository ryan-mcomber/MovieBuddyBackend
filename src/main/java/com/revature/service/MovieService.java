package com.revature.service;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.revature.model.Movie;
import com.revature.repositories.MovieDAO;

@Service
public class MovieService {
	
	@Autowired
	private MovieDAO mdao;
	
	@Autowired
	private MovieResource movieResource;

	public boolean insert(int tmdb_id, int user_id) { // add movie to user list
		Movie m = movieResource.findById(tmdb_id);
		mdao.save(m);
		return true;
	}
	
	public Set<Movie> findByUserId(int uid) { // return the user's movie list
		return mdao.getUserList(uid);
	}

	public boolean removeById(int movie_id, int user_id) { // delete movie from user list
		return true;
	}

}
