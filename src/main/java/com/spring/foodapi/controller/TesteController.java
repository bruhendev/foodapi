package com.spring.foodapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/teste")
public class TesteController {
    
    @GetMapping
    @ResponseBody
    public String index() {
        return "Olá teste";
    }
}
