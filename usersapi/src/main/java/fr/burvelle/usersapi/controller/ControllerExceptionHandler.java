package fr.burvelle.usersapi.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
//import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerExceptionHandler {
	
   @ExceptionHandler({DataRetrievalFailureException.class})
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public String handleDataRetrievalFailureException( Exception ex) {
       return ex.getMessage();
   }
   
   @ExceptionHandler({PermissionDeniedDataAccessException.class})
   @ResponseStatus(HttpStatus.FORBIDDEN)
   public String handlePermissionDeniedDataAccessException( Exception ex) {
       return ex.getMessage();
   }
   
   @ExceptionHandler({DataIntegrityViolationException.class})
   @ResponseStatus(HttpStatus.FORBIDDEN)
   public String handleAccessDeniedException( java.sql.SQLException ex) {
       return ex.getMessage();
   }


}
