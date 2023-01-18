package fr.burvelle.usersapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;

import fr.burvelle.usersapi.model.User;
import fr.burvelle.usersapi.repository.UserRepository;
import lombok.Data;

/**
 * UserService
 * Service that ensure the data consistency.
 * 
 * @author Catherine BURVELLE
 */


@Data
@Service
public class UserService {

	@Value( "${fr.burvelle.usersapi.agelimit:18}" )
	private int ageLimit;
	
	@Value( "${fr.burvelle.usersapi.countrylist:France}" )
	private String[] countryList;
	
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(final Long id) {
        return userRepository.findById(id);
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    public User saveUser(User user) {

    	// Check Name and Firstname are not empty and format them.
    	String firstname = user.getFirstName();
		if(firstname == null || firstname.isBlank()) 
		{
			throw new DataIntegrityViolationException("Firstname is missing or blank");
		} else {
			user.setFirstName(firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase());
		}
    	String lastname = user.getLastName();
		if (lastname == null || lastname.isBlank()) 
		{
			throw new DataIntegrityViolationException("Lastname is missing or blank");
		} else {
			user.setLastName(lastname.toUpperCase());
		}

    	// Check the country is one of the authorized from the property list
    	String country = user.getCountry();
    	if (country == null || country.isBlank())
    	{
    		throw new DataIntegrityViolationException("Country is missing or blank");
    	}
    	boolean countryFound = false;
    	for( int cIdx = 0; cIdx < this.countryList.length; cIdx++)
    	{
    		if (this.countryList[cIdx].equalsIgnoreCase(country))
    		{
    			countryFound = true;
    			break;
    		}
    	}
    	if (! countryFound)
    	{
    		throw new PermissionDeniedDataAccessException("Country "+country+" is not allowed.", new Exception());  		
    	}
    	
    	// Check the age is allowed.
    	if (user.getBirthdate() == null)
    	{
    		throw new DataIntegrityViolationException("Birthdate is missing");
    	}
    	if (user.getAge() < this.ageLimit) {
    		throw new PermissionDeniedDataAccessException("Age "+user.getAge()+" is not allowed.", new Exception());  		
    	}
    	
        User savedUser = userRepository.save(user);
        return savedUser;
    }

}
