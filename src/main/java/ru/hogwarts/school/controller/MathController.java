package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/math")
@Tag(name = "Math API", description = "Математические операции")
public class MathController {

    @GetMapping("/sum")
    @Operation(summary = "Сумма чисел от 1 до 1 000 000")
    public long calculateSum() {
        long n = 1_000_000;
        return n * (n + 1) / 2;
    }
}
