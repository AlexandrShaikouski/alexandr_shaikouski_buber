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
        email VARCHAR(45) NOT NULL UNIQUE,
        phone VARCHAR(13) NOT NULL UNIQUE,
				status ENUM('off-line', 'online', 'in-progress') NOT NULL,
        registration_date LONG NOT NULL,
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


INSERT INTO role
(name)
VALUES ('admin'),
			 ('driver'),
			 ('client');
INSERT INTO bonus
(name, factor)
VALUES ('light', 0.05),
			 ('medium', 0.10),
			 ('hard', 0.15),
			 ('free', 1);
INSERT INTO user_account
(login, password, first_name, email, phone, status, registration_date, role_id)
VALUES ('admin', '8df79deb606c9a5955d4d7fbd93037c29f736659b06aef73a377626dcd404b41', 'Admin', 'admin@admin.ru',
				'+375251111111', 'off-line', '0', '1'),
			 ('driver', 'AE788F9D08032173D63F16A059685C9B0890F2FA1128C84859D508AAE58E9D02', 'Driver', 'driver@driver.ru',
				'+375252222222', 'off-line', '0', '2'),
			 ('client', 'A3FA61A1980CDDB31E03B803B03B43C7D1638ECE2620ABACEF41CD7BF75AB216', 'Client', 'client@client.ru',
				'+375253333333', 'off-line', '0', '3');
	
	
	
	
	
	