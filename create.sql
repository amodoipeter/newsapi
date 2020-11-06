CREATE DATABASE newss;
\c newss
CREATE TABLE users (
    id serial PRIMARY KEY,
    name varchar,
    position varchar,
    role varchar,
    departmentId int);
CREATE TABLE departments (
    id serial PRIMARY KEY,
    name varchar,
    description varchar);
CREATE TABLE news (
    id serial PRIMARY KEY,
    userId int,
    content varchar,
    postdate timestamp,
    departmentId int,
    type varchar);

CREATE DATABASE newss_test WITH TEMPLATE newss;
