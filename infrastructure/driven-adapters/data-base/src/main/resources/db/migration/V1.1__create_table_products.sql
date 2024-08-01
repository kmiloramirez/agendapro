CREATE TABLE IF NOT EXISTS products
(
    id
    UUID
    NOT
    NULL,
    sku
    VARCHAR
(
    255
),
    name VARCHAR
(
    255
),
    price DOUBLE PRECISION,
    category VARCHAR
(
    255
),
    CONSTRAINT pk_products PRIMARY KEY
(
    id
)
    );
