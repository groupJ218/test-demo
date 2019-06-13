package com.test.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class StartController {

    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "index";
    }

}
