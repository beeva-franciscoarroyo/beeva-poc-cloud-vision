package com.beeva.ryd.vision.poc.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("index")
    public String getIndex() {
        return "index";
    }
}
