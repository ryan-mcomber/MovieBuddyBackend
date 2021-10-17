package com.revature;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.model.Movie;
import com.revature.service.MovieResource;
import com.revature.service.MovieService;

@SpringBootTest
class RestApiApplicationTests {
	MovieService ms = new MovieService();
	MovieResource mr = new MovieResource();

	@Test
	void contextLoads() {
	}
	@Test
	void MovieGenreTest() {
		
		assertEquals("Drama",MovieResource.getMostPopularGenre(1));
	}
	@Test
	void MovieRecommendTest() {
		
		assertFalse(mr.getRecommendationId(1)==0);
	}
	@Test
	void MovieRecommendListTest() {
		assertEquals(false,ms.getMovieRecommendations(1).isEmpty());
	}
	@Test
	void GetMovieListTest() {
		List<Movie> movieList =new ArrayList<Movie>();
		movieList = mr.getMovieList(1);
		assertEquals(movieList.isEmpty(),false);
	}
	@Test
	void TestMovieInsertFail() {
		assertThrows(HttpClientErrorException.class,()->{ms.insert(0, 0);}
		);
		
	}
	@Test
	void TestMovieInsert() {
		try {
			assertEquals(ms.insert(550, 1),true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	void searchByTitleTest() {
		assertEquals(ms.searchByTitle("batman").isEmpty(),false);}
	@Test
	void findByIdTest() {
		try {
			assertEquals(mr.findById(550).toString(),"Movie [id=0, user_id=0, tmdb_id=550, title=Fight Club, year=1999, genre=Drama, genre_id=18, rating=8.4, img=http://image.tmdb.org/t/p/w92/a26cQPRhJPX6GbWfQbvZdrrp9j9.jpg?api_key=093c734f042b18166bbf417d9a8701e6, description=A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.]");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
