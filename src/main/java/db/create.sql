create table posts (
id serial primary key,
name varchar(255),
description text UNIQUE,
link text,
created timestamp
);