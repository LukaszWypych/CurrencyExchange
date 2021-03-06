INSERT INTO countries (id,name) VALUES
(1,'Japan'),
(2,'Germany'),
(3,'Russia');

INSERT INTO currencies (id,code,name) VALUES
(1,'JPY','Yen'),
(2, 'GBP', 'Pound Sterling'),
(3, 'UAH', 'Hryvnia'),
(4, 'EUR', 'Euro'),
(5, 'CHF', 'Swiss Franc'),
(6, 'USD', 'US Dollar');

INSERT INTO currencies_countries (currencies_id, countries_id) VALUES 
(1,1),
(2,1),
(3,1),
(1,2),
(4,2),
(5,2),
(6,2),
(6,3);

INSERT INTO exchange_rates (id,date,rate,currency_id) VALUES
(1,'2021-02-10', 5.0000, 1),
(2,'2021-02-11', 5.0001, 1),
(3,'2021-02-12', 5.0010, 1),
(4,'2021-02-10', 2.1000, 2),
(5,'2021-02-11', 2.1001, 2),
(6,'2021-02-12', 2.1002, 2),
(7,'2021-02-10', 0.1010, 3),
(8,'2021-02-11', 0.1020, 3),
(9,'2021-02-12', 0.1030, 3),
(10,'2021-02-10', 3.0000, 4),
(11,'2021-02-11', 3.5000, 4),
(12,'2021-02-12', 4.0000, 4),
(13,'2021-02-10', 4.3333, 5),
(14,'2021-02-11', 4.4444, 5),
(15,'2021-02-12', 4.5555, 5),
(16,'2021-02-10', 1.1234, 6),
(17,'2021-02-11', 1.4321, 6),
(18,'2021-02-12', 1.2222, 6);

