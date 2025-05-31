package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty createFaculty(String name, String color) {
        Faculty faculty = new Faculty(name, color);
        return repository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Collection<Faculty> findByColor(String color) {
        return repository.findByColorIgnoreCase(color);
    }

    public Faculty updateFaculty(Long id, String name, String color) {
        return repository.findById(id)
                .map(faculty -> {
                    faculty.setName(name);
                    faculty.setColor(color);
                    return repository.save(faculty);
                })
                .orElse(null);
    }

    public Faculty deleteFaculty(Long id) {
        Optional<Faculty> faculty = repository.findById(id);
        faculty.ifPresent(repository::delete);
        return faculty.orElse(null);
    }
}