CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE event (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(300) NOT NULL,
    img_url VARCHAR(100) not null,
    event_url VARCHAR(100) NOT NULL,
    remote BOOLEAN NOT NULL,
    date TIMESTAMP NOT NULL
);