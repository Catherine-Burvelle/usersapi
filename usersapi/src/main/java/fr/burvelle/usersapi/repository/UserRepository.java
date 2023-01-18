package fr.burvelle.usersapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.burvelle.usersapi.model.User;

/**
 * UserRepository
 * Class that Create/Read/Update/Delete rows in DB.
 * 
 * @author Catherine BURVELLE
 */


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
