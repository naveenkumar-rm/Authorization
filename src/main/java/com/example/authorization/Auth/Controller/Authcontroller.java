package com.example.authorization.Auth.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class Authcontroller {
    @GetMapping("/getOne")
    public String getOneUser() {
        return "I am Naveen";
    }
}
