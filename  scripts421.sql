-- Установка ограничений для таблицы Faculty
ALTER TABLE Faculty
ADD CONSTRAINT unique_faculty_name_color UNIQUE (name, color);

-- Установка ограничений для таблицы Student
ALTER TABLE Student
ADD CONSTRAINT age_check CHECK (age >= 16),
ADD CONSTRAINT unique_student_name UNIQUE (name);

-- Установка значения по умолчанию для возраста студента
ALTER TABLE Student
ALTER COLUMN age SET DEFAULT 20;