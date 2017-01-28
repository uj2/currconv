insert into user (email, first_name, last_name, password)
    values ('joe@example.com', 'Joe', 'Simple',
    '$2a$10$.eMnm9R/E9xhOAzKhx0fqer9XKEPLwlmI9Ye.kJ/iYUrBeD4wdOKa');
insert into role(name) values ('ROLE_USER');

insert into user_roles(user_id, roles_id) values ((select id from user where email='joe@example.com'),
(select id from role where name = 'ROLE_USER'));
