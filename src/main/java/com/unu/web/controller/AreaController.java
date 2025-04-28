package com.unu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.unu.web.entity.Area;
import com.unu.web.entity.Banco;
import com.unu.web.service.AreaService;
import com.unu.web.service.BancoService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/Area")
public class AreaController {

	@Autowired
	@Qualifier("areaService")
	private AreaService areaService;

	@GetMapping("/")
	public String Main() {
		return "redirect:/Area/Lista";
	}

	@GetMapping("/Nuevo")
	public ModelAndView Nuevo() {
		ModelAndView modelAndView = new ModelAndView("Area/NuevoArea");
		Area area = new Area();
		modelAndView.addObject("NuevoArea", area);
		return modelAndView;
	}

	@PostMapping("/Nuevo")
	public ModelAndView Insertar(@Valid @ModelAttribute(name = "NuevoArea") Area area,
			BindingResult result) {
		
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			modelAndView.setViewName("Area/NuevoArea");
			return modelAndView;
		}
		areaService.InsertarArea(area);
		modelAndView.setViewName(
				"redirect:/Area/Lista");
		return modelAndView;
	}

}