package fr.burvelle.usersapi.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.burvelle.usersapi.model.User;
import fr.burvelle.usersapi.service.UserService;

import java.util.Optional;

/**
 * UserController
 * Class handling the mapping between HTTP request and the relevant service
 * 
 * @author Catherine BURVELLE
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    /**
	 * Create - Add a new user
	 * @param user An object user
	 * @return The user object saved
	 */
	@PostMapping("/user")
	public User createUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	
	/**
	 * Read - Get one user 
	 * @param id The id of the user
	 * @return An User object full filled
	 */
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable("id") final Long id) {
		Optional<User> user = userService.getUser(id);
		if( ! user.isPresent()) {
			throw new DataRetrievalFailureException("User " + id + " not found.");
		}
		
		return user.get();
	}
	
	/**
	 * Read - Get all users
	 * @return - An Iterable object of User full filled
	 */
	@GetMapping("/users")
	public Iterable<User> getUsers() {
		return userService.getUsers();
	}
	
	/**
	 * Update - Update an existing user
	 * @param id - The id of the user to update
	 * @param user - The user object updated
	 * @return
	 */
	@PutMapping("/user/{id}")
	public User updateUser(@PathVariable("id") final Long id, @RequestBody User user) {
		Optional<User> e = userService.getUser(id);
		if( ! e.isPresent()) {
			throw new DataRetrievalFailureException("User " + id + " not found.");
		}

		User currentUser = e.get();
		
		String firstName = user.getFirstName();
		if (firstName != null)
		{
			currentUser.setFirstName(firstName);
		}
		String lastName = user.getLastName();
		if (lastName != null)
		{
			currentUser.setLastName(lastName);
		}
		Date birthdate = user.getBirthdate();
		if (birthdate != null)
		{
			currentUser.setBirthdate(birthdate);
		}
		String gender = user.getGender();
		if (gender != null)
		{
			currentUser.setGender(gender);
		}
		String phone = currentUser.getPhone();
		if ( phone != null)
		{
			currentUser.setPhone(phone);
		}
		String country = currentUser.getCountry();
		if( country != null)
		{
			currentUser.setCountry(country);
		}
		return userService.saveUser(currentUser);

	}
	
	
	/**
	 * Delete - Delete an user
	 * @param id - The id of the user to delete
	 */
	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable("id") final Long id) {
		userService.deleteUser(id);
	}
}
