CREATE TABLE IF NOT  EXISTS clients
(
    id         BIGSERIAL PRIMARY KEY,
    login      VARCHAR(50) UNIQUE ,
    firstName  VARCHAR(50) NOT NULL,
    secondName VARCHAR(50) NOT NULL,
    middleName VARCHAR(50) NOT NULL,
    password   VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS cards
(
    id          BIGSERIAL primary key,
    number      VARCHAR(20) not null unique ,
    ownerLogin  varchar(50) not null,
    remeins     decimal(10, 2) not null ,
    foreign key (ownerLogin) references clients (login)
);



CREATE TABLE IF NOT EXISTS roles
(
    id      BIGSERIAL primary key not null,
    name    VARCHAR(100) not null unique
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id bigint,
    role_id bigint,
    foreign key (user_id) references clients (id),
    foreign key (role_id) references roles (id)
);


insert into clients (login, firstname, secondname, middlename, password) values ('dima_kuprik', 'Dima', 'Kuprik', 'Anatolevich', '$2a$04$LAJiulMMYbhQ0IGcgAwLH.tNYUSGHYLLiKeMxmqEdnkb9qf77yvcW');
insert into clients (login, firstname, secondname, middlename, password) values ('ivan_petrov', 'Ivan', 'Petrov', 'Sergeevich', '$2a$04$LAJiulMMYbhQ0IGcgAwLH.tNYUSGHYLLiKeMxmqEdnkb9qf77yvcW');
insert into clients (login, firstname, secondname, middlename, password) values ('sasha_petrov', 'Sasha', 'Petrov', 'Sergeevich', '$2a$04$LAJiulMMYbhQ0IGcgAwLH.tNYUSGHYLLiKeMxmqEdnkb9qf77yvcW');
insert into cards (number, ownerlogin, remeins) values ('1111222233334444', 'dima_kuprik', 1000.00);
insert into cards (number, ownerlogin, remeins) values ('0000111100001111', 'ivan_petrov', 1000.00);
insert into cards (number, ownerlogin, remeins) values ('4444444444444444', 'dima_kuprik', 1000.00);
insert into cards (number, ownerlogin, remeins) values ('3333333333333333', 'sasha_petrov', 1000.00);
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO users_roles VALUES (1, 1);
