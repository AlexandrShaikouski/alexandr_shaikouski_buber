DROP DATABASE IF EXISTS buber;
CREATE DATABASE buber;
use buber;
CREATE TABLE role (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(10) NOT NULL UNIQUE);
CREATE TABLE user_account (
        id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        login VARCHAR(45) NOT NULL UNIQUE,
        password CHAR(64) NOT NULL,
				repassword_key CHAR(64),
        first_name VARCHAR(45) NOT NULL,
        last_name VARCHAR(45),
        email VARCHAR(45) NOT NULL UNIQUE,
        phone VARCHAR(13) NOT NULL UNIQUE,
				status ENUM('off-line', 'online', 'in-progress') NOT NULL,
        registration_date LONG NOT NULL,
				location VARCHAR(45),
				status_ban LONG,
        role_id INT  NOT NULL,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE);
CREATE TABLE bonus (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(45) NOT NULL UNIQUE,
	factor FLOAT NOT NULL);
CREATE TABLE user_bonus (
		id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
		bonus_id INT NOT NULL,
		user_id INT NOT NULL,
	CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user_account (id) ON DELETE CASCADE,
    CONSTRAINT fk_bonus FOREIGN KEY (bonus_id) REFERENCES bonus (id) ON DELETE CASCADE);
CREATE TABLE trip_order (
		id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
		from_x VARCHAR(45) NOT NULL,
		to_y VARCHAR(45) NOT NULL,
		status_order ENUM('waiting','pending','in-progress','complete') NOT NULL,
		price FLOAT NOT NULL,
		date_create LONG NOT NULL,
		client_id INT NOT NULL,
		driver_id INT,
		bonus_id INT,
	CONSTRAINT fk_user_client FOREIGN KEY (client_id) REFERENCES user_account (id) ON DELETE CASCADE,
	CONSTRAINT fk_user_driver FOREIGN KEY (driver_id) REFERENCES user_account (id) ON DELETE CASCADE,
  CONSTRAINT fk_order_bonus FOREIGN KEY (bonus_id) REFERENCES bonus (id) ON DELETE CASCADE);
	
	
	
	
	
	
	
	
	