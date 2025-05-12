package com.unu.web.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.unu.web.entity.Administrador;
import com.unu.web.entity.Area;
import com.unu.web.entity.Empleado;
import com.unu.web.repository.AdministradorRepository;
import com.unu.web.service.AreaService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/Administrador")
public class AdminController {

	
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@GetMapping("/")
	public String Main() {
		return "redirect:/Administrador/Lista";
	}

	@GetMapping("")
	public String Main2() {
		return "redirect:/Administrador/Lista";
	}

	@GetMapping("/Lista")
	public ModelAndView Lista() {
		ModelAndView modelAndView = new ModelAndView("Admin/ListaAdmin");
	
		modelAndView.addObject("Administrador", (List<Administrador>) administradorRepository.findAll());
		return modelAndView;
	}
	
	@PutMapping("/CambiarEstado/{id}")
	@ResponseBody
	public ResponseEntity<String> cambiarEstado(@PathVariable("id") Integer id,
	                                            @RequestBody Map<String, Object> body,
	                                            Principal principal) {
	    try {
	        Optional<Administrador> optionalAdmin = administradorRepository.findById(id);
	        if (optionalAdmin.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrador no encontrado");
	        }

	        Administrador adminTarget = optionalAdmin.get();

	        // Obtener el usuario autenticado actualmente
	        String usuarioActual = principal.getName(); // Esto obtiene el nombre de usuario (admNombreUsuario)

	        if (adminTarget.getAdmNombreUsuario().equals(usuarioActual)) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                                 .body("No puedes cambiar tu propio estado mientras estás conectado.");
	        }

	        Boolean nuevoEstado = (Boolean) body.get("nuevoEstado");
	        adminTarget.setAdmState(nuevoEstado);
	        administradorRepository.save(adminTarget);

	        return ResponseEntity.ok("Estado actualizado correctamente");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error al actualizar estado");
	    }
	}


	

}