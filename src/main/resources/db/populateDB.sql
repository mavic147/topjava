DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE meal_global_seq RESTART WITH 1;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (id, user_id, date_time, description, calories)
VALUES (nextval('meal_global_seq'), 100000, '2021-02-21 10:15:17', 'Breakfast', 570),
       (nextval('meal_global_seq'), 100000, '2021-02-21 17:26:19', 'Kebob', 1256),
       (nextval('meal_global_seq'), 100001, '2021-02-20 19:38:21', 'Youghurt', 400);


