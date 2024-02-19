CREATE DATABASE db_simpeg_development;

USE db_simpeg_development;

CREATE TABLE users
(
    username    VARCHAR(100) NOT NULL,
    password    VARCHAR(100) NOT NULL,
    name        VARCHAR(100) NOT NULL,
    token       VARCHAR(100),
    token_expired_at    BIGINT,
    PRIMARY KEY (username),
    UNIQUE (token)
);

CREATE TABLE departments
(
    code VARCHAR(4) NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (code)
);