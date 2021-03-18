DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, to_timestamp('30-01-2020 10:00', 'dd-mm-yyyy hh24:mi'), 'Завтрак', 500),
       (100000, to_timestamp('30-01-2020 13:00', 'dd-mm-yyyy hh24:mi'), 'Обед', 1000),
       (100000, to_timestamp('30-01-2020 20:00', 'dd-mm-yyyy hh24:mi'), 'Ужин', 500),
       (100000, to_timestamp('31-01-2020 00:00', 'dd-mm-yyyy hh24:mi'), 'Еда на граничное значение', 100),
       (100000, to_timestamp('31-01-2020 10:00', 'dd-mm-yyyy hh24:mi'), 'Завтрак', 1000),
       (100000, to_timestamp('31-01-2020 13:00', 'dd-mm-yyyy hh24:mi'), 'Обед', 500),
       (100000, to_timestamp('31-01-2020 20:00', 'dd-mm-yyyy hh24:mi'), 'Ужин', 410),
       (100001, to_timestamp('01-06-2015 14:00', 'dd-mm-yyyy hh24:mi'), 'Админ ланч', 510),
       (100001, to_timestamp('01-06-2015 21:00', 'dd-mm-yyyy hh24:mi'), 'Админ ужин', 1500);