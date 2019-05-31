package com.test.demo.controllers;

import com.test.demo.models.Widget;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class TestController {

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Widget index() {
        return new Widget("green", 10, 7);
    }

}
