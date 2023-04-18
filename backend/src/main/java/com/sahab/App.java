package com.sahab;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;

public class App 
{
    @GetMapping("/")
    String home() {
        return "Spring is here!";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
