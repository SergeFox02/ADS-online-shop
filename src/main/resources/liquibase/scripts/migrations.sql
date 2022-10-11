-- liquibase formatted sql

-- changeSet algmironov:1

CREATE TABLE users
(
    id                   SERIAL NOT NULL PRIMARY KEY,
    username                varchar(255) NOT NULL,
    password             varchar(255) NOT NULL,
    first_name           varchar(255) NOT NULL,
    last_name            varchar(255) NOT NULL,
    phone                varchar(255) NOT NULL,
    role                 varchar(255) NOT NULL DEFAULT 'USER'
);

CREATE TABLE ads
(
    id                  SERIAL NOT NULL PRIMARY KEY,
    title               varchar(255) NOT NULL,
    description         TEXT NOT NULL,
    price               bigint NOT NULL,
    author              bigint NOT NULL references users(id)
);

CREATE TABLE comments
(
    id                  SERIAL NOT NULL PRIMARY KEY,
    text                TEXT NOT NULL,
    author              bigint NOT NULL references users(id),
    ads                 bigint NOT NULL references ads(id),
    created_at          timestamp NOT NULL
);

-- changeSet Serge:2
CREATE TABLE images(
                       id           bigserial   NOT NULL    PRIMARY KEY,
                       file_path    text        NOT NULL    UNIQUE,
                       file_size    bigint      NOT NULL,
                       media_type   text        NOT NULL,
                       data         bytea       NOT NULL,
                       ads_id       bigint      NOT NULL    references ads(id)
);


-- changeSet algmironov:3
ALTER TABLE ads add column image_id bigserial references images(id);