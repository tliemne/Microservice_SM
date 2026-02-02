-- Insert sample subjects
INSERT INTO subjects (subject_code, subject_name, credits, description, is_active) VALUES
('JAVA', 'Lập trình Java', 4, 'Học lập trình hướng đối tượng với Java', TRUE),
('WEB', 'Phát triển Web', 3, 'HTML, CSS, JavaScript, React', TRUE),
('DB', 'Cơ sở dữ liệu', 3, 'MySQL, MongoDB, SQL Server', TRUE),
('CLOUD', 'Cloud Computing', 3, 'AWS, Azure, GCP', TRUE),
('DEVOPS', 'DevOps & CI/CD', 3, 'Docker, Kubernetes, Jenkins', TRUE);

-- Insert sample points for students
-- Student 1 (SV001) - IT-K1 (school_id = 1)
INSERT INTO points (student_id, school_id, subject_id, score, semester) VALUES
(1, 1, 1, 85.5, '2024-Q1'),
(1, 1, 2, 90.0, '2024-Q1'),
(1, 1, 3, 78.5, '2024-Q2');

-- Student 2 (SV002) - IT-K2 (school_id = 1)
INSERT INTO points (student_id, school_id, subject_id, score, semester) VALUES
(2, 1, 1, 92.0, '2024-Q1'),
(2, 1, 2, 88.5, '2024-Q1'),
(2, 1, 4, 95.0, '2024-Q2');

-- Student 3 (SV003) - IT-K2 (school_id = 1)
INSERT INTO points (student_id, school_id, subject_id, score, semester) VALUES
(3, 1, 1, 76.0, '2024-Q1'),
(3, 1, 3, 82.5, '2024-Q1'),
(3, 1, 5, 80.0, '2024-Q2');

-- Student 4 (SV004) - BM-K1 (school_id = 2)
INSERT INTO points (student_id, school_id, subject_id, score, semester) VALUES
(4, 2, 2, 88.0, '2024-Q1'),
(4, 2, 3, 85.5, '2024-Q1');

-- Student 5 (SV005) - EN-K1 (school_id = 3)
INSERT INTO points (student_id, school_id, subject_id, score, semester) VALUES
(5, 3, 2, 79.5, '2024-Q1'),
(5, 3, 4, 86.0, '2024-Q1');

