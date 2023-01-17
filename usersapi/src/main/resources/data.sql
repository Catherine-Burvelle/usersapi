DROP TABLE IF EXISTS users;
 
CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  birthdate DATE NOT NULL,
  gender VARCHAR(250),
  phone VARCHAR(250),
  country VARCHAR(250) NOT NULL
);
 
INSERT INTO users (first_name, last_name, birthdate, gender, phone, country ) VALUES
  ('Claude', 'DOE', '2000-01-01', 'female', '0102030406', 'FRANCE'),
  ('Jean', 'DOE', '1990-01-01', 'male', '0102030405', 'FRANCE');
