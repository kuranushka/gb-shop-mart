insert into account_user (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
values ('user', '$2a$04$PypzYpRN8JlCr0.wljh15u9eWfzVWoCM5fURaWgkOowg4OV5ltDyO', true, true, true, true),
       ('admin', '$2a$04$PypzYpRN8JlCr0.wljh15u9eWfzVWoCM5fURaWgkOowg4OV5ltDyO', true, true, true, true),
       ('manager', '$2a$04$PypzYpRN8JlCr0.wljh15u9eWfzVWoCM5fURaWgkOowg4OV5ltDyO', true, true, true, true);


insert into authority (permission)
values ('product.create'),
       ('product.read'),
       ('product.update'),
       ('product.delete'),
       ('product.add.to.cart'),
       ('product.update.in.cart'),
       ('product.delete.from.cart'),
       ('save.cart'),
       ('user.account.create'),
       ('user.account.read'),
       ('user.account.update'),
       ('user.account.delete'),
       ('manager.account.create'),
       ('manager.account.read'),
       ('manager.account.update'),
       ('manager.account.delete'),
       ('admin.account.create'),
       ('admin.account.read'),
       ('admin.account.update'),
       ('admin.account.delete');

insert into account_role (name)
values ('USER'),
       ('ADMIN'),
       ('MANAGER');

insert into user_role (user_id, role_id)
values (1, 1),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 3);

insert into role_authority (authority_id, role_id)
values (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2),
       (7, 2),
       (8, 2),
       (9, 2),
       (10, 2),
       (11, 2),
       (12, 2),
       (13, 2),
       (14, 2),
       (15, 2),
       (16, 2),
       (17, 2),
       (18, 2),
       (19, 2),
       (20, 2),
       (1, 3),
       (2, 3),
       (3, 3),
       (4, 3),
       (5, 3),
       (6, 3),
       (7, 3),
       (8, 3);