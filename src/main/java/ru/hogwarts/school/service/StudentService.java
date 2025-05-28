package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private long lastId = 0;

    public Student createStudent(String name, int age) {
        long newId = ++lastId;
        Student student = new Student(newId, name, age);
        students.put(newId, student);
        return student;
    }

    public Student findStudent(long id) {
        return students.get(id);
    }

    public Student updateStudent(long id, String name, int age) {
        Student student = students.get(id);
        if (student != null) {
            student.setName(name);
            student.setAge(age);
        }
        return student;
    }


    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> findByAge(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
