package com.unu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.unu.web.entity.Banco;
import com.unu.web.service.BancoService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/Banco")
public class BancoController {

	@Autowired
	@Qualifier("bancoService")
	private BancoService bancoService;

	@GetMapping("/")
	public String Main() {
		return "redirect:/Banco/Nuevo";
	}
	@GetMapping("")
	public String Main2() {
		return "redirect:/Banco/Nuevo";
	}

	@GetMapping("/Nuevo")
	public ModelAndView Nuevo() {
		ModelAndView modelAndView = new ModelAndView("Banco/NuevoBanco");
		Banco banco = new Banco();
		modelAndView.addObject("NuevoBanco", banco);
		return modelAndView;
	}

	@PostMapping("/Nuevo")
	public ModelAndView Insertar(@Valid @ModelAttribute(name = "NuevoBanco") Banco banco,
			BindingResult result) {
		
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			modelAndView.setViewName("Banco/NuevoBanco");
			return modelAndView;
		}
		bancoService.InsertarBanco(banco);
		modelAndView.setViewName(
				"redirect:/Banco/Lista");
		return modelAndView;
	}

}