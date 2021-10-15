package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.model.User;

@Service
public class BuddyResource {
	
	@Autowired
	private UserService userService;
	
	public List<User> SuggestBuddies(User you) {
		List<User> Buddies = new ArrayList<User>();
		List<Integer> BuddyId = new ArrayList<Integer>();
		
		String genre = MovieResource.getMostPopularGenre(you.getId()); //get your most popular genre
		BuddyId = MovieResource.getBuddyByGenre(genre); //find all users who have something from your genre in the database
		
		for (int i = 0; i< BuddyId.size(); i++) { //Iterate through BuddyId
			if(you.getId() != BuddyId.get(i)) {//Discluding the user
				Buddies.add(userService.findById(BuddyId.get(i))); //Get the full user object and add it to the buddies list
			}
		}
		return Buddies;
	}

}
