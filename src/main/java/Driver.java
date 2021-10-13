import org.hibernate.query.Query;

import java.util.List;

import org.hibernate.Session;

import com.revature.model.Movie;
import com.revature.service.MovieResource;
import com.revature.util.HibernateUtil;

public class Driver {
	
	public static void main(String[] args) {
		MovieResource m = new MovieResource();
		System.out.println(m.getRecommendationId(2));
		System.out.println(m.getRecommendationId(2));
		System.out.println(m.getRecommendationId(2));
		System.out.println(m.getRecommendationId(2));
		System.out.println(m.getRecommendationId(2));
		System.out.println(m.getRecommendationId(2));
		
				
	}

}
