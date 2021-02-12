create table users (
    id                      bigserial primary key,
    username                varchar(30) not null unique,
    password                varchar(80) not null,
    email                   varchar(50) unique,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

create table roles (
    id                      bigserial primary key,
    name                    varchar(50) not null unique,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

CREATE TABLE users_roles (
    user_id                 bigint not null references users (id),
    role_id                 bigint not null references roles (id),
    primary key (user_id, role_id)
);

create table products (
    id bigserial primary key,
    title varchar(255),
    price integer,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
   );

create table order_items
(
    id bigserial primary key,
    product_id bigserial,
    quantity int,
    price int,
    price_per_product int,
    FOREIGN KEY (product_id)  REFERENCES products (id)
);

insert into products(title, price) values
  ('Apple', 120),
  ('Orange', 100),
  ('Tomato', 150),
  ('Cucumber', 90),
  ('Potato', 30),
  ('Grape', 200),
  ('Watermelon', 220),
  ('Mandarin', 90),
  ('Apple', 150),
  ('Tomato', 110),
  ('Tomato',80 ),
  ('Grape', 130),
  ('Potato', 20),
  ('Orange', 140);

  insert into roles (name)
  values
  ('ROLE_USER'),
  ('ROLE_ADMIN');

  insert into users (username, password, email)
  values
  ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
  ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

  insert into users_roles (user_id, role_id)
  values
  (1, 1),
  (2, 2);
--
-- insert into cart(productId, count) values
--    (3, 1),
--    (5, 6),
--    (10, 3);