package fr.burvelle.usersapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;

import fr.burvelle.usersapi.model.User;
import fr.burvelle.usersapi.repository.UserRepository;
import lombok.Data;

@Data
@Service
public class UserService {

	@Value( "${fr.burvelle.usersapi.agelimit}" )
	private int ageLimit;
	
	@Value( "${fr.burvelle.usersapi.countrylist}" )
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

    	// Check the country is one of the authorized from the property list
    	boolean countryFound = false;
    	for( int cIdx = 0; cIdx < this.countryList.length; cIdx++)
    	{
    		if (this.countryList[cIdx].equalsIgnoreCase(user.getCountry()))
    		{
    			countryFound = true;
    			break;
    		}
    	}
    	if (! countryFound)
    	{
    		throw new PermissionDeniedDataAccessException("Country "+user.getCountry()+" not allowed.", new Exception());  		
    	}
    	
    	// Check the age is allowed.
    	if (user.getAge() < this.ageLimit) {
    		throw new PermissionDeniedDataAccessException("Age "+user.getAge()+" not allowed.", new Exception());  		
    	}
    	
        User savedUser = userRepository.save(user);
        return savedUser;
    }

}
