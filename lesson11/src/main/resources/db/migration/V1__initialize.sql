create table users (
    id bigserial primary key,
    username varchar(30) not null,
    password varchar(255) not null
);

create table roles (
    id serial  primary key,
    name varchar(30) not null
);

create table users_roles (
    user_id bigint not null,
    role_id int not null,
    name varchar(30) not null,
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

create table scores (
    score int not null,
    user_id bigserial not null primary key,
    foreign key (user_id) references users (id)
);

insert into users(username, password) values
('Bob', '$2y$12$xA14nvuE8evnv66dL/9nSOlyYPaa9D2DkJpClZ.h5oHdUNiFKTBFi'),
('Max', '$2y$12$SQqfNnlTf4AMziSbERmfLuiSHdlBJYekQOhpR6fE/BGVXXW3iS5zm'),
('Admin', '$2y$12$F2kCqxjGX8JZrRCmOSkH1euxBqaTDb.Rn2xSf/78nCDRLCW6nYhG');

insert into roles(name) values
('ROLE_USER');

insert into scores(score, user_id) values
(24, 1),
(12, 2),
(50, 3);



