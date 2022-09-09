-- liquibase formatted sql

-- changeset algmironov:1

CREATE TABLE users
(
    id                   SERIAL NOT NULL PRIMARY KEY,
    email                varchar(255) NOT NULL,
    password             varchar(255) NOT NULL,
    first_name           varchar(255) NOT NULL,
    last_name            varchar(255) NOT NULL,
    phone                varchar(255) NOT NULL,
    role                 varchar(255) NOT NULL DEFAULT 'USER'
);

-- changeset algmironov:2

CREATE TABLE ads
(
    pk                  SERIAL NOT NULL PRIMARY KEY,
    title               varchar(255) NOT NULL,
    description         TEXT NOT NULL,
    image               varchar(255) NOT NULL,
    price               float NOT NULL,
    author              bigint NOT NULL references users(id)
);

-- changeset algmironov:3

CREATE TABLE comments
(
    pk                  SERIAL NOT NULL PRIMARY KEY,
    text                TEXT NOT NULL,
    author              bigint NOT NULL references users(id),
    ads                 bigint NOT NULL references ads(pk),
    created_at          timestamp NOT NULL
);