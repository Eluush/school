package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentPrintingService {
    private final StudentRepository studentRepository;

    public StudentPrintingService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void printParallel() {
        List<Student> students = studentRepository.findAll();
        if (students.size() < 6) {
            throw new IllegalStateException("Need at least 6 students");
        }

        System.out.println("Main Thread: " + students.get(0).getName());
        System.out.println("Main Thread: " + students.get(1).getName());

        CompletableFuture.runAsync(() -> {
            System.out.println("Parallel Thread 1: " + students.get(2).getName());
            System.out.println("Parallel Thread 1: " + students.get(3).getName());
        });

        CompletableFuture.runAsync(() -> {
            System.out.println("Parallel Thread 2: " + students.get(4).getName());
            System.out.println("Parallel Thread 2: " + students.get(5).getName());
        });
    }

    public void printSynchronized() {
        List<Student> students = studentRepository.findAll();
        if (students.size() < 6) {
            throw new IllegalStateException("Need at least 6 students");
        }

        printName(students.get(0).getName());
        printName(students.get(1).getName());

        CompletableFuture.runAsync(() -> {
            printName(students.get(2).getName());
            printName(students.get(3).getName());
        });

        CompletableFuture.runAsync(() -> {
            printName(students.get(4).getName());
            printName(students.get(5).getName());
        });
    }

    private synchronized void printName(String name) {
        System.out.println("Synchronized Print: " + name + " | Thread: " + Thread.currentThread().getName());
    }
}