-- DROP TABLE basket;
-- DROP TABLE product;
-- DROP TABLE user_account;

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
    image             VARCHAR,
    price             DECIMAL(4,2) NOT NULL
);

CREATE TABLE basket (
    user_email        VARCHAR NOT NULL
    CONSTRAINT user_email REFERENCES user_account(email),
    product_id        INTEGER NOT NULL
    CONSTRAINT product_id REFERENCES product(id),
    amount            INTEGER NOT NULL
);


INSERT INTO user_account(email, name, password, role) VALUES('fleur.vaneijk99@gmail.com', 'Fleur van Eijk', 'wachtwoord', 'GUEST');
INSERT INTO user_account(email, name, password, role) VALUES('admin@gmail.com', 'Admin', 'wachtwoord', 'ADMIN');


INSERT INTO product(title, description, image, price) VALUES('Lampionmand', 'Handgeweven mand uit Bolgatanga, Ghana', 'C:\Users\Fleur van Eijk\IdeaProjects\IPRWC-webshop-frontend\src\assets\lampion_mand.jpg', '20.00');
INSERT INTO product(title, description, image, price) VALUES('Fruitmand', 'Handgeweven mand uit Bolgatanga, Ghana', 'C:\Users\Fleur van Eijk\IdeaProjects\IPRWC-webshop-frontend\src\assets\fruitmand.jpg', '15.00');
INSERT INTO product(title, description, image, price) VALUES('Grote mand', 'Handgeweven mand uit Bolgatanga, Ghana', 'C:\Users\Fleur van Eijk\IdeaProjects\IPRWC-webshop-frontend\src\assets\grote_mand.jpg', '40.00');

INSERT INTO basket VALUES('fleur.vaneijk99@gmail.com', '1', '2');
INSERT INTO basket VALUES('fleur.vaneijk99@gmail.com', '2', '5');
INSERT INTO basket VALUES('fleur.vaneijk99@gmail.com', '3', '1');

INSERT INTO basket VALUES('admin@gmail.com', '3', '10');