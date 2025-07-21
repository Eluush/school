-- Создание таблицы Person
CREATE TABLE Person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT CHECK (age >= 16) DEFAULT 20,
    has_license BOOLEAN NOT NULL
);

-- Создание таблицы Car
CREATE TABLE Car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Создание таблицы для связи между людьми и машинами (многие ко многим)
CREATE TABLE Person_Car (
    person_id INT REFERENCES Person(id),
    car_id INT REFERENCES Car(id),
    PRIMARY KEY (person_id, car_id)
);