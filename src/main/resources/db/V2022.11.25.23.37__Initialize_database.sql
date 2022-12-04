CREATE SCHEMA IF NOT EXISTS security;

CREATE TABLE IF NOT EXISTS security.users
(
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE,
    password varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS security.roles
(
    id SERIAL PRIMARY KEY,
    role varchar(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS security.user_roles
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO security.users (name, password) values ('manager', 'manager');
INSERT INTO security.users (name, password) values ('guest', 'guest');

INSERT INTO security.roles(role) values ('Manager');
INSERT INTO security.roles(role) values ('Guest');

INSERT INTO security.user_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO security.user_roles(user_id, role_id) VALUES (1, 2);
INSERT INTO security.user_roles(user_id, role_id) VALUES (2, 2  );

CREATE TABLE ORGANIZATION
(
    id   BIGSERIAL PRIMARY KEY,
    name varchar(64) NOT NULL UNIQUE
);

CREATE TABLE PRODUCT
(
    id              BIGSERIAL PRIMARY KEY,
    organization_id int8 references ORGANIZATION (id) NOT NULL,
    name            varchar(64)                       NOT NULL UNIQUE,
    amount          int                               NOT NULL DEFAULT 0
);


INSERT INTO ORGANIZATION(name) values ('Organization 1');
INSERT INTO ORGANIZATION(name) values ('Organization 2');

INSERT INTO PRODUCT(organization_id, name, amount) VALUES (1, 'product 1', 10);
INSERT INTO PRODUCT(organization_id, name, amount) VALUES (1, 'product 2', 35);
INSERT INTO PRODUCT(organization_id, name, amount) VALUES (2, 'product 3', 4);
