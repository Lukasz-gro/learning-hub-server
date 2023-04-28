DROP TABLE IF EXISTS course_problem;
DROP TABLE IF EXISTS test;
DROP TABLE IF EXISTS problem;
DROP TABLE IF EXISTS course;

CREATE TABLE course (
    id              serial PRIMARY KEY,
    name            text NOT NULL UNIQUE,
    description     text NOT NULL UNIQUE
);

CREATE TABLE problem (
    id              serial PRIMARY KEY,
    name            text NOT NULL UNIQUE,
    description     text NOT NULL UNIQUE
);

CREATE TABLE test (
    id              serial PRIMARY KEY,
    problem_id      int REFERENCES problem(id),
    input           text NOT NULL,
    output          text NOT NULL
);

CREATE TABLE course_problem (
    course_id       int REFERENCES course(id) ON UPDATE CASCADE ON DELETE CASCADE,
    problem_id      int REFERENCES problem(id) ON UPDATE CASCADE,
    PRIMARY KEY (course_id, problem_id)
);