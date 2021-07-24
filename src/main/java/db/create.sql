create table posts (
id serial primary key,
name varchar(255),
description text,
link text UNIQUE,
created timestamp
);