INSERT INTO USER (email, first_name, last_name, password)
    VALUES ('joe@example.com', 'Joe', 'Simple',
    '$2a$10$.eMnm9R/E9xhOAzKhx0fqer9XKEPLwlmI9Ye.kJ/iYUrBeD4wdOKa');
INSERT INTO role(name) VALUES ('ROLE_USER');

INSERT INTO user_roles(user_id, roles_id) VALUES ((SELECT id FROM USER WHERE email='joe@example.com'),
(SELECT id FROM role WHERE name = 'ROLE_USER'));
