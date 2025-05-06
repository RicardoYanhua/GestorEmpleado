package com.unu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Control {

    @GetMapping("/")
    public String redirigirAlLogin() {
        return "redirect:/Login";
    }

}