create database Accomatch;
use Accomatch;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    mobile VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    is_admin TINYINT(1) NOT NULL,
    is_leaseholder TINYINT(1) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

-- Create Leaseholder Ads table
CREATE TABLE leaseholder_ads (
    leaseholder_application_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    subtitle VARCHAR(255),
    address VARCHAR(255),
    location_city VARCHAR(255),
    size INT,
    room_type VARCHAR(255),
    document VARCHAR(255),
    rent DECIMAL(10, 2),
    other_preferences TEXT,
    start_date DATE,
    start_age INT,
    end_age INT,
    is_verified TINYINT(1),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create HouseSeeker Ads table
CREATE TABLE houseseeker_ads (
    houseseeker_application_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    location_city VARCHAR(255),
    other_preferences TEXT,
    room_type VARCHAR(255),
    start_date DATE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create Leaseholder Food Preferences table
CREATE TABLE leaseholder_food_preferences (
    leaseholder_food_pref_id INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT NOT NULL,
    food_pref VARCHAR(255),
    FOREIGN KEY (application_id) REFERENCES leaseholder_ads(leaseholder_application_id)
);

-- Create Leaseholder Images table
CREATE TABLE leaseholder_images (
    leaseholder_image_id INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT NOT NULL,
    image_link VARCHAR(255),
    FOREIGN KEY (application_id) REFERENCES leaseholder_ads(leaseholder_application_id)
);

-- Create Leaseholder Gender Preferences table
CREATE TABLE leaseholder_gender_preferences (
    leaseholder_gender_pref_id INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT NOT NULL,
    gender_pref VARCHAR(10),
    FOREIGN KEY (application_id) REFERENCES leaseholder_ads(leaseholder_application_id)
);

-- Create HouseSeeker Food Preferences table
CREATE TABLE houseseeker_food_preferences (
    houseseeker_food_pref_id INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT NOT NULL,
    food_pref VARCHAR(255),
    FOREIGN KEY (application_id) REFERENCES houseseeker_ads(houseseeker_application_id)
);

-- Create HouseSeeker Gender Preferences table
CREATE TABLE houseseeker_gender_preferences (
    houseseeker_gender_pref_id INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT NOT NULL,
    gender_pref VARCHAR(10),
    FOREIGN KEY (application_id) REFERENCES houseseeker_ads(houseseeker_application_id)
);

-- Create Leaseholder Applicant table
CREATE TABLE leaseholder_applicants (
    leaseholder_applicant_id INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT NOT NULL,
    user_id INT NOT NULL,
    status VARCHAR(255),
    room_id INT,
    FOREIGN KEY (application_id) REFERENCES leaseholder_ads(leaseholder_application_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create HouseSeeker Applicant table
CREATE TABLE houseseeker_applicants (
    houseseeker_applicant_id INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT NOT NULL,
    user_id INT NOT NULL,
    status VARCHAR(255),
    room_id INT,
    FOREIGN KEY (application_id) REFERENCES houseseeker_ads(houseseeker_application_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
-- Create Room table
CREATE TABLE rooms (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    user_1_id INT NOT NULL,
    user_2_id INT NOT NULL,
    FOREIGN KEY (user_1_id) REFERENCES users(user_id),
    FOREIGN KEY (user_2_id) REFERENCES users(user_id)
);
-- Create Chat table
CREATE TABLE chats (
    message_id INT PRIMARY KEY AUTO_INCREMENT,
    room_id INT NOT NULL,
    user_id INT NOT NULL,
    message TEXT,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);



-- Create Rating table
CREATE TABLE rating_table (
    rating_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    application_id INT,
    rating INT,
    review TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (application_id) REFERENCES leaseholder_ads(leaseholder_application_id)
);

-- Create Leaseholder Current Residents table
CREATE TABLE leaseholder_current_residents (
    application_id INT PRIMARY KEY,
    user_id INT NOT NULL,
    FOREIGN KEY (application_id) REFERENCES leaseholder_ads(leaseholder_application_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);