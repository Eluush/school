package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Student API", description = "Управление студентами Хогвартса")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Operation(summary = "Создать нового студента")
    public Student createStudent(@RequestParam String name,
                                 @RequestParam int age,
                                 @RequestParam(required = false) Long facultyId) {
        return studentService.createStudent(name, age, facultyId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить студента по ID")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-age-between")
    @Operation(summary = "Фильтрация студентов по возрастному диапазону")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(
            @RequestParam int min,
            @RequestParam int max) {
        Collection<Student> students = studentService.findByAgeBetween(min, max);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные студента")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) Integer age,
                                                 @RequestParam(required = false) Long facultyId) {
        Student updatedStudent = studentService.updateStudent(id, name, age, facultyId);
        return updatedStudent != null ? ResponseEntity.ok(updatedStudent) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить студента")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student student = studentService.deleteStudent(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/faculty")
    @Operation(summary = "Получить факультет студента")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        Faculty faculty = studentService.getStudentFaculty(id);
        return faculty != null ? ResponseEntity.ok(faculty) : ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Получить количество студентов")
    public long getCountOfStudents() {
        return studentService.getCountOfStudents();
    }

    @GetMapping("/average-age")
    @Operation(summary = "Получить средний возраст студентов")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/last-five")
    @Operation(summary = "Получить последних 5 студентов")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }
}