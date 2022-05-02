DROP DATABASE IF EXISTS CollabArt;
CREATE DATABASE CollabArt;
USE CollabArt;

DROP TABLE IF EXISTS drawing, likes;



CREATE TABLE `users` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
);





CREATE TABLE drawing (
  id int NOT NULL AUTO_INCREMENT,
  image MEDIUMTEXT,
  likes int,
  dateCreated date,
  createdUsers varchar(256),
  prompt varchar(256),
  PRIMARY KEY(`id`),
  FOREIGN KEY (createdUser) REFERENCES users(username)
);


CREATE TABLE likes (
	picId int NOT NULL,
    username varchar(150),
    likeType boolean

    -- FOREIGN KEY (picId) REFERENCES drawing(id), --
    -- FOREIGN KEY (username) REFERENCES users(username) --
);
-- FOR EASY TESTING PURPOSES (GALLERY FRONTEND)

INSERT into Users (username, password) VALUES ("testUser", "testPassword");
INSERT into drawing (image, likes, dateCreated, createdUser, prompt) VALUES("futureImageURL or path???", 0, curdate(), "testUser", "generic theme");
INSERT into drawing (image, likes, dateCreated, createdUser, prompt) VALUES("second image for testing", 0, curdate(), "testUser", "i have no idea");
INSERT into drawing (image, likes, dateCreated, createdUser, prompt) VALUES("picture number 3", 0, curdate(), "testUser", "uhh the one where you draw crazy stuff");