DROP DATABASE IF EXISTS CollabArt;
CREATE DATABASE CollabArt;
USE CollabArt;

DROP TABLE IF EXISTS users, prompts, coordinates, drawing;



CREATE TABLE `users` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
);

CREATE TABLE `prompts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `statement` varchar(256) NOT NULL,
  `player1` varchar(150) NOT NULL,
  `player2` varchar(150) NOT NULL,
  `player3` varchar(150) NOT NULL,
  `player4` varchar(150) NOT NULL,
   `player5` varchar(150) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE coordinates (
  id int NOT NULL AUTO_INCREMENT,
  topCord int,
  leftCord int,
  bottomCord int,
  rightCord int,
  PRIMARY KEY(`id`),
  FOREIGN KEY (id) REFERENCES prompts(id)
);

CREATE TABLE drawing (
  id int NOT NULL AUTO_INCREMENT,
  image varchar(256),
  likes int,
  dateCreated date,
  createdUser varchar(150),
  promptId int NOT NULL,
  PRIMARY KEY(`id`),
  FOREIGN KEY (createdUser) REFERENCES users(username),
 FOREIGN KEY (promptId) REFERENCES prompts(id)
);