CREATE DATABASE etablissement;
use etablissement;

CREATE TABLE IF NOT EXISTS highSchool(
id_highSchool LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
name_hs VARCHAR(50) 
);

CREATE TABLE IF NOT EXISTS department(
id_department LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
name_d VARCHAR(50) 
);

CREATE TABLE IF NOT EXISTS teacher(
id_teacher LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
lastname_t VARCHAR(50)  NOT NULL  CHECK (LENGTH(lastname_st) >= 3),
firstname_t VARCHAR(50),
age_t INT CHECK (age_t > 18),
is_principal_teacher BOOLEAN,
is_head_teacher BOOLEAN,
id_d INT,
CONSTRAINT fk_id_d FOREIGN KEY (id_department) REFERENCES department(id_department)
);

CREATE TABLE IF NOT EXISTS classroom(
id_classroom LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
name_c VARCHAR(50),
level_c VARCHAR(50),
id_d INT,
CONSTRAINT fk_id_c FOREIGN KEY (id_department) REFERENCES department(id_department)
);

CREATE TABLE IF NOT EXISTS student(
id_student LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
lastname_st VARCHAR(50) NOT NULL  CHECK (LENGTH(lastname_st) >= 3),
firstname_st VARCHAR(50),
date_of_birth_st DATE,
 email_st VARCHAR(50) CHECK (email_st LIKE '%@gmail.com%'),
id_classroom INT,
CONSTRAINT fk_id_s FOREIGN KEY (id_classroom) REFERENCES classroom(id_classroom)
);

CREATE TABLE IF NOT EXISTS subject(
id_subject LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
title_s VARCHAR(50),
duration_s DATE,
coeff_s INT,
id_classroom INT,
CONSTRAINT fk_id_subject FOREIGN KEY (id_classroom) REFERENCES classroom(id_classroom)
);

CREATE TABLE IF NOT EXISTS grade(
id_grade LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
grade_g INT CHECK (grade_g >= 0 AND grade_g <= 20),
comment_g VARCHAR(50),
id_student INT,
id_subject INT,
CONSTRAINT fk_id_grade_stu FOREIGN KEY (id_st) REFERENCES student(id_student),
CONSTRAINT fk_id_grade_sub FOREIGN KEY (id_s) REFERENCES subject(id_subject)
);

CREATE TABLE IF NOT EXISTS schedule(
id_schedule LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
date_sc DATE,
hour_sc TIME
);

CREATE TABLE IF NOT EXISTS subject_schedule(
id_schedule LONG,
id_subject LONG,
CONSTRAINT fk_id_sc_ssch FOREIGN KEY (id_sc) REFERENCES schedule(id_schedule),
CONSTRAINT fk_id_s_schh FOREIGN KEY (id_s) REFERENCES subject(id_subject)
);

CREATE TABLE IF NOT EXISTS teacher_classroom(
id_teacher LONG,
id_classroom LONG,
CONSTRAINT fk_id_t_class FOREIGN KEY (id_t) REFERENCES teacher(id_teacher),
CONSTRAINT fk_id_c_class FOREIGN KEY (id_c) REFERENCES classroom(id_classroom)
);


CREATE TABLE IF NOT EXISTS teacher_subject(
id_t LONG,
id_s LONG,
CONSTRAINT fk_id_t_cu FOREIGN KEY (id_t) REFERENCES teacher(id_t),
CONSTRAINT fk_id_s_su FOREIGN KEY (id_s) REFERENCES subject(id_s)
);

INSERT INTO highSchool (name_hs) VALUES
('High School A'),
('High School B'),
('High School C');


INSERT INTO department (name_d) VALUES
('Mathematics'),
('Physics'),
('Biology');

INSERT INTO teacher (lastname_t, firstname_t, age_t, is_principal_teacher, is_head_teacher, id_d) VALUES 
('Doe', 'John', 35, TRUE, FALSE, 1),
('Smith', 'Jane', 40, FALSE, TRUE, 2),
('Johnson', 'Robert', 38, FALSE, FALSE, 3);

INSERT INTO classroom (name_c, level_c, id_d) VALUES 
('Class A', '10th Grade', 1),
('Class B', '11th Grade', 2),
('Class C', '12th Grade', 3);

INSERT INTO student (lastname_st, firstname_st, date_of_birth_st, email_st, id_c) VALUES 
('Smith', 'Alice', '2005-05-15', 'alice.smith@example.com', 1),
('Johnson', 'Bob', '2004-08-20', 'bob.johnson@example.com', 2),
('Doe', 'Emma', '2003-02-10', 'emma.doe@example.com', 3);

-- Insertion d'exemples dans la table 'subject'
INSERT INTO subject (title_s, duration_s, coeff_s, id_c) VALUES
('Mathematics', '2024-01-18', 3, 1),
('Physics', '2024-01-18', 4, 2),
('Biology', '2024-01-18', 2, 3),
('Chemistry', '2024-01-18', 3, 1),
('History', '2024-01-18', 2, 2);



INSERT INTO grade (grade_g, comment_g, id_st, id_s) VALUES 
(85, 'Good work', 1, 1),
(92, 'Excellent', 2, 2),
(78, 'Needs improvement', 3, 3);


INSERT INTO schedule (date_sc, hour_sc) VALUES 
('2024-01-18', '08:30:00'),
('2024-01-19', '10:00:00'),
('2024-01-20', '13:45:00');

INSERT INTO subject_schedule (id_sc, id_s) VALUES 
(1, 1),
(2, 2),
(3, 3);


INSERT INTO teacher_class (id_t, id_c) VALUES 
(1, 1),
(2, 2),
(3, 3);


INSERT INTO teacher_subject (id_t, id_s) VALUES 
(1, 1),
(2, 2),
(3, 3);

select * from classroom;

SELECT 
    s.lastname_st AS student_lastname,
    s.firstname_st AS student_firstname,
    COUNT(g.id_s) AS number_of_subjects
FROM 
    student s
JOIN 
    grade g ON s.id_st = g.id_st
GROUP BY 
    s.id_st, s.lastname_st, s.firstname_st;

select lastname_st, grade_g from 
student s join grade g on g.id_st = s.id_st;








