package com.beeva.ryd.vision.poc.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accessDenied")
public class AccessDeniedController {

    @RequestMapping
    public String get() {
        return "accessDenied";
    }
}
