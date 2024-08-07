package com.example.producer.rest;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
	private StreamBridge streamBridge;

    @GetMapping
    public Student get(
        @RequestParam(value = "name", defaultValue = "John") String name,
        @RequestParam(value = "standard", defaultValue = "1") int standard
        ) {
        return new Student(
            counter.incrementAndGet(), 
            String.format(template, name), 
            standard
        );
    }

    @PostMapping
    public Student create(
        @RequestBody Student student
        ) {
        streamBridge.send("process-in-0", student);
        return student;
    }
}