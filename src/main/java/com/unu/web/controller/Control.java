package com.unu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Control {

	@GetMapping("/")
	public ModelAndView DirigirPaginaInicio() {
		ModelAndView modelAndView = new ModelAndView("Inicio");
		return modelAndView;
	}
	
	@GetMapping("/Empleado")
	public String Productor() {
		return "redirect:/Dashboard/Productor/ListaProductor";
	}

	

}