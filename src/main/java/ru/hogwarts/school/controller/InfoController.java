package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

public class InfoController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/port")
    public String getPort() {
        return serverPort;
    }
}

