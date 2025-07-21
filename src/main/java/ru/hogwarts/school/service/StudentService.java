package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(String name, int age, Long facultyId) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        if (facultyId != null) {
            Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
            student.setFaculty(faculty);
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, String name, Integer age, Long facultyId) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            if (name != null) student.setName(name);
            if (age != null) student.setAge(age);
            if (facultyId != null) {
                Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
                student.setFaculty(faculty);
            }
            return studentRepository.save(student);
        }
        return null;
    }

    public Faculty getStudentFaculty(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        return student != null ? student.getFaculty() : null;
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Student deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            studentRepository.delete(student);
            return student;
        }
        return null;
    }

    public long getCountOfStudents() {
        return studentRepository.count();
    }

    public Double getAverageAge() {
        return studentRepository.findAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.findLastFiveStudents();
    }
}

