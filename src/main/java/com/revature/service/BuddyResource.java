package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.model.User;

@Service
public class BuddyResource {

	@Autowired
	private MovieResource movieResource;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private UserService userService;
	
	public List<User> FindBuddy (User u){
		String genre = "";
		List<Integer> BuddyIds = new ArrayList<Integer>();
		List<User> Buddies = new ArrayList<User>();
		genre = MovieResource.getMostPopularGenre(u.getId());
		
		if (genre != "") {//if genre is returned
			BuddyIds = MovieResource.getBuddyByGenre(genre); //Get buddy IDs
		}else {
			return null; //Break if no genre returned
		}
		
		for (int i = 0; i<BuddyIds.size(); i++) {
			if(BuddyIds.get(i) != u.getId()) { //If not the searched user
				Buddies.add(UserService.getUsernameById(BuddyIds.get(i))); //Add the user to the buddies list
			}
		}
		return Buddies;
		
	}
	
}
