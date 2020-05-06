package com.example.sliderverify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulInterface {

    @GetMapping("/sayhi")
    public String sayHi(){
        return "hi";
    }
}
