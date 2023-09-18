INSERT INTO users (name, email, phone_number) VALUES
('Иванов Иван Иванович', 'ivanov@mail.ru', '+79161234567'),
('Петров Петр Петрович', 'petrov@gmail.com', '+79162345678'),
('Сидорова Анна Сергеевна', 'sidorova@yahoo.com', '+79163456789'),
('Кузнецова Ольга Игоревна', 'kuznetsova@hotmail.com', '+79164567890'),
('Васильев Алексей Николаевич', 'vasiliev@yandex.ru', '+79165678901'),
('Макаров Сергей Дмитриевич', 'makarov@mail.ru', '+79166789012'),
('Тимофеева Наталья Валерьевна', 'timofeeva@gmail.com', '+79167890123'),
('Андреева Елена Игоревна', 'andreeva@yahoo.com', '+79168901234'),
('Козлов Артем Игоревич', 'kozlov@hotmail.com', '+79169012345'),
('Смирнова Екатерина Александровна', 'smirnova@yandex.ru', '+79160123456');

INSERT INTO payments (card_num, card_date, card_cvv) VALUES
('1234567812345678', '10/24', '123'),
('2345678923456789', '11/25', '234'),
('3456789034567890', '12/26', '345'),
('4567890145678901', '01/27', '456'),
('5678901256789012', '02/28', '567');

INSERT INTO orders (user_id, order_date, address, payment_type, payment_id, cost)
VALUES
  (1, '2022-01-01 12:30:00', '123 Main St, Moscow', 'card', 1, 233),
  (2, '2022-01-02 14:45:00', '456 Broadway, St. Petersburg', 'cash', NULL, 715),
  (3, '2022-01-03 11:15:00', '789 5th Ave, Kazan', 'card', 2, 1628),
  (4, '2022-01-04 09:00:00', '111 Lenin St, Samara', 'cash', NULL, 2148),
  (5, '2022-01-05 17:30:00', '222 Pushkin St, Yekaterinburg', 'card', 3, 123),
  (6, '2022-01-06 10:00:00', '333 Gorky St, Novosibirsk', 'card', 4, 1000),
  (7, '2022-01-07 13:00:00', '444 Red Square, Moscow', 'card', 5, 2999),
  (8, '2022-01-08 15:30:00', '555 Tverskaya St, Moscow', 'cash', NULL, 129),
  (9, '2022-01-09 16:15:00', '666 Hermitage, St. Petersburg', 'card', 1, 2183),
  (10, '2022-01-10 12:45:00', '777 Bolshoi Theater, Moscow', 'cash', NULL, 58129);

INSERT INTO user_payments (user_id, payment_id)
VALUES
  (1, 1),
  (2, 3),
  (3, 2),
  (4, 1),
  (5, 2),
  (6, 4),
  (7, 5);
