delete
from users_roles;
delete
from eve_assist_user;
insert into public.eve_assist_user (id, create_date, email, password, user_unique, screen_name)
values (1, '2023-06-10 01:34:45.000000', 'test@test.com', 'asdf', '123456789012345678901234567890', 'test user'),
       (2, '2023-06-10 06:55:38.000000', 'test2@test.com', 'qwer', '12345678901234567890123456789A',
        'another test user'),
       (3, '2023-06-10 06:55:38.000000', 'test3@test.com', 'qwer', '12345678901234567890123456789B', 'test3 user');

insert into users_roles (user_id, role_id)
VALUES (1, 0),
       (2, 1),
       (2, 2),
       (3, 2);
