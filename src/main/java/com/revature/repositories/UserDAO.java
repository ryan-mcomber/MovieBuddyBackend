package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.model.Address;
import com.revature.model.User;

@Repository // CrudRepository -> PagingAndSortingRepository -> JpaRepository
public interface UserDAO extends JpaRepository<User, Integer>{ 

	/*
	 * This is a Property Expression
	 * Property Expressions infer SQL statements on entities 
	 * based on the DIRECT properties of the entity being managed.
	 */
	
	public Optional<User> findByUsername(String username);

	// @Modifying  // I would use this if I intend for some method to manipulate and modify data in a DB
	@Query(value= "FROM User WHERE email LIKE %:substr") // Note that I'm calling the Java properties and object name -> this is JPQL 
	public List<User> findByEmailContains(String substr); // johnsmi -> reutrns johnsmith@gmail.com
	
	public List<User> findByOrderByLastName();
	
	public void deleteById(int id);
	
	public User findById(int id);
	
	// basic CRUD methods inherited from CrudRepository save(), get(), update(), etc...
}
