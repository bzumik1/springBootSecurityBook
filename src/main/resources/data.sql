DELETE FROM user_role;
DELETE FROM roles;
DELETE FROM users;


INSERT INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

INSERT INTO users (id, email, password) VALUES
(1,'admin@gmail.com', '$2a$10$An1oAYA49n4Te/izJlULbey0rNqVg1Kv0d5hzaz0zRtMI2j.bNlNm');

INSERT INTO user_role (user_id, role_id) VALUES
(1,1);

