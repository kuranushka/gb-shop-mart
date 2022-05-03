insert into authority (permission)
values ('product.create'),
       ('product.read'),
       ('product.update'),
       ('product.delete');

insert into account_role (name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into account_role_authorities(authorities_id, roles_id)
values(1,1),
      (2,1),
      (3,1),
      (4,1),
      (2,2);