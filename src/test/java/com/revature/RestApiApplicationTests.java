package com.revature;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.model.Movie;
import com.revature.service.MovieResource;

@SpringBootTest
class RestApiApplicationTests {
	MovieResource mr = new MovieResource();

	@Test
	void contextLoads() {
	}
	@Test
	void MovieGenreTest() {
		
		assertEquals("Fantasy",MovieResource.getMostPopularGenre(1));
	}
	@Test
	void MovieRecommendTest() {
		
		assertEquals(268,mr.getRecommendationId(1));
	}
	@Test
	void MovieRecommendListTest() {
		assertThrows(NullPointerException.class,()->{List<Movie> movieList =new ArrayList<Movie>();
		movieList = mr.getMovieRecommendations(1);}
		);
	}
	@Test
	void GetMovieList() {
		List<Movie> movieList =new ArrayList<Movie>();
		movieList = mr.getMovieList(1);
		assertEquals(movieList.isEmpty(),false);
	}

}
