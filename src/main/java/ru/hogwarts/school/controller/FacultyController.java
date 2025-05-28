package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Faculty API", description = "Управление факультетами Хогвартса")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @Operation(summary = "Создать новый факультет")
    public Faculty createFaculty(@RequestParam String name,
                                 @RequestParam String color) {
        return facultyService.createFaculty(name, color);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить факультет по ID")
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyService.findFaculty(id);
    }

    @GetMapping("/by-color")
    @Operation(summary = "Фильтрация факультетов по цвету")
    public Collection<Faculty> getFacultiesByColor(@RequestParam String color) {
        return facultyService.findByColor(color);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные факультета")
    public Faculty updateFaculty(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String color) {
        return facultyService.updateFaculty(id, name, color);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить факультет")
    public Faculty deleteFaculty(@PathVariable Long id) {
        return facultyService.deleteFaculty(id);
    }
}