package com.gmail.kennethbgoodin.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CopycatController {

    @RequestMapping(value = "/copy", method = RequestMethod.POST, headers = "Accept=application/json")
    public String index(@RequestBody String in) {
        return in;
    }
}