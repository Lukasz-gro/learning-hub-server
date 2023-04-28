INSERT INTO course (name, description) VALUES ('Sample Course 1', 'Some sample course 1 description');
INSERT INTO course (name, description) VALUES ('Sample Course 2', 'Some sample course 2 description');
INSERT INTO problem (name, description) VALUES ('Problem A', 'Problem A description');
INSERT INTO problem (name, description) VALUES ('Problem B', 'Problem B description');
INSERT INTO problem (name, description) VALUES ('Problem C', 'Problem C description');
INSERT INTO problem (name, description) VALUES ('Problem D', 'Problem D description');
INSERT INTO problem (name, description) VALUES ('Problem E', 'Problem E description');
INSERT INTO course_problem (course_id, problem_id) VALUES (1, 1), (1, 3), (1, 5);
INSERT INTO course_problem (course_id, problem_id) VALUES (2, 1), (2, 2), (2, 4);