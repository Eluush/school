package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.hogwarts.school.exeption.ResourceNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;


import org.springframework.beans.factory.annotation.Autowired;
import java.util.Objects;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Faculty API", description = "Управление факультетами Хогвартса")
public class FacultyController {
    private final FacultyService facultyService;
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyController(FacultyService facultyService, FacultyRepository facultyRepository) {
        this.facultyService = facultyService;
        this.facultyRepository = facultyRepository;
    }

    @PostMapping
    @Operation(summary = "Создать новый факультет")
    @ResponseStatus(HttpStatus.CREATED)
    public Faculty createFaculty(
            @RequestParam String name,
            @RequestParam String color
    ) {
        return facultyService.createFaculty(name, color);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить факультет по ID")
    public Faculty getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            throw new ResourceNotFoundException("Faculty not found with id " + id);
        }
        return faculty;
    }

    @GetMapping("/by-name-or-color")
    @Operation(summary = "Получить факультеты по имени или цвету")
    public Collection<Faculty> getFacultiesByNameOrColor(@RequestParam String nameOrColor) {
        return facultyService.findByNameOrColor(nameOrColor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные факультета")
    public Faculty updateFaculty(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String color
    ) {
        Faculty updatedFaculty = facultyService.updateFaculty(id, name, color);
        if (updatedFaculty == null) {
            throw new ResourceNotFoundException("Faculty not found with id " + id);
        }
        return updatedFaculty;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить факультет")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFaculty(@PathVariable Long id) {
        if (!facultyService.deleteFaculty(id)) {
            throw new ResourceNotFoundException("Faculty not found with id " + id);
        }
    }

    @GetMapping("/longest-name")
    @Operation(summary = "Получить самое длинное название факультета")
    public String getLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }
}