create table products (
    id bigserial primary key,
    title varchar(255),
    price integer,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
   );

create table cart
(
    id bigserial primary key,
    productId bigserial,
    FOREIGN KEY (productId)  REFERENCES products (id)
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

 insert into cart(productId) values
    (3),
    (5),
    (10);