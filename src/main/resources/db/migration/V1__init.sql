CREATE TABLE IF NOT EXISTS client(
    id SERIAL PRIMARY KEY,
    nui VARCHAR(10),
    fullname VARCHAR(50),
    address VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS invoice(
    id SERIAL PRIMARY KEY,
    code VARCHAR(30),
    create_at DATE,
    total DECIMAL(10,2),
    client_id INT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id)
);
CREATE TABLE IF NOT EXISTS product(
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    brand VARCHAR(255),
    price INT,
    stok INTEGER
);
CREATE TABLE IF NOT EXISTS detail(
    id SERIAL PRIMARY KEY,
    quantity INTEGER,
    price INT,
    invoice_id INT NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES invoice(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

