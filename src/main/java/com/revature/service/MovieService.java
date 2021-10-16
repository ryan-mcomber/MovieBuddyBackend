package com.revature.service;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.Movie;
import com.revature.repositories.MovieDAO;
import com.revature.util.HibernateUtil;

@Service
public class MovieService {

	@Autowired
	private MovieDAO mdao;

	@Autowired
	private MovieResource movieResource;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean insert(int tmdb_id, int user_id) throws JSONException { // add movie to user list
		try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
			Movie m = movieResource.findById(tmdb_id);
			m.setUser_id(user_id);
			System.out.println(m);
			ses.save(m);
			ses.close();
		} catch (javax.persistence.PersistenceException ex) {
			ex.printStackTrace();
		}

		return true;
	}
	
	public int getRecommendMovieId(int user_id) {
		return movieResource.getRecommendationId(user_id);	}
	public List<Movie> getMovieRecommendations(int movie_id){
		return movieResource.getMovieRecommendations(movie_id);
	}
//	public Set<Movie> findByUserId(int uid) { // return the user's movie list
//		return mdao.getUserList(uid);
//	}
//
//	public boolean removeById(int movie_id, int user_id) { // delete movie from user list
//		return true;
//	}

}
