package com.example.BuiVanMinh;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@SpringBootApplication
@RestController

@RequestMapping("/api/v1")
public class HelloControll {
    @RequestMapping("/")
    String home() {
        System.out.println("Hello");
        return "Hello World!";
    }

    @GetMapping("/get")
    public String getMethodName() {
        return "Get Product";
    }

    @PutMapping("/put/{id}")
    public String putMethodName() {
        // TODO: process PUT request

        return "Put Product";
    }

    @DeleteMapping("/del")
    public String delPro() {
        return "Del Pro";
    }

}