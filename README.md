# usersapi
Spring boot learning project
This project is entended to answer a technicaltest offer about Java langage in Spring Boot environment.

## Technical test offer
A Springboot API that exposes two services:
 - one that allows to register a user
 - one that displays the details of a registered user

A user is defined by:
 - a user name
 - a birthdate
 - a country of residence

A user has optional attributes:
 - a phone number
 - a gender

Only adult French residents are allowed to create an account!

Inputs must be validated and return proper error messages/http statuses.

## Specific interpretation
I have added endpoints that reads all users, that update a user and that delete a user.

I have also put as properties the age limit and the allowed countries of residence (with Monaco added on top of France).

The project is configured with an embeded H2 database.

Logs are sent to the console.

## Tests
There are unit and functional tests done using Spring Boot facilities, and a Postman collection is also available.

## How To
Once this repository is cloned, maven commands are available:
- mvn clean
- mvn install
- mvn test
- mvn spring-boot:run

# API
When a body is required, the content of this body must be a JSON object with the following content :
```
{
    "firstName": "a_firsname",
    "lastName": "a_lastname",
    "birthdate": "2000-10-01",
    "country": "France",
    "gender": "any",
    "phone": "0102030405"
}
```
"birthdate" must have the format YYYY-mm-dd.

Answers are also JSON objects.

## GET /users
Return an array of all registered users.
## GET /user/{id}
Return the user with identifier 'id'.
## POST /user
Create a new user, "gender" and "phone" are optional.
## PUT /user/{id}
Update the given user with the data from the body.
## DELETE /user/{id}
Delete the given user.


