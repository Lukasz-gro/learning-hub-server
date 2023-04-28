DROP TABLE IF EXISTS problem;
DROP TABLE IF EXISTS course;

CREATE TABLE course (
    id              SERIAL,
    name            TEXT NOT NULL,
    description     TEXT NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(name)
);

CREATE TABLE problem (
    id      SERIAL,
    name    TEXT NOT NULL,
    PRIMARY KEY(id)
);