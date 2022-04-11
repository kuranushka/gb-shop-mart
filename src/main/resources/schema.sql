DROP TABLE IF EXISTS product_category CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS manufacturer CASCADE;

CREATE TABLE category
(
    id                 BIGSERIAL PRIMARY KEY,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP,
    title              VARCHAR(255) NOT NULL,
    version            INTEGER      NOT NULL DEFAULT 0
);

create table manufacturer
(
    id                 BIGSERIAL PRIMARY KEY,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP,
    version            INTEGER      NOT NULL DEFAULT 0,
    name               VARCHAR(255) NOT NULL
);

create table product
(
    id                 BIGSERIAL PRIMARY KEY,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP,
    version            INTEGER      NOT NULL DEFAULT 0,
    cost               NUMERIC(19, 2),
    manufacture_date   DATE,
    status             VARCHAR(255),
    title              VARCHAR(255) NOT NULL,
    manufacturer_id    BIGINT
        CONSTRAINT fk_product_manufacturer
            REFERENCES manufacturer
);

CREATE TABLE product_category
(
    category_id BIGINT NOT NULL
        CONSTRAINT fk_product_category_category
            REFERENCES category,
    product_id  BIGINT NOT NULL
        CONSTRAINT fk_product_category_product
            REFERENCES product,
    PRIMARY KEY (category_id, product_id)
);
