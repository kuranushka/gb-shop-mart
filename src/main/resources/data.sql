INSERT INTO category (created_by, created_date, title)
VALUES ('User', CURRENT_TIMESTAMP, 'Fruits'),
       ('User', CURRENT_TIMESTAMP, 'Grocery'),
       ('User', CURRENT_TIMESTAMP, 'Bread');


INSERT INTO manufacturer (created_by, created_date, name)
VALUES ('User', CURRENT_TIMESTAMP, 'Unilever'),
       ('User', CURRENT_TIMESTAMP, 'Heinz'),
       ('User', CURRENT_TIMESTAMP, 'Nestle');

INSERT INTO product (created_by, created_date, cost, manufacture_date, status, title, manufacturer_id)
VALUES ('User', CURRENT_TIMESTAMP, 10.99, CURRENT_TIMESTAMP, 'ACTIVE', 'Apple', 1),
       ('User', CURRENT_TIMESTAMP, 20.99, CURRENT_TIMESTAMP, 'DISABLED', 'Grapes', 2),
       ('User', CURRENT_TIMESTAMP, 30.99, CURRENT_TIMESTAMP, 'ACTIVE', 'Banana', 3),

       ('User', CURRENT_TIMESTAMP, 13.99, CURRENT_TIMESTAMP, 'ACTIVE', 'Tomato', 1),
       ('User', CURRENT_TIMESTAMP, 23.99, CURRENT_TIMESTAMP, 'DISABLED', 'Cucumber', 2),
       ('User', CURRENT_TIMESTAMP, 32.99, CURRENT_TIMESTAMP, 'DISABLED', 'Potato', 3),

       ('User', CURRENT_TIMESTAMP, 33.99, CURRENT_TIMESTAMP, 'ACTIVE', 'Roll', 1),
       ('User', CURRENT_TIMESTAMP, 233.99, CURRENT_TIMESTAMP, 'ACTIVE', 'Cookie', 2),
       ('User', CURRENT_TIMESTAMP, 322.99, CURRENT_TIMESTAMP, 'ACTIVE', 'Cake', 3);

INSERT INTO product_category (category_id, product_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 7),
       (3, 8),
       (3, 9);