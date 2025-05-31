package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(String name, int age) {
        Student student = new Student(name, age);
        return repository.save(student);
    }

    public Student findStudent(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Collection<Student> findByAge(int age) {
        return repository.findByAge(age);
    }

    public Student updateStudent(Long id, String name, int age) {
        return repository.findById(id)
                .map(student -> {
                    student.setName(name);
                    student.setAge(age);
                    return repository.save(student);
                })
                .orElse(null);
    }

    public Student deleteStudent(Long id) {
        Optional<Student> student = repository.findById(id);
        student.ifPresent(repository::delete);
        return student.orElse(null);
    }
}