package com.revature.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.Movie;

@Repository
public interface MovieDAO extends JpaRepository<Movie, Integer> {
	
	public Set<Movie> getUserList(int uid);
	
	public boolean deleteById(int id);

}
