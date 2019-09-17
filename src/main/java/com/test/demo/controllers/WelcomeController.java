package com.test.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class WelcomeController {
    Logger log = Logger.getLogger(this.getClass().getName());

    @GetMapping
    public String getPersons(Model model) {
        log.warning("=========================START Welcome=============================");


        log.warning("=========================END  Welcome=============================");
        return "index";
    }

}
