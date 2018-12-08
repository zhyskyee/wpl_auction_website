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

DROP TABLE IF EXISTS `item`;
CREATE TABLE 'item' (
    'itemid' int AUTO_INCREMENT,
    'title' varchar(255) unique NOT NULL,
    'ownerid' int not null,
    'address' varchar(255) unique NOT NULL,
    'description' text,
    'photo' mediumblob,
    primary key(itemid,itemname),
    foreign key(ownerid) references user(userid)) engine="innodb";

 
DROP TABLE IF EXISTS `post_item`;
CREATE TABLE 'post_item' (
    'itemid' int not null,
    'ownerid' int not null,
    'auction_date' datetime not null,
    'min_price' decimal(11, 2),
    primary key(itemid, ownerid),
    foreign key(itemid) references item(itemid),
    foreign key(ownerid) references user(userid)) engine="innodb";

DROP TABLE IF EXISTS `bid_item`;
CREATE TABLE 'bid_item' (
    'itemid' int not null,
    'ownerid' int not null,
    'bidderid' int not null,
    'price' decimal(11,2),
    'time_stamp' datetime not null,
    primary key(itemid, ownerid, bidderid),
    foreign key(itemid) references item(itemid),
    foreign key(ownerid) references user(userid),
    foreign key(bidderid) references user(userid)) engine="innodb";