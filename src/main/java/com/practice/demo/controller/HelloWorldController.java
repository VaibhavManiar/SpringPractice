package com.practice.demo.controller;

import com.practice.demo.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HelloWorldController {

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping(path = "/hello/{name}")
    public String helloWorld(@PathVariable String name) {
        return String.format("Hello %s", name);
    }
}