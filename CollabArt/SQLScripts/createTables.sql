DROP DATABASE IF EXISTS CollabArt;
CREATE DATABASE CollabArt;
USE CollabArt;

DROP TABLE IF EXISTS users, drawing;



CREATE TABLE `users` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
);

-- CREATE TABLE `prompts` (
--   `id` int NOT NULL AUTO_INCREMENT,
--   `statement` varchar(256) NOT NULL,
--   `player1` varchar(150) NOT NULL,
--   `player2` varchar(150) NOT NULL,
--   `player3` varchar(150) NOT NULL,
--   `player4` varchar(150) NOT NULL,
--    `player5` varchar(150) NOT NULL,
--   PRIMARY KEY (`id`)
-- );


-- CREATE TABLE coordinates (
--   id int NOT NULL AUTO_INCREMENT,
--   topCord int,
--   leftCord int,
--   bottomCord int,
--   rightCord int,
--   PRIMARY KEY(`id`),
--   FOREIGN KEY (id) REFERENCES prompts(id)
-- );

CREATE TABLE drawing (
  id int NOT NULL AUTO_INCREMENT,
  image varchar(256),
  likes int,
  dateCreated date,
  createdUser varchar(150),
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