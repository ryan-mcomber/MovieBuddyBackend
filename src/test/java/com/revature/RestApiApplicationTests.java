package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.service.MovieResource;

@SpringBootTest
class RestApiApplicationTests {
	MovieResource mr = new MovieResource();
	@Test
	void contextLoads() {
	}
	@Test
	void MovieGenreTest() {
		
		assertEquals("Drama",mr.getMostPopularGenre(1));
	}
	@Test
	void MovieRecommendTest() {
		
		assertEquals(550,mr.getRecommendationId(1));
	}

}
