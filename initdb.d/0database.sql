use reactive_sample;

CREATE TABLE user
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(250) NOT NULL,
    email varchar(250) NOT NULL,
    password varchar(250) NOT NULL,
    age int NOT NULL
) ENGINE=InnoDB;

CREATE TABLE address
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    street varchar(250) NOT NULL,
    number varchar(250) NOT NULL,
    city varchar(250) NOT NULL,
    country varchar(250) NOT NULL
) ENGINE=InnoDB;
