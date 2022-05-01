DELETE
FROM product;
DELETE
FROM category;
DELETE
FROM manufacturer;
DELETE
FROM product_category;
DELETE
FROM product_order;
DELETE
FROM shop_order;


INSERT INTO category (created_by, created_date, title)
VALUES ('User', CURRENT_TIMESTAMP, 'Fruits'),
       ('User', CURRENT_TIMESTAMP, 'Grocery'),
       ('User', CURRENT_TIMESTAMP, 'Bread');


INSERT INTO manufacturer (created_by, created_date, name)
VALUES ('User', CURRENT_TIMESTAMP, 'Unilever'),
       ('User', CURRENT_TIMESTAMP, 'Heinz'),
       ('User', CURRENT_TIMESTAMP, 'Nestle');

INSERT INTO product (created_by, created_date, cost, manufacture_date, status, title, manufacturer_id)
VALUES ('User', 'Dec 27, 2019', '84.45', 'Mar 17, 2020', 'DISABLED', 'cranberry', 2),
       ('User', 'Dec 22, 2021', '73.05', 'Jun 4, 2019', 'DISABLED', 'lentils', 1),
       ('User', 'Apr 29, 2019', '99.81', 'May 18, 2021', 'DISABLED', 'pineapple', 2),
       ('User', 'Mar 4, 2019', '95.13', 'May 15, 2021', 'ACTIVE', 'potato', 3),
       ('User', 'Feb 24, 2020', '98.10', 'Oct 14, 2021', 'ACTIVE', 'turnip', 2),
       ('User', 'Aug 25, 2019', '26.95', 'Jul 18, 2021', 'ACTIVE', 'grape', 2),
       ('User', 'Sep 2, 2019', '70.91', 'Jul 20, 2019', 'DISABLED', 'sprout', 3),
       ('User', 'Jan 5, 2020', '65.55', 'Jan 21, 2019', 'ACTIVE', 'kohlrabi', 3);

INSERT INTO product_category (product_id, category_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 1),
       (5, 1),
       (6, 3),
       (7, 1),
       (8, 2);