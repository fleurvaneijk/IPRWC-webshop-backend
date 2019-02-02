DROP TABLE cart;
DROP TABLE product_image;
DROP TABLE product;
DROP TABLE user_account;

CREATE TABLE user_account (
    email             VARCHAR PRIMARY KEY,
    name              VARCHAR(100) CHECK(length(name) >=3),
    password          VARCHAR CHECK(length(password) >=8),
    role              VARCHAR
);

CREATE TABLE product (
    id                SERIAL PRIMARY KEY,
    title             VARCHAR NOT NULL,
    description       VARCHAR,
    price             DECIMAL(4,2) NOT NULL
);

CREATE TABLE product_image (
    product_id        INTEGER NOT NULL
    CONSTRAINT product_id REFERENCES product(id) ON DELETE CASCADE,
    image             VARCHAR
);

CREATE TABLE cart (
    user_email        VARCHAR NOT NULL
    CONSTRAINT user_email REFERENCES user_account(email) ON DELETE CASCADE,
    product_id        INTEGER NOT NULL
    CONSTRAINT product_id REFERENCES product(id) ON DELETE CASCADE,
    amount            INTEGER NOT NULL
);