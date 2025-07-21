-- Первый JOIN-запрос: информация о студентах и их факультетах
SELECT s.name AS student_name, s.age AS student_age, f.name AS faculty_name
FROM Student s
JOIN Faculty f ON s.faculty_id = f.id;

-- Второй JOIN-запрос: студенты с аватарами
SELECT s.name AS student_name, s.age AS student_age, a.image_url AS avatar_image
FROM Student s
JOIN Avatar a ON s.id = a.student_id;