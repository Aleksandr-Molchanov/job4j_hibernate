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