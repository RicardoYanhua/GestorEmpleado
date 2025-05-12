package com.unu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.unu.web.entity.Administrador;
import com.unu.web.repository.AdministradorRepository;

import jakarta.validation.Valid;

@Controller
public class SesionController {

	@Autowired
	private AdministradorRepository administradorRepository;

	@GetMapping("/Login")
	public ModelAndView Login(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
	        return new ModelAndView("redirect:/Empleado/Lista");
	    }

	    return new ModelAndView("/Sesion/FormLogin");
	}

	@GetMapping("/Register")
	public ModelAndView Register() {
		System.out.println("Register");
		ModelAndView modelAndView = new ModelAndView("/Sesion/FormRegister");
		modelAndView.addObject("NuevoAdministrador", new Administrador());
		return modelAndView;
	}

	@PostMapping("/Register")
	public ModelAndView Registrarse(
			@Valid @ModelAttribute(name = "NuevoAdministrador") Administrador admin,
			BindingResult result
			) {
		
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			modelAndView.setViewName("/Sesion/FormRegister");
			return modelAndView;
		}
		
		try {
			var  bCryptEncoder = new BCryptPasswordEncoder();
			Administrador Usuario_register = admin;
			Usuario_register.setAdmContrasenia(bCryptEncoder.encode(Usuario_register.getAdmContrasenia()));
			administradorRepository.save(Usuario_register);
		} catch (Exception e) {}

		modelAndView.setViewName("redirect:/Login");
		return modelAndView;
	}
	
	
	
	

}