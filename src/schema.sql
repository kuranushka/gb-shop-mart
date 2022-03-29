CREATE TABLE product_category
(
    product_id  BIGINT NOT NULL
        CONSTRAINT fk_product_category_product
            REFERENCES product (id),
    category_id BIGINT NOT NULL
        CONSTRAINT fk_product_category_category
            REFERENCES category (id),
        PRIMARY KEY (product_id, category_id)
)