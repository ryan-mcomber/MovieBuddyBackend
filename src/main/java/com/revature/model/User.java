package com.revature.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="users")
@Data @NoArgsConstructor @AllArgsConstructor
public class User implements Serializable {
	
	@Id
	@Column(name="user_id", nullable=false, unique=true, updatable=false) // non-nullable and unique =tru is overkill because it's serial primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Length(min=2)
	private String firstName;
	
	// here we're just not adding a constraint
	private String lastName;
	
	@NotBlank
	@Length(min=2)
	@Pattern(regexp="[a-zA-Z][a-zA-Z0-9]*") // make sure a username doens't contain spaces and ONLY alphanumeric character
	private String username;
	
	@NotEmpty
	@Length(min=4)
	private String password;
	
	@Email // must contain @ and .something
	private String email;
	

}
