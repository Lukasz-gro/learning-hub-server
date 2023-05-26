INSERT INTO
    course (name, short_description, description)
VALUES
    ('Sample Course 1', 'Short description 1', 'Some sample course 1 description'),
    ('Sample Course 2', 'Short description 2', 'Some sample course 2 description');

INSERT INTO
    problem (name, description, prompt)
VALUES
    ('Problem A', 'Problem A description', 'Sample prompt 1'),
    ('Problem B', 'Problem B description', 'Sample prompt 2'),
    ('Problem C', 'Problem C description', 'Sample prompt 3'),
    ('Problem D', 'Problem D description', 'Sample prompt 4'),
    ('Problem E', 'Problem E description', 'Sample prompt 5');

INSERT INTO
    course_problem (course_id, problem_id, ordinal_number)
VALUES
    (1, 1, 1), (1, 5, 2), (1, 3, 3),
    (2, 1, 1), (2, 2, 2), (2, 4, 3);

INSERT INTO
    tag (name)
VALUES
    ('Tag 1'), ('Tag 2'), ('Tag 3'), ('Tag 4'), ('Tag 5');

INSERT INTO
    problem_tag (problem_id, tag_id)
VALUES
    (1, 1), (1, 3), (1, 5),
    (2, 2), (1, 4),
    (3, 4),
    (4, 1), (4, 2), (4, 4), (4, 5),
    (5, 2), (5, 3), (5, 5);

INSERT INTO
    test (problem_id, input, output)
VALUES
    (1, 'Sample input 1', 'Sample output 1'),
    (2, 'Sample input 2', 'Sample output 2'),
    (3, 'Sample input 3', 'Sample output 3'),
    (4, 'Sample input 4', 'Sample output 4'),
    (5, 'Sample input 5', 'Sample output 5');

INSERT INTO
    _user(username, password, email)
VALUES
    ('user 1', 'password 1', 'user1@email.com');
