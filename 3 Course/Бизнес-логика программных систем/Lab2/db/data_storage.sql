DROP TABLE IF EXISTS users, payments, orders, user_payments;
DROP TYPE IF EXISTS paymentTypes;

CREATE TYPE paymentTypes AS ENUM ('cash', 'card');

CREATE TABLE users (
  id bigserial PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  phone_number VARCHAR(16) NOT NULL
);

CREATE TABLE payments (
  id bigserial PRIMARY KEY,
  card_num VARCHAR(20) NOT NULL,
  card_date VARCHAR(5) NOT NULL,
  card_cvv VARCHAR(3) NOT NULL
);

CREATE TABLE orders (
  id bigserial PRIMARY KEY,
  user_id bigint REFERENCES users(id) NOT NULL,
  payment_id bigint REFERENCES payments(id),
  cost double precision NOT NULL,
  order_date timestamp NOT NULL,
  address VARCHAR(100) NOT NULL,
  payment_type paymentTypes NOT NULL
);

CREATE TABLE user_payments (
  id bigserial PRIMARY KEY,
  user_id bigint REFERENCES users(id) NOT NULL UNIQUE,
  payment_id bigint REFERENCES payments(id) NOT NULL
);

