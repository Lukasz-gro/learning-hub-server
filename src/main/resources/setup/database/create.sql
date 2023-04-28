DROP TABLE IF EXISTS problem;

CREATE TABLE problem (
    id      SERIAL,
    name    TEXT NOT NULL,
    PRIMARY KEY(id)
);