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
	@Column(length = 1000)
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


	public Movie() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public int getTmdb_id() {
		return tmdb_id;
	}


	public void setTmdb_id(int tmdb_id) {
		this.tmdb_id = tmdb_id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public int getGenre_id() {
		return genre_id;
	}


	public void setGenre_id(int genre_id) {
		this.genre_id = genre_id;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return "Movie [id=" + id + ", user_id=" + user_id + ", tmdb_id=" + tmdb_id + ", title=" + title + ", year="
				+ year + ", genre=" + genre + ", genre_id=" + genre_id + ", rating=" + rating + ", img=" + img
				+ ", description=" + description + "]";
	}

	
}
