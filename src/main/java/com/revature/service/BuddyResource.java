package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.model.User;

@Service
public class BuddyResource {
	
	public List<User> SuggestBuddies(User you) {
		List<User> Buddies = new ArrayList<User>();
		List<Integer> BuddyId = new ArrayList<Integer>();
		
		String genre = MovieResource.getMostPopularGenre(you.getId()); 
		MovieResource.getBuddyByGenre(genre); 
		return null;
	}

}
