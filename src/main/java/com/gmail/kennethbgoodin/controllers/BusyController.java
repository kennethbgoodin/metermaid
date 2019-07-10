package com.gmail.kennethbgoodin.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusyController {

    @RequestMapping(value = "/busy", method = RequestMethod.GET, headers = "Accept=application/json")
    public String index(@RequestBody String in) {
        int x = 0;
        for (int i = 0; i < 10; i++) {
            x += fibonacci(i);
        }

        return x + "";
    }

    private long fibonacci(int i) {
        if (i <= 1) {
            return i;
        }

        return fibonacci(i - 1) + fibonacci(i - 2);
    }

}
