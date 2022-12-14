-- liquibase formatted sql

-- changeSet algmironov:1

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

-- changeSet algmironov:2

CREATE TABLE ads
(
    pk                  SERIAL NOT NULL PRIMARY KEY,
    title               varchar(255) NOT NULL,
    description         TEXT NOT NULL,
    image               varchar(255) NOT NULL,
    price               float NOT NULL,
    author              bigint NOT NULL references users(id)
);

-- changeSet algmironov:3

CREATE TABLE comments
(
    pk                  SERIAL NOT NULL PRIMARY KEY,
    text                TEXT NOT NULL,
    author              bigint NOT NULL references users(id),
    ads                 bigint NOT NULL references ads(pk),
    created_at          timestamp NOT NULL
);

-- changeSet Serge:4
CREATE TABLE image(
                       id           bigserial NOT NULL PRIMARY KEY,
                       file_path    text NOT NULL UNIQUE,
                       file_size    bigint NOT NULL,
                       media_type   text NOT NULL,
                       data         bytea NOT NULL
);

-- changeSet Serge:5
ALTER TABLE ads ALTER COLUMN price TYPE bigint;

-- changeSet Serge:6
ALTER TABLE ads RENAME COLUMN pk TO id;

-- changeSet Serge:7
ALTER TABLE ads RENAME COLUMN image TO image_id;

-- changeSet Serge:8
ALTER TABLE ads ALTER COLUMN image_id TYPE bigint USING image_id ::bigint;

-- changeSet Serge:9
ALTER TABLE ads ADD CONSTRAINT imageIdReferences FOREIGN KEY (image_id) REFERENCES image(id);

-- changeSet Serge:10
ALTER TABLE comments RENAME COLUMN pk TO id;

