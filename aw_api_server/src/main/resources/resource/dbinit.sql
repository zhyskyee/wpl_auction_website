CREATE DATABASE IF NOT EXISTS auctionweb;

DROP TABLE IF EXISTS `user`;
CREATE TABLE 'user' (
    'userid' int PRIMARY KEY AUTO_INCREMENT,
    'username' varchar(30) unique NOT NULL,
    'password' varchar(32) NOT NULL,
    'email' varchar(50) NOT NULL,
    'phone' varchar(15),
    'photo' mediumblob,
    'last_visit' datetime) engine="innodb";