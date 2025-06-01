package com.nickolss.rest_with_spring_boot.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nickolss.rest_with_spring_boot.model.Greeting;

@RestController
public class GrettingController {

    private static final String template = "Hello, %s!";
    private static final AtomicLong counter = new AtomicLong();

    // http://localhost:8080/gretting?name=Nickolas
    @GetMapping("/gretting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    

}
