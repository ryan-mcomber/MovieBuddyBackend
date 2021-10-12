package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.Movie;

@Repository
public interface MovieDAO extends JpaRepository<Movie, Integer> {
	
	public Set<Movie> getUserList(int uid) {
		"SELECT * FROM movies WHERE user_id=?"
	}
	
	deleteById(int id) {
		
	}

}
