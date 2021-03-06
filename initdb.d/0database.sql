use reactive_sample;

CREATE TABLE address
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    street varchar(250) NOT NULL,
    number varchar(250) NOT NULL,
    city varchar(250) NOT NULL,
    country varchar(250) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE user
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(250) NOT NULL,
    email varchar(250) NOT NULL,
    age int NOT NULL,
    address_id bigint NULL,
    FOREIGN KEY (address_id) REFERENCES address(`id`)
) ENGINE=InnoDB;
