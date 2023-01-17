package fr.burvelle.usersapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.burvelle.usersapi.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
