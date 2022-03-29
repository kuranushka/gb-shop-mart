DROP TABLE IF EXISTS category CASCADE;
CREATE TABLE category
(
    id                 BIGSERIAL PRIMARY KEY,
    title              VARCHAR(255) NOT NULL UNIQUE,
    version            INTEGER DEFAULT 0 NOT NULL,
    created_by         VARCHAR(255),
    created_date       DATE    DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    last_modified_by   VARCHAR(255),
    last_modified_date DATE
);



INSERT INTO category (id, title) VALUES (1, 'Grocery');
INSERT INTO category (id, title) VALUES (2, 'Fruits');
INSERT INTO category (id, title) VALUES (3, 'Bakery products');