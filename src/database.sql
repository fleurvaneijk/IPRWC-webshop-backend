CREATE TABLE user_account (
    id                SERIAL PRIMARY KEY,
    fullName          VARCHAR(100) CHECK(length(fullName) >=3),
    postcode          VARCHAR(7) CHECK(length(postcode) >=6),
    streetnumber      VARCHAR(10) CHECK(length(streetnumber) >=1),
    emailAddress      VARCHAR UNIQUE NOT NULL,
    password          VARCHAR CHECK(length(password) >=8)
);

CREATE TABLE roles (
    user_id           INTEGER
    CONSTRAINT user_id REFERENCES user_account(id),
    role              VARCHAR
);

CREATE TABLE product (
    id                SERIAL PRIMARY KEY,
    title             VARCHAR NOT NULL,
    description       VARCHAR,
    image             VARCHAR,
    price             DECIMAL(4,2) NOT NULL
);

CREATE TABLE basket (
    id                SERIAL PRIMARY KEY,
    user_id           INTEGER NOT NULL
    CONSTRAINT user_id REFERENCES user_account(id)

);

CREATE TABLE ordered_product (
    basket_id          INTEGER NOT NULL
    CONSTRAINT basket_id REFERENCES basket(id),
    product_id        INTEGER NOT NULL
    CONSTRAINT product_id REFERENCES product(id),
    price             DECIMAL(4,2) NOT NULL
);


INSERT INTO user_account(fullName, postcode, streetnumber, emailAddress, password) VALUES('Fleur van Eijk', '2153LN', '1052', 'fleur.vaneijk99@gmail.com', 'wachtwoord');
INSERT INTO user_account(fullName, postcode, streetnumber, emailAddress, password) VALUES('admin', '9999AA', '1', 'admin@gmail.com', 'wachtwoord');

INSERT INTO roles VALUES('1', 'guest');
INSERT INTO roles VALUES('2', 'admin');

INSERT INTO product(title, description, image, price) VALUES('Lampionmand', 'Handgeweven mand uit Bolgatanga, Ghana', 'C:\Users\Fleur van Eijk\IdeaProjects\IPRWC-webshop-frontend\src\assets\lampion_mand.jpg', '20.00');
INSERT INTO product(title, description, image, price) VALUES('Fruitmand', 'Handgeweven mand uit Bolgatanga, Ghana', 'C:\Users\Fleur van Eijk\IdeaProjects\IPRWC-webshop-frontend\src\assets\fruitmand.jpg', '15.00');
INSERT INTO product(title, description, image, price) VALUES('Grote mand', 'Handgeweven mand uit Bolgatanga, Ghana', 'C:\Users\Fleur van Eijk\IdeaProjects\IPRWC-webshop-frontend\src\assets\grote_mand.jpg', '40.00');