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

insert into user (username, password, email) values ('admin', '21232f297a57a5a743894a0e4a801fc3', 'admin');

DROP TABLE IF EXISTS `item`;
CREATE TABLE item (
    itemid int AUTO_INCREMENT,
    title varchar(255) unique NOT NULL,
    ownerid int not null,
    address varchar(255) unique NOT NULL,
    description text,
    photo mediumblob,
    auction_date datetime not null,
    min_price decimal(11, 2),
    deal_price decimal(11, 2),
    primary key(itemid,address),
    foreign key(ownerid) references user(userid)) engine="innodb";

DROP TABLE IF EXISTS bid_item;
CREATE TABLE bid_item (
    itemid int not null,
    bidderid int not null,
    price decimal(11,2) not null,
    primary key(itemid, bidderid, price),
    foreign key(itemid) references item(itemid),
    foreign key(bidderid) references user(userid)) engine="innodb";