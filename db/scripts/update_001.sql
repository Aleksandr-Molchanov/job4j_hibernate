CREATE TABLE if not exists models (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE if not exists brands (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE if not exists brands_models(
    id serial primary key,
    models_id int references models(id),
    brand_id int references brands(id)
);

CREATE TABLE if not exists books (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE if not exists authors (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE if not exists books_authors (
    id serial primary key,
    book_id int references books(id),
    authors_id int references authors(id)
);