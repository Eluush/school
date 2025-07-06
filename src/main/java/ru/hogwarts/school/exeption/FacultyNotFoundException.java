package ru.hogwarts.school.exeption;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException(String message) {
        super(message);
    }
}