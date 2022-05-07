
DROP SCHEMA if EXISTS `heroku_6a11113fbc7e4d0`;
CREATE SCHEMA IF NOT EXISTS `heroku_6a11113fbc7e4d0` DEFAULT CHARACTER SET utf8mb4;

USE heroku_6a11113fbc7e4d0;
DROP TABLE IF EXISTS users, drawings, likes;


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

