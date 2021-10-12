package com.revature.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.Reference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "movies") // schema = "moviebuddy"
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
	
	@Id
	@Column(name = "movie_id", nullable = false, unique = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id; // serial id for db
	
	@Reference
	int user_id; // signifies belongs to this user's list
	
	int tmdb_id; // tmdb id#
	String title;
	int year;
	String genre;
	int genre_id; // useful for other calls?
	double rating; // based on "vote_average"
	String img; // points to FQDN + "poster_path" url concatenated with apikey
	String description; // from json "overview"
	

	// no ids constructor
	public Movie (int tmdb_id, String title, int year,
			String genre, int genre_id, double rating,
			String img, String description) {
		super();
		this.tmdb_id = tmdb_id;
		this.title = title;
		this.year = year;
		this.genre = genre;
		this.genre_id = genre_id;
		this.rating = rating;
		this.img = img;
		this.description = description;
	}

}
