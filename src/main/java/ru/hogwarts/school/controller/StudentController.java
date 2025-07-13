package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
@Tag(name = "Student API", description = "Управление студентами Хогвартса")
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
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
    public Student getStudent(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @GetMapping("/by-age-between")
    @Operation(summary = "Фильтрация студентов по возрастному диапазону")
    public Collection<Student> getStudentsByAgeBetween(
            @RequestParam int min,
            @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные студента")
    public Student updateStudent(@PathVariable Long id,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) Integer age,
                                 @RequestParam(required = false) Long facultyId) {
        return studentService.updateStudent(id, name, age, facultyId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить студента")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/{id}/faculty")
    @Operation(summary = "Получить факультет студента")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
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

    @GetMapping("/names-starting-with-a")
    @Operation(summary = "Получить имена студентов, начинающиеся на 'А'")
    public List<String> getNamesStartingWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(Objects::nonNull)
                .filter(name -> name.toUpperCase().startsWith("А"))
                .map(name -> name.toUpperCase(Locale.ROOT))
                .sorted()
                .collect(Collectors.toList());
    }

    @GetMapping("/average-age-repo")
    @Operation(summary = "Средний возраст студентов (через репозиторий)")
    public double getAverageAgeViaRepo() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }
}
