package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Faculty> createFaculty(@RequestParam String name,
                                                 @RequestParam String color) {
        Faculty faculty = facultyService.createFaculty(name, color);
        return new ResponseEntity<>(faculty, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить факультет по ID")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        return faculty != null ? ResponseEntity.ok(faculty) : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-name-or-color")
    @Operation(summary = "Получить факультеты по имени или цвету")
    public Collection<Faculty> getFacultiesByNameOrColor(@RequestParam String nameOrColor) {
        return facultyService.findByNameOrColor(nameOrColor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные факультета")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestParam String name,
                                                 @RequestParam String color) {
        Faculty updatedFaculty = facultyService.updateFaculty(id, name, color);
        return updatedFaculty != null ? ResponseEntity.ok(updatedFaculty) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить факультет")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        if (facultyService.deleteFaculty(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

