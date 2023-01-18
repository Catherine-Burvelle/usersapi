package fr.burvelle.usersapi.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * User
 * Object that maps a DB row.
 * 
 * @author Catherine BURVELLE
 * 
 */


@Data
@Entity
@Table(name = "users") 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    private Date birthdate;
    
    private String gender;
    
    private String phone;
    
    private String country;

    public int getAge()
    {
    	if (this.birthdate != null)
    	{
        	LocalDate now = LocalDate.now();
    		return Period.between(this.birthdate.toLocalDate(), now).getYears();
    	}
    	return 0;
    }

}
