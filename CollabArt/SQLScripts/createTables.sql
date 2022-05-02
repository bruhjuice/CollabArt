USE heroku_6a11113fbc7e4d0;
DROP TABLE IF EXISTS drawings, likes;


CREATE TABLE `users` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
);


CREATE TABLE drawings (
  id int NOT NULL AUTO_INCREMENT,
  image MEDIUMTEXT,
  likes int,
  dateCreated date,
  createdUsers varchar(256),
  prompt varchar(256),
  PRIMARY KEY(`id`)
);


CREATE TABLE likes (
	picId int NOT NULL,
    username varchar(150),
    likeType boolean

);





INSERT into Users (username, password) VALUES ("testUser", "testPassword");
INSERT into drawing (image, likes, dateCreated, createdUser, prompt) VALUES("futureImageURL or path???", 0, curdate(), "testUser", "generic theme");
INSERT into drawing (image, likes, dateCreated, createdUser, prompt) VALUES("second image for testing", 0, curdate(), "testUser", "i have no idea");
INSERT into drawing (image, likes, dateCreated, createdUser, prompt) VALUES("picture number 3", 0, curdate(), "testUser", "uhh the one where you draw crazy stuff");