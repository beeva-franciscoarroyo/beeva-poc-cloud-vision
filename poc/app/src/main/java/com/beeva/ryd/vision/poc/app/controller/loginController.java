package com.beeva.ryd.vision.poc.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class loginController {

    @RequestMapping
    public String login() {
        return "login";
    }
}
