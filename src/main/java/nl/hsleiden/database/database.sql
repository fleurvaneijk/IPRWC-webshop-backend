DROP TABLE product_image;
DROP TABLE product;
DROP TABLE user_account;

CREATE TABLE user_account (
    email             VARCHAR PRIMARY KEY,
    name              VARCHAR(100),
    password          VARCHAR,
    role              VARCHAR
);

CREATE TABLE product (
    id                SERIAL PRIMARY KEY,
    title             VARCHAR NOT NULL,
    description       VARCHAR,
    price             DECIMAL(19,2) NOT NULL
);

CREATE TABLE product_image (
    product_id        INTEGER NOT NULL
    CONSTRAINT product_id REFERENCES product(id) ON DELETE CASCADE,
    image             VARCHAR
);
