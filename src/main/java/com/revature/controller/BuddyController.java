package com.revature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.User;
import com.revature.service.BuddyResource;

@RestController // RestController automatically puts ResponseBody on every public method (that is mapped) within this class
@RequestMapping("/buddies") // we can access the methods of this controller at http://localhost:5000/api/buddies
@CrossOrigin(origins = "*") // this exposes this controller to all ports
public class BuddyController {

	@Autowired
	public BuddyResource buddyResource;
	
	// search
		@GetMapping("/find/{user}") // http://localhost:5000/api/buddies/find/user
		public ResponseEntity<List<User>> search(@RequestBody User you)
				throws JSONException {
			return ResponseEntity.ok(buddyResource.FindBuddy(you));
		}
	
}
