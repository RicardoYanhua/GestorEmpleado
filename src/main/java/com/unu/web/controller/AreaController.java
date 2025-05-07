package com.unu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.unu.web.entity.Area;
import com.unu.web.entity.Empleado;
import com.unu.web.service.AreaService;
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

	@GetMapping("")
	public String Main2() {
		return "redirect:/Area/Lista";
	}

	@GetMapping("/Lista")
	public ModelAndView Lista() {
		ModelAndView modelAndView = new ModelAndView("Area/ListaArea");
		modelAndView.addObject("Areas", areaService.ListarArea());
		return modelAndView;
	}

	@GetMapping("/Nuevo")
	public ModelAndView Nuevo() {
		ModelAndView modelAndView = new ModelAndView("Area/NuevoArea");
		Area area = new Area();
		modelAndView.addObject("NuevoArea", area);
		return modelAndView;
	}

	@PostMapping("/Nuevo")
	public ModelAndView Insertar(@Valid @ModelAttribute(name = "NuevoArea") Area area, BindingResult result) {

		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			modelAndView.setViewName("Area/NuevoArea");
			return modelAndView;
		}
		areaService.InsertarArea(area);
		modelAndView.setViewName("redirect:/Area/Lista");
		return modelAndView;
	}

	@GetMapping("/Editar/{CodigoArea}")
	public ModelAndView Obtener(@PathVariable(name = "CodigoArea") int id) {
		ModelAndView modelAndView = new ModelAndView("Area/EditarArea");
		Area area = areaService.ObtenerArea(id);
		modelAndView.addObject("Area", area);
		return modelAndView;
	}

	@PostMapping("/Editar/{CodigoArea}")
	public ModelAndView Actualizar(
			@PathVariable(name = "CodigoArea") Integer id, 
			@Valid @ModelAttribute(name = "Area") Area area,
			BindingResult result) {

		ModelAndView modelAndView = new ModelAndView();


		if (result.hasErrors()) {
			modelAndView.setViewName("Area/EditarArea");
			return modelAndView;
		}

		areaService.ActualizarArea(area);
		modelAndView.setViewName("redirect:/Area/Lista");
		return modelAndView;

	}

}