DROP TABLE IF EXISTS chat_message;
DROP TABLE IF EXISTS submit;
DROP TABLE IF EXISTS user_course;
DROP TABLE IF EXISTS _user;
DROP TABLE IF EXISTS test;
DROP TABLE IF EXISTS problem_tag;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS hint;
DROP TABLE IF EXISTS course_problem;
DROP TABLE IF EXISTS problem;
DROP TABLE IF EXISTS course;

CREATE TABLE course (
    id                  serial PRIMARY KEY,
    name                text NOT NULL UNIQUE,
    short_description   text NOT NULL,
    description         text NOT NULL
);

CREATE TABLE problem (
    id                  serial PRIMARY KEY,
    name                text NOT NULL UNIQUE,
    description         text NOT NULL UNIQUE,
    prompt              text NOT NULL,
    solution_code       text NOT NULL
);

CREATE TABLE course_problem (
    course_id           int REFERENCES course(id) ON UPDATE CASCADE ON DELETE CASCADE,
    problem_id          int REFERENCES problem(id) ON UPDATE CASCADE,
    ordinal_number      int NOT NULL,
    PRIMARY KEY (course_id, problem_id),
    UNIQUE (course_id, ordinal_number)
);

CREATE TABLE hint (
    id                 serial PRIMARY KEY,
    problem_id         int REFERENCES problem(id),
    ordinal_number     int NOT NULL,
    description        text NOT NULL,
    UNIQUE(problem_id, ordinal_number)
);

CREATE TABLE tag (
    id                  serial PRIMARY KEY,
    name                text NOT NULL UNIQUE
);

CREATE TABLE problem_tag (
    problem_id         int REFERENCES problem(id) ON UPDATE CASCADE ON DELETE CASCADE,
    tag_id             int REFERENCES tag(id) ON UPDATE CASCADE,
    PRIMARY KEY (problem_id, tag_id)
);

CREATE TABLE test (
    id                  serial PRIMARY KEY,
    problem_id          int REFERENCES problem(id),
    input               text NOT NULL,
    output              text NOT NULL
);

CREATE TABLE _user (
    id                  serial PRIMARY KEY,
    username            text NOT NULL UNIQUE,
    password            text NOT NULL,
    email               text NOT NULL UNIQUE,
    role                text NOT NULL
);

CREATE TABLE user_course (
    user_id             int REFERENCES _user(id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id           int REFERENCES course(id) ON UPDATE CASCADE,
    PRIMARY KEY (user_id, course_id)
);

CREATE TABLE submit (
    id                  int PRIMARY KEY,
    problem_id          int REFERENCES problem(id),
    code                text NOT NULL,
    status              text NOT NULL,
    date                timestamp NOT NULL,
    user_id             int REFERENCES _user(id),
    error_message       text,
    output              text
);

CREATE TABLE chat_message (
    id                  serial PRIMARY KEY,
    user_id             int REFERENCES _user(id),
    problem_id          int REFERENCES problem(id),
    message             TEXT NOT NULL,
    date                timestamp NOT NULL,
    is_user             boolean NOT NULL
);

-- BEGIN (each user gets access to every course for now)
CREATE OR REPLACE FUNCTION user_add_course()
returns TRIGGER AS $user_course_add$
DECLARE
    course_id course.id%TYPE;
BEGIN
    FOR course_id IN (SELECT id FROM course) LOOP
        INSERT INTO user_course VALUES (NEW.id, course_id);
    END LOOP;
    RETURN NEW;
END
$user_course_add$
language plpgsql;

CREATE TRIGGER user_course_add AFTER INSERT
    ON _user
    FOR EACH ROW
    EXECUTE PROCEDURE user_add_course();
-- END (each user gets access to every course for now)
