import org.hibernate.query.Query;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.util.List;

import org.hibernate.Session;

import com.revature.model.Movie;
import com.revature.model.User;
import com.revature.service.MovieResource;
import com.revature.service.MovieService;
import com.revature.service.UserService;
import com.revature.util.HibernateUtil;

public class Driver {
	
	public static void main(String[] args) throws JSONException {
//		MovieService mserv = new MovieService();
//		System.out.println(mserv.insert(550, 1));
		
		UserService userv = new UserService();
		User u = new User("jochen", "pendleton", "jochenp", "password", "jochenp@revature.net");
		System.out.println(userv.insert(u));
		
				
	}

}
