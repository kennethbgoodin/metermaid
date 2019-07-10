package com.gmail.kennethbgoodin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusyController {

    @RequestMapping(value = "/busy")
    public String index() throws InterruptedException {
        Thread.sleep(1000);
        return fibonacci(14) + "";
    }

    private long fibonacci(int i) {
        if (i <= 1) {
            return i;
        }

        return fibonacci(i - 1) + fibonacci(i - 2);
    }

}
